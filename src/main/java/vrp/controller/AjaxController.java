package vrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vrp.service.ProjectService;

@RestController
public class AjaxController {

    private final ProjectService projectService;

    @Autowired
    public AjaxController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/delete-project", method = RequestMethod.POST)
    public void deleteProject(@RequestParam("project_name") final String projectName){
        projectService.deleteProject(projectName);
    }
}
