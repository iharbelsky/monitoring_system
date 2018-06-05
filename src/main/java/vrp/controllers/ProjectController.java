package vrp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vrp.domain.Project;
import vrp.repositories.ProjectRepository;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepo;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Iterable<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public Project setProject(@RequestBody Project project) {
        return projectRepo.save(project);
    }
}
