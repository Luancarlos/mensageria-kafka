package br.com.rocha.apiboleto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletoRequestDTO {

    @NotNull(message = "não pode ser nullo")
    private String codigoBarras;
}
