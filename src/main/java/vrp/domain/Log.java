package vrp.domain;

import vrp.exception.CreateInvalidObjectException;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logs"
     , schema = "monitoring")
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

    protected Log() {

    }

    public Log(final String textLog
             , final Date createdAt
             , final Module module) {
        this.textLog = textLog;
        this.createdAt = createdAt;
        this.module = module;
        validateCreateObject();
    }

    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    public String getTextLog() {
        return textLog;
    }

    protected void setTextLog(final String textLog) {
        this.textLog = textLog;
    }

    public Module getModule() {
        return module;
    }

    protected void setModule(final Module module) {
        this.module = module;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    protected void setCreatedAt(final Date createdAt)
    {
        this.createdAt = createdAt;
    }

    protected void validateCreateObject(){
        validateTextLog();
        validateCreatedDate();
        validateModule();
    }

    protected void validateTextLog(){
        if(textLog == null || textLog.isEmpty()){
            throw new CreateInvalidObjectException("text log can not be empty");
        }
    }

    protected void validateCreatedDate(){
        if(createdAt == null){
            throw new CreateInvalidObjectException("date can not be null");
        }
    }

    protected void validateModule(){
        if(module == null){
            throw new CreateInvalidObjectException("module can not be null");
        }
    }
}
