package vrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vrp.exception.InvalidRequestParamsException;
import vrp.exception.PreconditionFailedException;
import vrp.exception.ResourceExistsException;
import vrp.service.ProjectService;

@Controller
public class ConfigurationController {

    private final ProjectService projectService;

    @Autowired
    public ConfigurationController(final ProjectService projectService){
        this.projectService = projectService;
    }

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public ModelAndView mainMenuController(){
        return new ModelAndView("main");
    }

    @RequestMapping(value = "/main/create_new_project", method = RequestMethod.GET)
    public ModelAndView createNewProjectGetController(){
        return new ModelAndView("create_new_project");
    }

    @RequestMapping(value = "/main/create_new_project", method = RequestMethod.POST)
    public ModelAndView createNewProjectPostController( @RequestParam("name_project") final String projectName
                                                      , @RequestParam("name_modules") final String modulesName){
        final var mav = new ModelAndView();
        projectService.saveProjectAndDependentModules(projectName, modulesName);
        mav.addObject("success_message", "Project added successfully");
        mav.setViewName("create_new_project");
        return mav;
    }

    @ExceptionHandler(ResourceExistsException.class)
    protected ModelAndView handleResourceExistsException(ResourceExistsException e){
        final var mav = new ModelAndView();
        mav.addObject("error_message", e.getMessage());
        mav.setViewName("create_new_project");
        return mav;
     }

    @ExceptionHandler(InvalidRequestParamsException.class)
    protected ModelAndView handleInvalidRequestParamsException(InvalidRequestParamsException e){
        final var mav = new ModelAndView();
        mav.addObject("error_message", e.getMessage());
        mav.setViewName("create_new_project");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    protected ModelAndView handleException(Exception e){
        throw new PreconditionFailedException(e.getMessage());
    }
}
