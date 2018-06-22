package vrp.domain;

import org.thymeleaf.util.StringUtils;
import vrp.exception.CreateInvalidObjectException;
import javax.persistence.*;
import java.util.Set;

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
    private String projectName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id_project")
    private Set<Module> projectModules;


    //////////////////////////////////
    // Constructors
    //

    protected Project() {
    }

    public Project(String projectName) {
        this.projectName = projectName;
        validateCreateObject();
    }

    public Project(String projectName, Set<Module> projectModules){
        this.projectName = projectName;
        this.projectModules = projectModules;
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

    public String getProjectName() {
        return projectName;
    }

    protected void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<Module> getProjectModules() {
        return projectModules;
    }

    protected void setProjectModules(Set<Module> projectModules) {
        this.projectModules = projectModules;
    }


    //////////////////////////////////
    // Validate invariants fields
    //

    @PostLoad
    protected void validateCreateObject(){
        validateNameProject();
    }

    protected void validateNameProject(){
        if(StringUtils.isEmpty(projectName)){
            throw new CreateInvalidObjectException("Name project can not be empty");
        }
    }
}
