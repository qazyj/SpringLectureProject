package hellojpa;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period {

    private LocalDateTime stratDate;
    private LocalDateTime endDate;

    public Period(){}

    public LocalDateTime getStratDate() {
        return stratDate;
    }

    public void setStratDate(LocalDateTime stratDate) {
        this.stratDate = stratDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
