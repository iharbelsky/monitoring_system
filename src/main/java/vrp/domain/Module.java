package vrp.domain;

import javax.persistence.*;

@Entity
@Table(name = "modules", schema = "monitoring")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_module")
    private String nameModule;

    @ManyToOne
    @JoinColumn(name="id_project")
    private Project project;

    public Module() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameModule() {
        return nameModule;
    }


    public void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
