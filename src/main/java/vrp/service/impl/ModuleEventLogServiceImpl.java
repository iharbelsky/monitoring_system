package vrp.service.impl;

import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vrp.domain.ModuleEventLog;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.dto.ModuleEventLogDTO;
import vrp.exception.ResourceNotFoundException;
import vrp.repository.ModuleEventLogRepository;
import vrp.repository.ModuleRepository;
import vrp.repository.ProjectRepository;
import vrp.service.ModuleEventLogService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleEventLogServiceImpl implements ModuleEventLogService {

    private final ModuleEventLogRepository moduleEventLogRepository;
    private final ProjectRepository projectRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleEventLogServiceImpl( final ModuleEventLogRepository moduleEventLogRepository
                                    , final ProjectRepository projectRepository
                                    , final ModuleRepository moduleRepository) {
        this.moduleEventLogRepository = moduleEventLogRepository;
        this.projectRepository = projectRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    public void saveLog(final ModuleEventLogDTO moduleEventLogDTO) {
        final var module = safetyFetchModule(moduleEventLogDTO);
        final var log = new ModuleEventLog( moduleEventLogDTO.getTextLog()
                                          , new Date()
                                          , module);
        moduleEventLogRepository.save(log);
    }

    @Override
    public PVector<ModuleEventLogDTO> fetchAllLogsDTOByProjectName(final String projectName) {
       return fetchListLogsDTOByListLogs(moduleEventLogRepository.findAllByProjectName(projectName));
    }

    protected Module safetyFetchModule(final ModuleEventLogDTO moduleEventLogDTO) {
        final var project = safetyFetchProject(moduleEventLogDTO);
        return safetyFetchModulesByProject(project).stream()
                                                   .filter(obj -> moduleEventLogDTO.getModuleName()
                                                                                   .equals(obj.getModuleName()))
                                                   .findFirst()
                                                   .orElseThrow(() -> new ResourceNotFoundException("Module not found"));
    }

    protected Project safetyFetchProject(final ModuleEventLogDTO moduleEventLogDTO){
        return projectRepository.findByProjectName(moduleEventLogDTO.getProjectName())
                                .orElseThrow(() -> new ResourceNotFoundException("Project not Found"));
    }

    protected PVector<Module> safetyFetchModulesByProject(final Project project){
        return TreePVector.from(moduleRepository.findByProjectId(project.getId()));
    }

    protected PVector<ModuleEventLogDTO> fetchListLogsDTOByListLogs(final List<ModuleEventLog> moduleEventLogs){
        return TreePVector.from(moduleEventLogs.stream()
                                               .map(this::fetchLogDTOByLog)
                                               .collect(Collectors.toList()));
    }

    protected ModuleEventLogDTO fetchLogDTOByLog(final ModuleEventLog moduleEventLog){
        return new ModuleEventLogDTO( moduleEventLog.getModule()
                                                    .getProject()
                                                    .getProjectName()
                                    , moduleEventLog.getModule()
                                                    .getModuleName()
                                    , moduleEventLog.getTextLog());
    }
}
