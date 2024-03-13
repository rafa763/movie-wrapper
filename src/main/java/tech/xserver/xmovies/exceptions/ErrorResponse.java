package tech.xserver.xmovies.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private String note = "This is a custom error message by Xserver API";


    public ErrorResponse(HttpStatus httpStatus, String anUnexpectedErrorOccurred, LocalDateTime now) {
        this.status = httpStatus;
        this.message = anUnexpectedErrorOccurred;
        this.timestamp = now;
    }

}
