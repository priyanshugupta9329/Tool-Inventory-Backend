package com.example.toolinventorysystem.controllers;


import com.example.toolinventorysystem.dto.InputDto.ToolDto;
import com.example.toolinventorysystem.dto.InputDto.ToolInput;
import com.example.toolinventorysystem.dto.InputDto.ToolReturnDto;
import com.example.toolinventorysystem.services.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;


    @PostMapping("/add")
    public List<ToolDto> addTools(@RequestBody @Valid ToolInput input){
        return toolService.addTools(input);
    }

    @PostMapping("/giveTools")
    public void returnTools(@RequestBody @Valid  ToolReturnDto toolReturnDto){ toolService.returnTools(toolReturnDto);}

//    @PostMapping("/used")
//    public List<ToolDto> addTools(@RequestBody Map<UUID, Integer> toolUsed){
//        for (Map.Entry<UUID, Integer> entry : toolUsed.entrySet()){
//            Tool tool = toolRepository.findById(entry.getKey()).get();
//            tool.setLifecycle(tool.getLifecycle() - entry.getValue());
//        }
//        return toolService.addTools(input);
//    }



}
