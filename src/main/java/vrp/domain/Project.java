package vrp.domain;

import org.thymeleaf.util.StringUtils;
import vrp.exception.CreateInvalidObjectException;
import javax.persistence.*;

@Entity
@Table(name = "projects", schema = "monitoring")
public class Project {

    //////////////////////////////////
    // Calculated fields
    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_project")
    private String nameProject;

    //////////////////////////////////
    // Constructors
    //

    protected Project() {
    }

    public Project(String nameProject) {
        this.nameProject = nameProject;
        validateCreateObject();
    }

    //////////////////////////////////
    // Accessors
    //

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    protected void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    //////////////////////////////////
    // Validate invariants fields
    //

    @PostLoad
    private void validateCreateObject(){
        validateNameProject();
    }

    private void validateNameProject(){
        if(StringUtils.isEmpty(nameProject)){
            throw new CreateInvalidObjectException("Name project can not be empty");
        }
    }
}
