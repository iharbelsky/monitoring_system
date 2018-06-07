package vrp.dto;

import vrp.exception.CreateInvalidObjectException;

public class LogDTO {

    private String projectName;
    private String moduleName;
    private String textLog;

    protected LogDTO(){
    }

    public LogDTO( final String projectName
                 , final String moduleName
                 , final String textLog) {
        this.projectName = projectName;
        this.moduleName = moduleName;
        this.textLog = textLog;
        validateCreateObject();
    }

    public String getProjectName() {
        return projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getTextLog() {
        return textLog;
    }

    protected void validateCreateObject(){
        validateTextLog();
        validateProjectName();
        validateModuleName();
    }

    protected void validateTextLog(){
        if(textLog == null || textLog.isEmpty()){
            throw new CreateInvalidObjectException("text log can not be empty");
        }
    }

    protected void validateProjectName(){
        if(projectName == null || projectName.isEmpty()){
            throw new CreateInvalidObjectException("project name can not be empty");
        }
    }

    protected void validateModuleName(){
        if(moduleName == null || moduleName.isEmpty()){
            throw new CreateInvalidObjectException("module name can not be empty");
        }
    }
}
