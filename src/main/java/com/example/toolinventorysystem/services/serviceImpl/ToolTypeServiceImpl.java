package com.example.toolinventorysystem.services.serviceImpl;

import com.example.toolinventorysystem.exception.ElementNotFound;
import com.example.toolinventorysystem.models.*;
import com.example.toolinventorysystem.repository.ToolTypeRepository;
import com.example.toolinventorysystem.services.ToolTypeService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class ToolTypeServiceImpl implements ToolTypeService {
    private final ToolTypeRepository toolTypeRepository;
    private final ModelMapper modelMapper;
    private final QToolType qToolType = QToolType.toolType;

    public List<ToolType> getAll(String q){
        log.info("sending list of tool type from the database");
        BooleanBuilder where = new BooleanBuilder();
        if(q != null){
            where.or(qToolType.name.equalsIgnoreCase(q));
            where.or(qToolType.name.startsWithIgnoreCase(q));
        }

        List<ToolType> all = (List<ToolType>) toolTypeRepository.findAll(where);
        return all.stream().map(toolType -> modelMapper.map(toolType, ToolType.class)).toList();
    }
    public ToolType saveToolType(ToolType toolType) {
        log.info("saving tool type details to the database from client");
        if (toolType== null) {
            log.error("empty input fields for tool type");
        }
        ToolType toolType1 = modelMapper.map(toolType, ToolType.class);
        toolType1.setId(UUID.randomUUID());
        toolType1 = toolTypeRepository.save(toolType1);
        return modelMapper.map(toolType1, ToolType.class);
    }

    public ToolType deleteToolType(UUID id) {
        log.info("deleting a tool type by a given id");
        ToolType toolType = toolTypeRepository.findById(id).orElseThrow(() -> {
            log.error("no such tool type ID found");
            return new ElementNotFound("No tool type found with this ID");
        });
        toolTypeRepository.delete(toolType);
        return modelMapper.map(toolType, ToolType.class);
    }

}
