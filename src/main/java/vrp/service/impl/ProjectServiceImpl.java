package vrp.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.pcollections.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.exception.InvalidRequestParamsException;
import vrp.exception.ResourceExistsException;
import vrp.repository.ProjectRepository;
import vrp.service.ProjectService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(final ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public void saveProject(final String projectName, final String [] moduleNames) {
        validateRequestParams(projectName, moduleNames);
        validateProjectIsExists(projectName);
        final var project = new Project(projectName, fetchSetModules(fetchSetModuleNamesByArray(moduleNames)));
        projectRepository.save(project);
    }

    protected void validateProjectIsExists(final String projectName){
       if(projectRepository.findByProjectName(projectName)
                           .isPresent()){
           throw new ResourceExistsException("This project is already exists");
       }
    }

    protected void validateRequestParams(final String projectName, final String [] moduleNames){
        if(StringUtils.isEmpty(projectName)){
            throw new InvalidRequestParamsException("Project name can't be is empty");
        }
        if(moduleNames == null || moduleNames.length == 0 || fetchSetModuleNamesByArray(moduleNames).size() == 0){
            throw new InvalidRequestParamsException("At least one module must be specified");
        }
    }

    protected PSet<Module> fetchSetModules(final PSet<String> moduleNames){
        return HashTreePSet.from(moduleNames.stream()
                                            .map(Module::new)
                                            .collect(Collectors.toSet()));
    }

    protected PSet<String> fetchSetModuleNamesByArray(final String [] moduleNames){
        final var setModuleNames = List.of(moduleNames).stream()
                                                       .map(StringUtils::trim)
                                                       .collect(Collectors.toSet());
        setModuleNames.remove("");
        return HashTreePSet.from(setModuleNames);
    }
}
