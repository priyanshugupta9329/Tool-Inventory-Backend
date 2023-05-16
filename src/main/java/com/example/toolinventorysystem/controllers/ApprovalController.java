package com.example.toolinventorysystem.controllers;
import com.example.toolinventorysystem.models.Approval;
import com.example.toolinventorysystem.services.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApprovalController {
    private final ApprovalService approvalService;

//    @GetMapping
//    public List<UserOutputDto> findAll(){
////        return approvalServiceImpl.findAll();
//        return null;
//    }

    @GetMapping
    public List<Approval> getAllApprovalRequest(){
        return approvalService.getAllApprovalRequest();
    }

    @PostMapping("/raise-request")
    public Approval requestApproval(@RequestBody Approval approval){

//        Approval approval = new Approval();
//        approval.setStatus(PENDING);
//        approval.setUserId(CurrentUser.get().getId());
//        approval.setUserId();
//        approval.getMachineId();
//        approval.setToolId();
//        approval.setRequestAt();
        return approvalService.requestApproval(approval);
    }
    @PostMapping("/approved/{id}")
    public void approveApproval(@PathVariable UUID id){
         approvalService.approveApproval(id);
    }

    @PostMapping("/rejected/{id}")
    public void rejectApproval(@PathVariable UUID id){
        approvalService.rejectApproval(id);
    }
//
//    @PostMapping("/{id}/return")
//    public void returnTool(@PathVariable UUID id, @RequestBody ToolType toolType){approvalService.returnTool(id,toolType);}

}
