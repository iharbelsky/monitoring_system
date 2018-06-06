package vrp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value="/get/{nameProject}", method = RequestMethod.GET)
    public ProjectDTO getProjectByProjectName(@PathVariable(name = "nameProject") String nameProject){
        return projectService.getProjectByProjectName(nameProject);
    }

}
