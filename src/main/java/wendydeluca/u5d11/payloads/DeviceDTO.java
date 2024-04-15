package wendydeluca.u5d11.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record DeviceDTO(@NotEmpty(message = "The type of device field is mandatory.")
                        @Size(min = 5, max = 15, message = "The type of device must be set between 5 and 15 characters.")
                        String typeOfDevice,
                        @NotEmpty(message = "The state field is mandatory.")
                        @Size(min = 5, max = 15, message = "The state field must be set between 5 and 15 characters.")
                        String state,
                        @NotNull(message = "The user Id cannot be null.")
                        UUID userId
) {
}
