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
    private String moduleName;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;


    //////////////////////////////////
    // Constructors
    //

    protected Module() {
    }

    public Module(String moduleName){
        this.moduleName = moduleName;
        validateCreateObject();
    }

    public Module(String moduleName, Project project) {
        this.moduleName = moduleName;
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

    public String getModuleName() {
        return moduleName;
    }

    protected void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
    }

    protected void validateNameModule(){
        if(StringUtils.isEmpty(moduleName)){
            throw new CreateInvalidObjectException("Name module can not be empty");
        }
    }
}
