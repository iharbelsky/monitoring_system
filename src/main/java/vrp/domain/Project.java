package vrp.domain;

import org.apache.commons.lang3.StringUtils;
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

    @Column(name = "description")
    private String description;

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
        validateCreateObject();
    }

    public Project(String projectName, String description, Set<Module> projectModules) {
        this.projectName = projectName;
        this.description = description;
        this.projectModules = projectModules;
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

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
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
