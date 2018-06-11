package vrp.domain;

import org.thymeleaf.util.StringUtils;
import vrp.exception.CreateInvalidObjectException;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logs", schema = "monitoring")
@NamedNativeQueries(
        @NamedNativeQuery(name="Log.findByTextLog",query = "SELECT * FROM monitoring.logs WHERE (text_log->>'text') = ?",resultClass = Log.class)
)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text_log")
    private String textLog;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_module")
    private Module module;

    protected Log() {
    }

    public Log( final String textLog, final Date createdAt, final Module module) {
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

    protected void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    private void validateCreateObject() {
        validateTextLog();
        validateCreatedDate();
        validateModule();
    }

    private void validateTextLog() {
        if (StringUtils.isEmpty(textLog)) {
            throw new CreateInvalidObjectException("Text log can not be empty");
        }
    }

    private void validateCreatedDate() {
        if (createdAt == null) {
            throw new CreateInvalidObjectException("Date can not be null");
        }
    }

    private void validateModule() {
        if (module == null) {
            throw new CreateInvalidObjectException("Module can not be null");
        }
    }

    @PostLoad
    private void postLoad(){
        validateCreateObject();
    }
}
