package exercise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;




@Entity
@Table(name = "guests")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    // BEGIN
    @NotEmpty
    private String name;

    @Email
    private String email;

    @Pattern(regexp="(^\\+?[1-9][0-9]{10,12}$)")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;
    // END

    @CreatedDate
    private LocalDate createdAt;
}
