package vrp.domain;

import org.thymeleaf.util.StringUtils;
import vrp.exception.CreateInvalidObjectException;
import javax.persistence.*;

@Entity
@Table(name = "modules", schema = "monitoring")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_module")
    private String nameModule;

    protected Module() {
    }

    public Module(String nameModule, Project project) {
        this.nameModule = nameModule;
        this.project = project;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_project")
    private Project project;

    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    public String getNameModule() {
        return nameModule;
    }

    protected void setNameModule(final String nameModule) {
        this.nameModule = nameModule;
    }

    public Project getProject() {
        return project;
    }

    protected void setProject(final Project project) {
        this.project = project;
    }

    private void validateCreateObject(){
        validateNameModule();
        validateProject();
    }

    private void validateNameModule(){
        if(StringUtils.isEmpty(nameModule)){
            throw new CreateInvalidObjectException("Name module can not be empty");
        }
    }

    private void validateProject(){
        if(project == null){
            throw new CreateInvalidObjectException("Project can not be empty");
        }
    }

    @PostLoad
    private void postLoad(){
        validateCreateObject();
    }


}
