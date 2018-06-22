package vrp.service.impl;

import org.pcollections.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
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
    public void saveProjectAndDependentModules(final String projectName, final String modulesName) {
        validateRequestParams(projectName, modulesName);
        validateProjectIsExists(projectName);
        final var project = new Project(projectName, fetchSetModule(fetchSetModulesNameByString(modulesName)));
        projectRepository.save(project);
    }

    protected void validateProjectIsExists(final String projectName){
       if(projectRepository.findByProjectName(projectName)
                           .isPresent()){
           throw new ResourceExistsException("This project is already exists");
       }
    }

    protected void validateRequestParams(final String projectName, final String moduleName){
        if(StringUtils.isEmpty(projectName)|| StringUtils.isEmpty(StringUtils.trim(moduleName))){
            throw new InvalidRequestParamsException("Project name or module name cannot be is empty");
        }
    }

    protected PSet<Module> fetchSetModule(final PSet<String> modulesName){
        return HashTreePSet.from(modulesName.stream()
                                            .map(moduleName->new Module(moduleName))
                                            .collect(Collectors.toSet()));
    }

    protected PSet<String> fetchSetModulesNameByString(final String str){
       var set = List.of(str.split("\\r?\\n"))              //TODO
                            .stream()
                            .map(StringUtils::trim)
                            .collect(Collectors.toSet());
       set.remove("");
       return HashTreePSet.from(set);
    }
}
