package com.example.toolinventorysystem.services.serviceImpl;

import com.example.toolinventorysystem.dto.InputDto.ToolDto;
import com.example.toolinventorysystem.dto.InputDto.ToolInput;
import com.example.toolinventorysystem.dto.InputDto.ToolReturnDto;
import com.example.toolinventorysystem.enums.ApprovalStatus;
import com.example.toolinventorysystem.exception.ElementNotFound;
import com.example.toolinventorysystem.models.*;
import com.example.toolinventorysystem.repository.*;
import com.example.toolinventorysystem.services.ToolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {


    private final ToolRepository toolRepository;
    private final ToolLedgerRepository toolLedgerRepository;
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;
    private final ToolTypeRepository toolTypeRepository;
    private final ApprovalRepository approvalRepository;
//    private Map<UUID, Integer> NumberOfDeletes;

    @Override
    public List<ToolDto> addTools(ToolInput input) {
        log.info("adding tool instance to a particular tool type");
        final List<Tool> toSave = new ArrayList<>();
        Map<UUID, Integer> tools = input.getTools();
        tools.forEach((key, value) -> {

            ToolType toolType = toolTypeRepository.findById(key).orElseThrow(() -> {
                log.error("no such tool type ID found");
                return new ElementNotFound("No such tool type" + key);
            });

            for (int i = 0; i < value; i++) {
                Tool tool = new Tool();
                tool.setToolType(key);
                tool.setToolTypeName(toolType.getName());
                tool.setLifecycle(100F);
                tool.setNumberOfResharpen(0);
                tool.setNumberOfTimesUsed(0);
                tool.setId(UUID.randomUUID());
                toSave.add(tool);
            }
        });

        List<Tool> toSaved = toolRepository.saveAll(toSave);
        Machine machine = machineRepository.findById(input.getMachineId()).orElseThrow(() -> {
            log.error("no such machine ID found");
            return new ElementNotFound("No machine found with this ID");
        });
        if (CollectionUtils.isEmpty(machine.getToolIds())) {
            machine.setToolIds(new HashMap<>());
        }
        Map<UUID, List<Tool>> collect = toSaved.stream().collect(Collectors.groupingBy(Tool::getToolType));
        collect.forEach((key,value) -> {
            if(machine.getToolIds().containsKey(key)){
                machine.getToolIds().get(key).addAll(value.stream().map(BaseModel::getId).toList());
            }else{
                machine.getToolIds().put(key,value.stream().map(BaseModel::getId).toList());
            }
        });
        machineRepository.save(machine);

        return toSaved.stream().map(tool -> modelMapper.map(tool, ToolDto.class)).toList();

    }

    public void returnTools(ToolReturnDto toolReturnDto) {
        log.info("operator returning the tools he used");
        Approval approval = approvalRepository.findById(toolReturnDto.getApprovalId()).orElseThrow(() -> new ElementNotFound("no such approval ID found"));
        approval.setStatus(ApprovalStatus.RETURNED);
        approvalRepository.save(approval);
        toolReturnDto.setToolLedgerId(approval.getToolLedgerId());
        ToolLedger toolLedger = toolLedgerRepository.findById(toolReturnDto.getToolLedgerId()).orElseThrow(() -> new ElementNotFound("no such tool ledger id"));
        toolReturnDto.setMachineId(toolLedger.getMachineId());
        toolLedger.setReturnDateTime(LocalDateTime.now());
        toolLedger.setIsInUse(Boolean.FALSE);
        toolLedgerRepository.save(toolLedger);
        Machine machine = machineRepository.findById(toolLedger.getMachineId()).orElseThrow(()->new ElementNotFound("No such machine id"));
        Map<UUID, Integer> tools = toolReturnDto.getToolUsed();

//        if(CollectionUtils.isEmpty(NumberOfDeletes)){
//            NumberOfDeletes = new HashMap<>();
//        }

        tools.forEach((key, value) -> {
//        for (Map.Entry<UUID, Integer> entry : toolReturnDto.entrySet()) {
            Tool tool = toolRepository.findById(key).orElseThrow(() -> {
                log.error("no such tool type ID found");
                return new ElementNotFound("No tool type found with this ID");
            });

//            if(!NumberOfDeletes.containsKey(tool.getToolType())){
//                NumberOfDeletes.put(tool.getToolType(), 0);
//            }

            tool.setLifecycle(tool.getLifecycle() - value);
            if (tool.getNumberOfResharpen() <= 2) {
                if (tool.getLifecycle() <= 60.0) {
                    tool.setLifecycle(100F);
                    tool.setNumberOfResharpen(tool.getNumberOfResharpen() + 1);
                }
                toolRepository.save(tool);
            } else {

//                List<UUID> KeySet = machine.getToolIds().keySet().stream().toList();
//                int index = KeySet.indexOf(tool.getToolType());
//                boolean remove = machine.getToolIds().get(tool.getToolType()).remove(key);
//                int indexOfInstance = uuids.indexOf(key);


//                machine.getToolIds().forEach((key1,value1) ->{
//                    if(key1.equals(tool.getToolType())) {
//                        List<UUID> uuids = value1.stream().toList();
//                        uuids.remove(key);
//                    }
//                });
//                machineRepository.save(machine);

                machine.getToolIds().get(tool.getToolType()).remove(key);
                machineRepository.save(machine);

//                if(NumberOfDeletes.containsKey(tool.getToolType())){
//                    Integer integer = NumberOfDeletes.get(tool.getToolType());
//                    NumberOfDeletes.put(tool.getToolType(), integer+1);
//                }

                toolRepository.delete(tool);
            }
        });

//
//        ToolInput toolInput= new ToolInput();
//        NumberOfDeletes.forEach((key, value) -> {
//            if(value != 0) {
//                toolInput.setTools(NumberOfDeletes);
//                toolInput.setMachineId(machine.getId());
//                addTools(toolInput);
//            }
//
//        });

        machine.setAvailable(Boolean.TRUE);
        machineRepository.save(machine);
    }
}
