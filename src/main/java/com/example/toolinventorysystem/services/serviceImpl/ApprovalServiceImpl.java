package com.example.toolinventorysystem.services.serviceImpl;

import com.example.toolinventorysystem.dto.InputDto.ToolInstanceIdAndNameDto;
import com.example.toolinventorysystem.dto.OutputDto.ApprovalDto;
import com.example.toolinventorysystem.enums.ApprovalStatus;
import com.example.toolinventorysystem.exception.CustomException;
import com.example.toolinventorysystem.exception.ElementNotFound;
import com.example.toolinventorysystem.models.*;
import com.example.toolinventorysystem.repository.*;
import com.example.toolinventorysystem.services.ApprovalService;
import com.example.toolinventorysystem.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final ModelMapper modelMapper;
    private final ApprovalRepository approvalRepository;
    private final UserRepository userRepository;
    private final MachineRepository machineRepository;
    private final ToolLedgerRepository toolLedgerRepository;
    private final ToolRepository toolRepository;
    private final ToolTypeRepository toolTypeRepository;

    //    public List<Approval> findAll(){
//        List<Approval> approval1 = approvalRepository.findAll();
//        Type listType = new TypeToken<List<Approval>>(){}.getType();
//        List<Approval> approval = modelMapper.map(approval1, listType);
//        return approval;
//    }
    public Approval requestApproval(Approval approval) {
        log.info("creating a approval request fro machine and tools");
        if (approval == null) {
            log.error("null request received");
        }
        approval.setUserId(CurrentUser.get().getId());
        User user = userRepository.findById(approval.getUserId()).orElseThrow(() -> {
            log.error("no such user ID found");
            return new ElementNotFound("No user found with this ID");
        });
        Machine machine = machineRepository.findById(approval.getMachineId()).orElseThrow(() -> {
            log.error("no such machine ID");
            return new ElementNotFound("No such machine Id");
        });
        approval.setToolTypeNameAndUnits(new HashMap<>());
        Approval approval1 = modelMapper.map(approval, Approval.class);
        approval1.setUserId(CurrentUser.get().getId());
        approval1.setUserName(user.getName());
        approval1.setMachineName(machine.getName());
        approval1.setStatus(ApprovalStatus.PENDING);
        approval1.setRequestAt(LocalDateTime.now());
        approval1.setToolTypeAndUnits(approval.getToolTypeAndUnits());

        Map<UUID, Integer> tools = approval.getToolTypeAndUnits();
        tools.forEach((key,value) ->{
            ToolType toolType = toolTypeRepository.findById(key).orElseThrow(() -> new ElementNotFound("no such tool type id found"));
            approval.getToolTypeNameAndUnits().put(toolType.getName(), value);
        });

        approval1.setToolTypeNameAndUnits(approval.getToolTypeNameAndUnits());
        approval1.setId(UUID.randomUUID());
        approval1 = approvalRepository.save(approval1);
        return modelMapper.map(approval1, Approval.class);
    }


    public void approveApproval(UUID id) {
        log.info("once the request is approved the details are logged to the tool ledger and updating tool units ");
        Approval approval = approvalRepository.findById(id).orElseThrow(() -> {
            log.error("no such approval ID found");
            return new ElementNotFound("No approval found with this ID");
        });
        Machine machine = machineRepository.findById(approval.getMachineId()).orElseThrow(() -> {
            log.error("no such machine ID found");
            return new ElementNotFound("No machine found with this ID");
        });

        if(machine.getAvailable()==Boolean.TRUE) {
            approval.setStatus(ApprovalStatus.APPROVED);
            approval.setApprovedAt(LocalDateTime.now());
            approval.setToolIdAndName(new HashMap<>());
            approvalRepository.save(approval);

            User user = userRepository.findById(approval.getUserId()).orElseThrow(() -> {
                log.error("no such user ID found");
                return new ElementNotFound("No user found with this ID");
            });


            ToolLedger toolLedger = new ToolLedger();
            toolLedger.setToolTypeNameAndUnits(new HashMap<>());
            toolLedger.setToolIds(new ArrayList<>());
            approval.setToolTypeNameAndUnits(new HashMap<>());
            Map<UUID, Integer> toolTypeAndUnits = approval.getToolTypeAndUnits();
            for (Map.Entry<UUID, Integer> entry : toolTypeAndUnits.entrySet()) {
                UUID key = entry.getKey();
                Integer value = entry.getValue();
                List<UUID> toolInstance = machine.getToolIds().get(key);
                List<Tool> toolObject = toolRepository.findByIdIn(toolInstance);
                if (toolObject.size() < value) {
                    throw new CustomException("requested number of tools not present in the inventory");
                }
                List<UUID> uuids = toolObject.stream()
                        .sorted(Comparator.comparing(Tool::getLifecycle))
                        .limit(value)
                        .map(BaseModel::getId)
                        .toList();

                ToolType toolType = toolTypeRepository.findById(key).orElseThrow(() -> new ElementNotFound("no such tool type id found"));


                toolLedger.getToolIds().addAll(uuids);
                toolLedger.getToolTypeNameAndUnits().put(toolType.getName(), value);
//            toolLedger.setToolTypeName(toolType.getName());
//            toolLedger.setUnits(value);
                toolLedger.setUsername(user.getName());
                toolLedger.setApprovalId(approval.getId());
                toolLedger.setIsInUse(Boolean.TRUE);
                toolLedger.setStartDateTime(LocalDateTime.now());
                toolLedger.setUserId(user.getId());
                toolLedger.setShowUserId(user.getShowUserId());
                toolLedger.setMachineId(machine.getId());
                toolLedger.setMachineName(machine.getName());
                toolLedger.setId(UUID.randomUUID());
                toolLedgerRepository.save(toolLedger);

                uuids.forEach(i -> {
                    Tool iToolObject = toolRepository.findByIdIn(i);
                    approval.getToolIdAndName().put(i, iToolObject.getToolTypeName());
                });

//                List<Object> toDto =new ArrayList<>();
//                uuids.forEach(i ->{
//                    Tool tool = toolRepository.findByIdIn(i);
//                    ToolInstanceIdAndNameDto dto = new ToolInstanceIdAndNameDto();
//                    dto.setToolId(i);
//                    dto.setToolTypeName(tool.getToolTypeName());
//                    toDto.add(dto);
//                });
//                approval.setToolIdAndName(toDto);


//                approval.getToolIds().addAll(uuids);
                approval.getToolTypeNameAndUnits().put(toolType.getName(), value);
                approval.setToolLedgerId(toolLedger.getId());
                approvalRepository.save(approval);
            }


            machine.setAvailable(Boolean.FALSE);
            machineRepository.save(machine);
        }else {
            rejectApproval(id);
            throw new CustomException("Machine is not available");
        }
    }

    public void rejectApproval(UUID id) {
        log.info("request for machine and tools is rejected");
        Approval approval = approvalRepository.findById(id).orElseThrow(() -> {
            log.error("no such approval ID found");
            return new ElementNotFound("No machine found with this ID");
        });
        approval.setStatus(ApprovalStatus.REJECTED);
        approvalRepository.save(approval);

    }

    @Override
    public List<Approval> getAllApprovalRequest() {
        List<Approval> all = approvalRepository.findAll();
        return all.stream().map(approval -> modelMapper.map(approval, Approval.class)).toList();
    }

//    @Override
//    public List<ApprovalDto> getAllApprovalRequest() {
//        List<Approval> all = approvalRepository.findAll();
//        List<ToolInstanceIdAndNameDto> toolOutputs = new ArrayList<>();
//        List<ApprovalDto> output = new ArrayList<>();
//        all.forEach(each->{
//            ApprovalDto approvalDto = modelMapper.map(each, ApprovalDto.class);
//            List<Tool> tools = toolRepository.findByIdIn(each.getToolIds());
//            List<ToolInstanceIdAndNameDto> toolInstanceIdAndNameDto = tools.stream().map(each -> modelMapper.map(each, ToolInstanceIdAndNameDto.class)).toList();
//            approvalDto.setToolsBeingUsed(toolInstanceIdAndNameDto);
//            output.add(approvalDto);
//        });
//        return output;
//    }
//    public Integer units;
//
//    public void returnTool(UUID id) {
//        Approval approval = approvalRepository.findById(id).orElse(null);
//        ToolLedger toolLedger = toolLedgerRepository.findById(approval.getId()).orElse(null);
//        toolLedger.setReturnDateTime(LocalDateTime.now());
//        toolLedgerRepository.save(toolLedger);
//        ToolType toolType = new ToolType();
//
//    }
}

