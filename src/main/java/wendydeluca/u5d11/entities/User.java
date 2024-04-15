package wendydeluca.u5d11.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID ID;
    private String username;
    private String name;
    private String surname;
    private String email;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Device> devices;

    public User( String username, String name, String surname, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;

    }
}
