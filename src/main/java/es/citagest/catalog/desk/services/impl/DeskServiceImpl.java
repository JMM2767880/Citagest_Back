package es.citagest.catalog.desk.services.impl;

import es.citagest.catalog.department.dto.DepartmentDTO;
import es.citagest.catalog.department.entity.DepartmentEntity;
import es.citagest.catalog.department.mapper.DepartmentMapper;
import es.citagest.catalog.department.repository.DepartmentRepository;
import es.citagest.catalog.desk.dto.DeskDTO;
import es.citagest.catalog.desk.dto.DeskDepartmentDTO;
import es.citagest.catalog.desk.dto.TurnsDeskDTO;
import es.citagest.catalog.desk.mapper.DeskMapper;
import es.citagest.catalog.desk.repository.DeskRepository;
import es.citagest.catalog.desk.services.DeskService;
import es.citagest.turn.repository.TurnRepository;
import es.citagest.turn.service.TurnService;
import es.citagest.user.entity.UserEntity;
import es.citagest.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeskServiceImpl implements DeskService {

    private DeskRepository deskRepository;
    private DepartmentRepository departmentRepository;
    private UserRepository userRepository;
    private TurnService turnService;

    public DeskServiceImpl(DeskRepository deskRepository, DepartmentRepository departmentRepository, UserRepository userRepository, TurnService turnService){
        this.deskRepository = deskRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.turnService = turnService;
    }

    @Override
    public List<DeskDTO> getEnabledDesk(){
        return deskRepository.findByEnabled(true).stream().map(DeskMapper::toDTO).toList();
    }

    @Override
    public List<DeskDepartmentDTO> getDeskDepartments(){
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOS = departmentEntities.stream().map(DepartmentMapper::toDTO).toList();
        List<DeskDepartmentDTO> deskDepartmentDTOS = new ArrayList<>();

        for (DepartmentDTO department: departmentDTOS){
            List<UserEntity> userEntities = userRepository.findByDepartmentEntityIdAndDeskEntityEnabledOrderByDeskEntityNameAsc(department.getId(), true);
            DeskDepartmentDTO deskDepartmentDTO = new DeskDepartmentDTO();
            List<TurnsDeskDTO> turnsDeskDTOS = new ArrayList<>();
            deskDepartmentDTO.setDepartmentDTO(department);
            for(UserEntity userEntity: userEntities){
                turnsDeskDTOS.add(new TurnsDeskDTO(DeskMapper.toDTO(userEntity.getDeskEntity()), turnService.getTurnsTodayByUserEmptyClient(userEntity.getId())));
            }

            if (turnsDeskDTOS.size() != 0){
                deskDepartmentDTO.setTurnsDeskDTOS(turnsDeskDTOS);
                deskDepartmentDTOS.add(deskDepartmentDTO);
            }
        }

        return deskDepartmentDTOS;
    }
}
