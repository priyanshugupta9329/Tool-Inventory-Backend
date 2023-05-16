package com.example.toolinventorysystem.services.serviceImpl;
import com.example.toolinventorysystem.dto.InputDto.MachineInputDto;
import com.example.toolinventorysystem.dto.OutputDto.MachineOutputDto;
import com.example.toolinventorysystem.dto.OutputDto.ToolTypeOutputDto;
import com.example.toolinventorysystem.exception.ElementNotFound;
import com.example.toolinventorysystem.models.*;
import com.example.toolinventorysystem.repository.MachineRepository;
import com.example.toolinventorysystem.repository.ToolTypeRepository;
import com.example.toolinventorysystem.services.MachineService;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;
    private final ToolTypeRepository toolTypeRepository;
    private ModelMapper modelMapper;
    private final QMachine qMachine = QMachine.machine;

    public List<MachineOutputDto> getAll(String q){
        log.info("sending list of machines from the database");
        BooleanBuilder where = new BooleanBuilder();
        if(q != null){
            where.or(qMachine.name.equalsIgnoreCase(q));
            where.or(qMachine.name.startsWithIgnoreCase(q));
        }

        List<Machine> all = (List<Machine>) machineRepository.findAll(where);
        return all.stream().map(machine -> modelMapper.map(machine, MachineOutputDto.class)).toList();
    }
    public MachineOutputDto saveMachine(MachineInputDto machine) {
        log.info("saving machine details to the database from client");
        if (machine== null) {
            log.error("empty input fields for machine");
        }
        Machine machine1 = modelMapper.map(machine, Machine.class);
        machine1.setAvailable(Boolean.TRUE);
        machine1.setId(UUID.randomUUID());
        machine1 = machineRepository.save(machine1);
        return modelMapper.map(machine1, MachineOutputDto.class);
    }

    public MachineOutputDto deleteMachine(UUID id) {
        log.info("deleting machine details using ID received");
        Machine machine = machineRepository.findById(id).orElseThrow(() -> {log.error("no such machine ID found"); return new ElementNotFound("No machine found with this ID");});
        machineRepository.delete(machine);
        return modelMapper.map(machine, MachineOutputDto.class);
    }

    public List<ToolTypeOutputDto> getCategoryByMachineId(UUID machineId){
        Machine machine = machineRepository.findById(machineId).orElseThrow(() -> new ElementNotFound("no such machine id"));
        List<ToolType> allByIdCategory = toolTypeRepository.findByIdIn(machine.getToolIds().keySet().stream().toList());
        return allByIdCategory.stream().map(toolType -> modelMapper.map(toolType, ToolTypeOutputDto.class)).toList();
    }
}
