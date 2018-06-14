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
    private String nameProject;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id_project")
    private Set<Module> modulesProject;


    //////////////////////////////////
    // Constructors
    //

    protected Project() {
    }

    public Project(String nameProject) {
        this.nameProject = nameProject;
        validateCreateObject();
    }

    public Project(String nameProject, Set<Module> modulesProject){
        this.nameProject = nameProject;
        this.modulesProject = modulesProject;
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

    public Set<Module> getModulesProject() {
        return modulesProject;
    }

    protected void setModulesProject(Set<Module> modulesProject) {
        this.modulesProject = modulesProject;
    }


    //////////////////////////////////
    // Validate invariants fields
    //

    @PostLoad
    protected void validateCreateObject(){
        validateNameProject();
    }

    protected void validateNameProject(){
        if(StringUtils.isEmpty(nameProject)){
            throw new CreateInvalidObjectException("Name project can not be empty");
        }
    }
}
