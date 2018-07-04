package vrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vrp.exception.InvalidRequestParamsException;
import vrp.exception.PreconditionFailedException;
import vrp.exception.ResourceExistsException;
import vrp.service.ModuleEventLogService;
import vrp.service.ProjectService;

@Controller
public class ConfigurationController {

    private final ProjectService projectService;
    private final ModuleEventLogService moduleEventLogService;

    @Autowired
    public ConfigurationController( final ProjectService projectService
                                  , final ModuleEventLogService moduleEventLogService){
        this.projectService = projectService;
        this.moduleEventLogService = moduleEventLogService;
    }

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public ModelAndView mainMenuController(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");
        mav.addObject("projects", projectService.fetchAllProjects());
        return mav;
    }

    @RequestMapping(value = "/main/create_new_project", method = RequestMethod.GET)
    public ModelAndView createNewProjectGetController(){
        return new ModelAndView("create_new_project");
    }

    @RequestMapping(value = "/main/create_new_project", method = RequestMethod.POST)
    public ModelAndView createNewProjectPostController( @RequestParam("project_name") final String projectName
                                                      , @RequestParam(value = "description", required = false) final String description
                                                      , @RequestParam(value = "module_names[]", required = false) final String [] moduleNames){
        final var mav = new ModelAndView();
        projectService.saveProject(projectName, description, moduleNames);
        mav.addObject("success_message", "Project added successfully");
        mav.setViewName("create_new_project");
        return mav;
    }

    @RequestMapping(value = "/main/edit_project/{project_name}", method = RequestMethod.GET)
    public ModelAndView editProjectGetController( @PathVariable("project_name") final String projectName) {
        final var mav = new ModelAndView();
        mav.setViewName("edit_project");
        mav.addObject("project", projectService.fetchProjectByProjectName(projectName));
        return mav;
    }

    @RequestMapping(value = "/main/edit_project", method = RequestMethod.POST)
    public ModelAndView editProject ( @RequestParam("id") final Long id
                                    , @RequestParam("project_name") final String projectName
                                    , @RequestParam(value = "description", required = false) final String description){
        final var mav = new ModelAndView();
        projectService.editProject(id, projectName, description);
        mav.addObject("success_message", "Project edited successfully");
        mav.setViewName("complete_page");
        return mav;
    }

    @RequestMapping(value = "/main/view_project/{project_name}", method = RequestMethod.GET)
    public ModelAndView viewProject( @PathVariable("project_name") final String projectName){
        final var mav = new ModelAndView();
        mav.addObject("logs", moduleEventLogService.fetchAllLogsDTOByProjectName(projectName));
        mav.setViewName("view_project_logs");
        return mav;
    }

    @ExceptionHandler(ResourceExistsException.class)
    protected ModelAndView handleResourceExistsException(ResourceExistsException e){
        final var mav = new ModelAndView();
        mav.addObject("error_message", e.getMessage());
        mav.setViewName("error_page");
        return mav;
     }

    @ExceptionHandler(InvalidRequestParamsException.class)
    protected ModelAndView handleInvalidRequestParamsException(InvalidRequestParamsException e){
        final var mav = new ModelAndView();
        mav.addObject("error_message", e.getMessage());
        mav.setViewName("error_page");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    protected ModelAndView handleException(Exception e){
        throw new PreconditionFailedException(e.getMessage());
    }
}
