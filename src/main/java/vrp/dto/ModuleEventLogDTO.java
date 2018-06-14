package vrp.dto;

import org.thymeleaf.util.StringUtils;
import vrp.exception.CreateInvalidObjectException;

public class ModuleEventLogDTO {

    //////////////////////////////////
    // Calculated fields
    //

    private String projectName;
    private String moduleName;
    private String textLog;


    //////////////////////////////////
    // Constructors
    //

    protected ModuleEventLogDTO(){
    }

    public ModuleEventLogDTO(final String projectName, final String moduleName, final String textLog) {
        this.projectName = projectName;
        this.moduleName = moduleName;
        this.textLog = textLog;
        validateCreateObject();
    }


    //////////////////////////////////
    // Accessors
    //

    public String getProjectName() {
        return projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getTextLog() {
        return textLog;
    }


    //////////////////////////////////
    // Validate invariants fields
    //

    protected void validateCreateObject(){
        validateTextLog();
        validateProjectName();
        validateModuleName();
    }

    protected void validateTextLog(){
        if(StringUtils.isEmpty(textLog)){
            throw new CreateInvalidObjectException("Text log can not be empty");
        }
    }

    protected void validateProjectName(){
        if(StringUtils.isEmpty(projectName)){
            throw new CreateInvalidObjectException("Project name can not be empty");
        }
    }

    protected void validateModuleName(){
        if(StringUtils.isEmpty(moduleName)){
            throw new CreateInvalidObjectException("Module name can not be empty");
        }
    }
}
