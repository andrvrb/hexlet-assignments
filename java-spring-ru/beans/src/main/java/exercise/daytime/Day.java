package exercise.daytime;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Scope("prototype")
@Getter
@Component
public class Day implements Daytime {

    private final String name = "day";

    // BEGIN
    @PostConstruct
    public void init() {
        System.out.println("Bean Day is initialized!");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Cleaning up resources Day or performing final actions!");
    }
    // END
}
