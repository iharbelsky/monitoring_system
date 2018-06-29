package vrp.service;

import org.pcollections.PVector;
import vrp.dto.ProjectDTO;

public interface ProjectService {
    void saveProject(String projectName, String [] moduleNames);
    PVector<ProjectDTO> fetchAllProjects();
    void deleteProject(String projectName);
}
