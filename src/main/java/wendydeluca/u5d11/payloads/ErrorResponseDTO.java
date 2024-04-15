package wendydeluca.u5d11.payloads;


import java.time.LocalDateTime;

public record ErrorResponseDTO (String message, LocalDateTime timestamp){
}
