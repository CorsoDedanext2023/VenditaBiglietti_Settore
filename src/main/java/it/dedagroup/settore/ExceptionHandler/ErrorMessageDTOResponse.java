package it.dedagroup.settore.ExceptionHandler;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessageDTOResponse {

    private String fieldName;
    private String message;
    private String invalid_Data;

}
