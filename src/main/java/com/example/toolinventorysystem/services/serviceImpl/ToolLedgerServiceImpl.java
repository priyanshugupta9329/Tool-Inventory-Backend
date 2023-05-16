package com.example.toolinventorysystem.services.serviceImpl;

import com.example.toolinventorysystem.dto.OutputDto.ToolLedgerOutputDto;
import com.example.toolinventorysystem.models.QToolLedger;
import com.example.toolinventorysystem.models.ToolLedger;
import com.example.toolinventorysystem.repository.ToolLedgerRepository;
import com.example.toolinventorysystem.services.ToolLedgerService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ToolLedgerServiceImpl implements ToolLedgerService {
    private final ToolLedgerRepository toolLedgerRepository;

    private final ModelMapper modelMapper;
    private final QToolLedger qToolLedger = QToolLedger.toolLedger;

    public List<ToolLedgerOutputDto> getAll(String q) {
        log.info("sending all user details from the database");

        BooleanBuilder where = new BooleanBuilder();
        if (q != null) {
            if (StringUtils.isNumeric(q)) {
                where.or(qToolLedger.showUserId.eq(Long.valueOf(q)));
            }
            where.or(qToolLedger.username.equalsIgnoreCase(q));
            where.or(qToolLedger.username.startsWithIgnoreCase(q));
            where.or(qToolLedger.machineName.equalsIgnoreCase(q));
            where.or(qToolLedger.machineName.startsWithIgnoreCase(q));
            where.or(qToolLedger.toolTypeNameAndUnits.containsKey(q));
        }
        List<ToolLedger> all = (List<ToolLedger>) toolLedgerRepository.findAll(where);
        return all.stream().map(toolLedger -> modelMapper.map(toolLedger, ToolLedgerOutputDto.class)).toList();
    }

    public List<ToolLedgerOutputDto> findByUserId(String showUserId) {
        List<ToolLedgerOutputDto> toolLedger1 = toolLedgerRepository.findByUserId(showUserId);
        Type listType = new TypeToken<List<ToolLedgerOutputDto>>() {
        }.getType();
        List<ToolLedgerOutputDto> dtoUserIdList = modelMapper.map(toolLedger1, listType);
        return dtoUserIdList;
    }
}
