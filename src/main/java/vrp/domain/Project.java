package vrp.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "projects", schema = "monitoring")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "name_project")
    private String nameProject;


    public Project() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }
}
