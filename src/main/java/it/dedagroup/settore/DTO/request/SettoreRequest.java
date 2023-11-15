package it.dedagroup.settore.DTO.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class SettoreRequest {

        @NotBlank(message = "Il campo 'nome' non può essere vuoto")
        private String nome;

        @Positive(message = "Puoi inserire solo numeri")
        private int posti;

        @Positive(message = "Puoi inserire solo numeri")
        private long idLuogo;
}
