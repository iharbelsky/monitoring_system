package vrp.domain;

import javax.persistence.*;

@Entity
@Table(name = "modules", schema = "monitoring")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_module")
    private String nameModule;


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


}
