package br.com.rocha.apiboleto.dto;

import br.com.rocha.apiboleto.entity.enums.SituacaoBoleto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoletoDTO {

    private String codigoBarras;

    private SituacaoBoleto situacaoBoleto;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

}
