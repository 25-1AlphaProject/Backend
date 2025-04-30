package alpha.chupchup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private String status;
    private T message;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>("success", data);
    }

    public static ResponseDto<String> error(String errorMessage) {
        return new ResponseDto<>("error", errorMessage);
    }
}
