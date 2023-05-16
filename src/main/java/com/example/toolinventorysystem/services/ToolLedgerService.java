package com.example.toolinventorysystem.services;

import com.example.toolinventorysystem.dto.OutputDto.ToolLedgerOutputDto;

import java.util.List;


public interface ToolLedgerService {
    public List<ToolLedgerOutputDto> getAll(String q);

    public List<ToolLedgerOutputDto> findByUserId(String showUserId);

}
