package vrp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "modules", schema = "monitoring")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "name_module")
    private String nameModule;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_project")
    private Project project;

    public Module() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

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
