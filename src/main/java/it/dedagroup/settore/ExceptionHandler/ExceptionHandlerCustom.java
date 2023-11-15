package it.dedagroup.settore.ExceptionHandler;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerCustom {


    //Handler per controllare i Requestbody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorMessageListDTOResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorMessageListDTOResponse errors=new ErrorMessageListDTOResponse();
        for (FieldError violation:e.getFieldErrors()) {
            ErrorMessageDTOResponse error = new ErrorMessageDTOResponse();
            error.setMessage(violation.getDefaultMessage());
            error.setInvalid_Data(violation.getRejectedValue().toString());
            error.setFieldName(violation.getField());
            errors.getViolations().add(error);
        }
        return errors;
    }

    //Handler per controllare le ConstraintViolations(PathVariable e RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorMessageListDTOResponse onConstraintValidationException(ConstraintViolationException e){
        ErrorMessageListDTOResponse error=new ErrorMessageListDTOResponse();
        for (ConstraintViolation violation:e.getConstraintViolations()) {
            ErrorMessageDTOResponse messaggioErrore= new ErrorMessageDTOResponse();
            messaggioErrore.setFieldName(violation.getPropertyPath().toString());
            messaggioErrore.setMessage(violation.getMessage());
            messaggioErrore.setInvalid_Data(violation.getInvalidValue().toString());
            error.getViolations().add(messaggioErrore);
        }
        return error;
    }
}