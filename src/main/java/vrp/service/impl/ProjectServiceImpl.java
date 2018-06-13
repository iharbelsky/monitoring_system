package vrp.service.impl;

import org.pcollections.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.exception.InvalidRequestParams;
import vrp.exception.ResourceExistsException;
import vrp.repository.ModuleRepository;
import vrp.repository.ProjectRepository;
import vrp.service.ProjectService;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public ProjectServiceImpl(final ProjectRepository projectRepository, final ModuleRepository moduleRepository){
        this.projectRepository = projectRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    @Transactional
    public void saveProjectAndDependentModules(final String projectName, final String modulesName) {
        validateRequestParams(projectName,modulesName);
        validateProjectIsExists(projectName);
        final var project = new Project(projectName);
        projectRepository.save(project);
        saveDependentModules(fetchSetModulesByString(modulesName), project);
    }

    protected void validateProjectIsExists(final String projectName){
       if(projectRepository.findByNameProject(projectName)
                           .isPresent()){
           throw new ResourceExistsException("This project is already exists");
       }
    }

    protected void validateRequestParams(final String projectName, final String moduleName){
        if(StringUtils.isEmpty(projectName)|| StringUtils.isEmpty(StringUtils.trim(moduleName))){
            throw new InvalidRequestParams("Project name or module name cannot be is empty");
        }
    }

    protected void saveDependentModules(final Set<String> modulesName, final Project project){
        modulesName.stream()
                   .map(str->new Module(str, project))
                   .forEach(module->moduleRepository.save(module));
    }

    protected PSet<String> fetchSetModulesByString(final String str){
       var set = List.of(str.split("\\r?\\n"))
                            .stream()
                            .map(obj-> StringUtils.trim(obj))
                            .collect(Collectors.toSet());
       set.remove("");
       return HashTreePSet.from(set);
    }
}
