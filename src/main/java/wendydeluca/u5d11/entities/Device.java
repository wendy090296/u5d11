package wendydeluca.u5d11.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Device {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "device_type")
    private String typeOfDevice;
    private String state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Device( String typeOfDevice, String state,User userId) {
        this.typeOfDevice = typeOfDevice;
        this.state = state;
        this.user= userId;
    }
}
