package vrp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vrp.dto.ProjectDTO;
import vrp.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {


    @Autowired
    private  ProjectService projectService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public void setProject(@RequestBody ProjectDTO projectDTO) {
         projectService.saveProject(projectDTO);
    }
}
