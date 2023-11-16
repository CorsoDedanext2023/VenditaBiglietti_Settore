package it.dedagroup.settore.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class SettoreRequest {

        @NotBlank(message = "Il campo 'nome' non può essere vuoto")
        private String nome;

        @Positive(message = "Puoi inserire solo numeri")
        private int posti;

        @Positive(message = "Puoi inserire solo numeri positivi")
        private long idLuogo;
}
