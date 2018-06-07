package vrp.domain;

import javax.persistence.*;

@Entity
@Table(name = "modules"
     , schema = "monitoring")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_module")
    private String nameModule;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    protected Module() {

    }

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
}
