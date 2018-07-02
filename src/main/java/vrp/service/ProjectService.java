package vrp.service;

import org.pcollections.PVector;
import vrp.dto.ProjectDTO;

public interface ProjectService {
    void saveProject(String projectName, String description, String [] moduleNames);
    PVector<ProjectDTO> fetchAllProjects();
    void deleteProject(String projectName);
    String checkProject(String projectName);
}
