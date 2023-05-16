package com.example.toolinventorysystem.services;
import com.example.toolinventorysystem.dto.OutputDto.ApprovalDto;
import com.example.toolinventorysystem.models.Approval;

import java.util.List;
import java.util.UUID;


public interface ApprovalService {
//    public List<Approval> findAll();
    Approval requestApproval(Approval approval);
    void approveApproval(UUID id);
    void rejectApproval(UUID id);

    List<Approval> getAllApprovalRequest();
//    List<ApprovalDto> getAllApprovalRequest();
//    public void returnTool(UUID id);

}
