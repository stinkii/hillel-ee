package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ErrorBody {
    private String message;
    private final Integer code = 400;
}
