package vrp.domain;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "logs", schema = "monitoring")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text_log")
    private String textLog;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "id_module")
    private Module module;

    public Log() {
    }

    public Log(String textLog, Date createdAt, Module module) {
        this.textLog = textLog;
        this.createdAt = createdAt;
        this.module = module;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextLog() {
        return textLog;
    }

    public void setTextLog(String textLog) {
        this.textLog = textLog;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }
}
