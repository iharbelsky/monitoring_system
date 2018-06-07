package vrp.dto;

public class LogDTO {

    private String projectName;
    private String moduleName;
    private String textLog;


    public LogDTO() {

    }

    public LogDTO(String projectName
                 ,String moduleName
                 ,String textLog) {
        this.projectName = projectName;
        this.moduleName = moduleName;
        this.textLog = textLog;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTextLog() {
        return textLog;
    }

    public void setTextLog(String textLog) {
        this.textLog = textLog;
    }

}
