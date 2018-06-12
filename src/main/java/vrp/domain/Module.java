package vrp.domain;

import org.thymeleaf.util.StringUtils;
import vrp.exception.CreateInvalidObjectException;
import javax.persistence.*;

@Entity
@Table(name = "modules", schema = "monitoring")
public class Module {

    //////////////////////////////////
    // Calculated fields
    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_module")
    private String nameModule;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_project")
    private Project project;

    //////////////////////////////////
    // Constructors
    //

    protected Module() {
    }

    public Module(String nameModule, Project project) {
        this.nameModule = nameModule;
        this.project = project;
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

    public String getNameModule() {
        return nameModule;
    }

    protected void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }

    public Project getProject() {
        return project;
    }

    protected void setProject(Project project) {
        this.project = project;
    }

    //////////////////////////////////
    // Validate invariants fields
    //

    @PostLoad
    protected void validateCreateObject(){
        validateNameModule();
        validateProject();
    }

    protected void validateNameModule(){
        if(StringUtils.isEmpty(nameModule)){
            throw new CreateInvalidObjectException("Name module can not be empty");
        }
    }

    protected void validateProject(){
        if(project == null){
            throw new CreateInvalidObjectException("Project can not be empty");
        }
    }
}
