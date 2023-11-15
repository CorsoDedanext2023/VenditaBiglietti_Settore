package it.dedagroup.settore.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettoreResponse {
    private String nome;
    private int posti;
    private long version;
}