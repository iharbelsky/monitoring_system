package vrp.dto;

import org.apache.commons.lang3.StringUtils;
import vrp.exception.CreateInvalidObjectException;

public class ProjectDTO {

    //////////////////////////////////
    // Calculated fields
    //

    private Long id;
    private String projectName;
    private String description;


    //////////////////////////////////
    // Constructors
    //

    public ProjectDTO(final Long id, final String projectName, final String description) {
        this.id = id;
        this.projectName = projectName;
        this.description = description;
        validateCreateObject();
    }


    //////////////////////////////////
    // Accessors
    //

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    //////////////////////////////////
    // Validate invariants fields
    //

    public void validateCreateObject(){
        validateProjectName();
    }

    public void validateProjectName(){
        if(StringUtils.isEmpty(projectName)){
            throw new CreateInvalidObjectException("Project name can not be empty");
        }
    }
}
