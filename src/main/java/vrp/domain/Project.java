package vrp.domain;

import javax.persistence.*;

@Entity
@Table(name = "projects"
     , schema = "monitoring")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_project")
    private String nameProject;

    protected Project() {
    }

    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    protected void setNameProject(final String nameProject) {
        this.nameProject = nameProject;
    }
}
