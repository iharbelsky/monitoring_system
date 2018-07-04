package vrp.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.pcollections.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.dto.ProjectDTO;
import vrp.exception.InvalidRequestParamsException;
import vrp.exception.ResourceExistsException;
import vrp.repository.ProjectRepository;
import vrp.service.ProjectService;
import javax.transaction.Transactional;
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
    public void saveProject(final String projectName, final String description, final String [] moduleNames) {
        validateRequestParams(projectName, moduleNames);
        validateProjectIsExists(projectName);

        final var project = new Project(projectName, description, fetchSetModules(fetchSetModuleNamesByArray(moduleNames)));
        projectRepository.save(project);
    }

    @Override
    public PVector<ProjectDTO> fetchAllProjects() {
        return fetchListProjectDTObyProjectList(projectRepository.findAll());
    }

    @Override
    public String checkProject(final String projectName) {
       if(!projectRepository.findByProjectName(projectName).isPresent()){
           return "ok";
       }
        return "Exists";
    }

    @Override
    @Transactional
    public void deleteProject(final String projectName) {
        projectRepository.deleteByProjectName(projectName);
    }

    @Override
    public ProjectDTO fetchProjectByProjectName(final String projectName) {
        return fetchProjectDTObyProject(projectRepository.findByProjectName(projectName).get());
    }

    @Override
    @Transactional
    public void editProject(Long id, String projectName, String description) {
        projectRepository.updateProjectById(id, projectName, description);
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

    protected PVector<ProjectDTO> fetchListProjectDTObyProjectList(final List<Project> projects){
        return TreePVector.from(projects.stream()
                                        .map(this::fetchProjectDTObyProject)
                                        .collect(Collectors.toList()));
    }

    protected ProjectDTO fetchProjectDTObyProject(final Project project){
        return new ProjectDTO( project.getId(), project.getProjectName(), project.getDescription());
    }
}
