package vrp.domain;

import org.thymeleaf.util.StringUtils;
import vrp.exception.CreateInvalidObjectException;
import javax.persistence.*;

@Entity
@Table(name = "projects", schema = "monitoring")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_project")
    private String nameProject;

    protected Project() {
    }

    public Project(String nameProject) {
        this.nameProject = nameProject;
    }

    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    protected void setNameProject(final String nameProject) {
        this.nameProject = nameProject;
    }

    private void validateCreateObject(){
        validateNameProject();
    }

    private void validateNameProject(){
        if(StringUtils.isEmpty(nameProject)){
            throw new CreateInvalidObjectException("Name project can not be empty");
        }
    }

    @PostLoad
    private void postLoad(){
        validateCreateObject();
    }
}
