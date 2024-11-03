package exercise.daytime;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
@Getter
public class Night implements Daytime {
    private final String name = "night";

    // BEGIN
    @PostConstruct
    public void init() {
        System.out.println("Bean Night is initialized!");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Cleaning up resources Night or performing final actions!");
    }
    // END
}
