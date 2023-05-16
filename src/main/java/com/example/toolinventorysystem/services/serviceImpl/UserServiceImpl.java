package com.example.toolinventorysystem.services.serviceImpl;

import com.example.toolinventorysystem.dto.InputDto.FirebaseUserInputDto;
import com.example.toolinventorysystem.dto.InputDto.LoginInputDto;
import com.example.toolinventorysystem.dto.InputDto.UserInputDto;
import com.example.toolinventorysystem.dto.OutputDto.LoginOutputDto;
import com.example.toolinventorysystem.dto.OutputDto.SignInFireBaseOutput;
import com.example.toolinventorysystem.dto.OutputDto.UserOutputDto;
import com.example.toolinventorysystem.constants.AppConstants;
import com.example.toolinventorysystem.enums.Role;
import com.example.toolinventorysystem.exception.CustomException;
import com.example.toolinventorysystem.exception.ElementNotFound;
import com.example.toolinventorysystem.models.IdInformation;
import com.example.toolinventorysystem.models.QUser;
import com.example.toolinventorysystem.models.User;
import com.example.toolinventorysystem.repository.IdInformationRepository;
import com.example.toolinventorysystem.repository.ToolLedgerRepository;
import com.example.toolinventorysystem.repository.UserRepository;
import com.example.toolinventorysystem.services.EmailService;
import com.example.toolinventorysystem.services.FirebaseUserService;
import com.example.toolinventorysystem.services.UserService;
import com.example.toolinventorysystem.utils.CurrentUser;
import com.google.firebase.auth.UserRecord;
import com.google.gson.Gson;
import com.querydsl.core.BooleanBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Log4j2
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final FirebaseUserService firebaseUserService;

//    private PatchMapper patchMapper;

    private final UserRepository userRepository;
    private final IdInformationRepository idInformationRepository;
    private final ToolLedgerRepository toolLedgerRepository;
    private final EmailService emailService;

    @Value("${firebase.accounts.url}")  //@Value is used to fetch any value saved in properties file
    private String FIREBASE_URL;

    @Value("${firebase.key}")
    private String FIREBASE_API_KEY;


    private final QUser qUser = QUser.user; //qfiles will be present in the target file generated source and then we will mark directory as test sources so that qfiles will be generated

    public List<UserOutputDto> getAll(String q) {
        log.info("sending all user details from the database");

        BooleanBuilder where = new BooleanBuilder();
        if (q != null) {
            if (StringUtils.isAlphanumeric(q)) { //StringUtils are present in the apache package
                where.or(qUser.showUserId.eq(Long.valueOf(q))); //qfiles are used for search api implementation
            }
            where.or(qUser.name.equalsIgnoreCase(q));
            where.or(qUser.name.startsWithIgnoreCase(q));
        }

        List<User> all = (List<User>) userRepository.findAll(where);
        return all.stream().map(user -> modelMapper.map(user, UserOutputDto.class)).toList();
    }

    public UserOutputDto saveUser(UserInputDto user) {
        log.info("creating a new user");

        if(userRepository.findByEmail(user.getEmail()).isPresent()){  //checking if the email is already present in the database or not
            throw new CustomException("Email already exits");
        }

        User user1 = modelMapper.map(user, User.class);
        FirebaseUserInputDto inputDto = new FirebaseUserInputDto();
        inputDto.setEmail(user1.getEmail());  //setting the login details given by the user to the firebase inputDto
        inputDto.setName(user1.getName());
        String randomPassword = RandomStringUtils.randomAlphanumeric(9);  //generating a random password of length 9 characters
        user1.setPassword(randomPassword);
        inputDto.setPassword(randomPassword);

        UserRecord userInFireBase = firebaseUserService.createUserInFireBase(inputDto);
        user1.setFirebaseId(userInFireBase.getUid());  //creating user in firebase

        if (CollectionUtils.isEmpty(idInformationRepository.findAll())) { //checking if the list of users is empty and setting the latest ID to 0
            IdInformation idInformation = new IdInformation();
            idInformation.setLatestUserId(0L);
            idInformationRepository.save(idInformation);
        }
        IdInformation idInformation = idInformationRepository.findAll().get(0);
        Long latestId = idInformation.getLatestUserId() + 1;  //incrementing latestId everytime new user is added
        user1.setShowUserId(latestId);
        if(CurrentUser.get().getRole().equals(Role.ADMIN)) {  //if admin is creating a new user then the role of the new user is set to manager
            user1.setRole(Role.MANAGER);
        } else if (CurrentUser.get().getRole().equals(Role.MANAGER)) {  //if the manager is creating a new user then the role of the new user is set to operator
            user1.setRole(Role.OPERATOR);
        }

        user1.setId(UUID.randomUUID());
        user1 = userRepository.save(user1);
        idInformation.setLatestUserId(latestId);
        idInformation.setId(UUID.randomUUID());
        idInformationRepository.save(idInformation);
        String mailBody = AppConstants.emailTemplet.replace("{USER_NAME}", user1.getName())  //sending invite mail by replacing the template with user specific details
                .replace("{EMAIL}", user1.getEmail())
                .replace("{PASSWORD}", randomPassword)
                .replace("{ROLE}", user1.getRole().toString().charAt(0)+user1.getRole().toString().substring(1).toLowerCase());

        emailService.sendMail("Welcome to tool inventory", mailBody, user1.getEmail());

        return modelMapper.map(user1, UserOutputDto.class);


    }

    public UserOutputDto getUser(UUID id) {   // method to get user by the given id
        log.info("sending the particular user details for a given ID");
        User user1 = userRepository.findById(id).orElseThrow(() -> {
            log.error("no such user ID found");
            return new ElementNotFound("No user found with this ID");
        });
        return modelMapper.map(user1, UserOutputDto.class);
    }

    public UserOutputDto deleteUser(UUID id) {   //method to delete a user by the given id
        log.info("deleting a user by a given id");
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("no such user ID found");
            return new ElementNotFound("No user found with this ID");
        });
        firebaseUserService.deleteUser(user.getFirebaseId()); //deleting user from firebase
        userRepository.delete(user);
        return modelMapper.map(user, UserOutputDto.class);
    }

    public UserOutputDto updateUser(UUID id, UserInputDto input) {   //PUT api to edit the user details by the given id
        log.info("updating the user details by a given id");
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("no such user ID found");
            return new ElementNotFound("No user found with this ID");
        });


        if(!(Objects.equals(input.getEmail(), user.getEmail()))){
            if(Objects.equals(input.getName(), user.getName())) {
                FirebaseUserInputDto inputDto = new FirebaseUserInputDto();
                inputDto.setEmail(input.getEmail());
                inputDto.setName(user.getName());
//                inputDto.setPassword(user.getPassword());

                firebaseUserService.updateUser(user.getFirebaseId(), inputDto);

//                UserRecord userInFireBase = firebaseUserService.createUserInFireBase(inputDto);
//                user.setFirebaseId(userInFireBase.getUid());
            }else{
                FirebaseUserInputDto inputDto = new FirebaseUserInputDto();
                inputDto.setEmail(input.getEmail());
                inputDto.setName(input.getName());
//                inputDto.setPassword(user.getPassword());

                firebaseUserService.updateUser(user.getFirebaseId(), inputDto);

//                UserRecord userInFireBase = firebaseUserService.createUserInFireBase(inputDto);
//                user.setFirebaseId(userInFireBase.getUid());
            }
        }



        modelMapper.map(input, user);
        user = userRepository.save(user);
        return modelMapper.map(user, UserOutputDto.class);
    }

    public LoginOutputDto login(LoginInputDto loginInputDto) {
        String url = FIREBASE_URL + ":signInWithPassword?key=" + FIREBASE_API_KEY;
        Map<String, Object> map = new HashMap<>();
        map.put("email", loginInputDto.getUsername());  //mapping the username to email
        map.put("password", loginInputDto.getPassword()); //mapping the password to the password field in firebase
        map.put("returnSecureToken", true);  //setting it to true so firebase sends us token

        RestTemplate restTemplate = new RestTemplate(); // to call apis through java
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String body = exchange.getBody(); // we will get body having local id access tokens in string format
        Gson gson = new Gson(); //it is used to covert string to any object
        SignInFireBaseOutput signInFireBaseOutput = gson.fromJson(body, SignInFireBaseOutput.class); // body mapped to fields in SignInFieBaseOutputDto
        LoginOutputDto loginOutputDto = new LoginOutputDto();
        loginOutputDto.setAccessToken(signInFireBaseOutput.getIdToken());
        loginOutputDto.setRefreshToken(signInFireBaseOutput.getRefreshToken());
        loginOutputDto.setExpiresIn(signInFireBaseOutput.getExpiresIn());
        loginOutputDto.setUsername(loginInputDto.getUsername());

        return loginOutputDto;
    }

    @Override
    public User getByFirebaseId(String uid) {
        return userRepository.findByFirebaseId(uid).orElseThrow(() -> {
            log.error("no such firebase ID found");
            return new ElementNotFound("No such firebase ID");
        });
    }

    @Override
    public UserOutputDto userMe() {
        return modelMapper.map(CurrentUser.get(), UserOutputDto.class);
    } //to display the user who has logged in


//    @Override
//    public void returnTool(UUID id) {
//
//        User user = userRepository.findById(id).orElse(null);
//        Approval approval = approvalRepository.findById(user.getId()).orElse(null);
//        ToolLedger toolLedger = toolLedgerRepository.findById(approval.getId()).orElse(null);
//        toolLedger.setReturnDateTime(LocalDateTime.now());
//        toolLedgerRepository.save(toolLedger);
//    }

}
