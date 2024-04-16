package wendydeluca.u5d11.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Setter;


public record UserDTO(@NotEmpty(message = "The name field is mandatory.")
                      @Size(min = 4, max = 15, message = "The name field must be between 4 and 15 characters.")
                      String name,
                      @NotEmpty(message = "The surname field is mandatory.")
                      @Size(min = 4, max = 15, message = "The surname field must be between 4 and 15 characters.")
                      String surname,
                      @NotEmpty(message = "The username field is mandatory.")
                      @Size(min = 3, max = 15, message = "The username field must be between 4 and 15 characters.")
                      String username,
                      @Email(message = "Invalid email format.")
                      @NotEmpty(message = "The email field is mandatory.")
                      String email,
                      @NotEmpty(message = "Password field must not be empty.")
                      @Size(min = 8, message = "Password must be at least 8 characters long.")
                      String password) {
}
