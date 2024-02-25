package br.com.rocha.apiboleto.mapper;

import br.com.rocha.apiboleto.dto.BoletoDTO;
import br.com.rocha.apiboleto.entity.BoletoEntity;
import br.com.rocha.apiboleto.entity.enums.SituacaoBoleto;
import br.com.rocha.avro.Boleto;

public class BoletoMapper {
    private BoletoMapper() {

    }
    public static BoletoDTO toDTO(BoletoEntity boletoEntity) {
        return BoletoDTO.builder()
                .codigoBarras(boletoEntity.getCodigoBarras())
                .situacaoBoleto(boletoEntity.getSituacaoBoleto())
                .dataCriacao(boletoEntity.getDataCriacao())
                .dataAtualizacao(boletoEntity.getDataAtualizacao())
                .build();
    }

    public static Boleto toAvro(BoletoEntity boletoEntity) {
        return Boleto.newBuilder()
                .setCodigoBarras(boletoEntity.getCodigoBarras())
                .setSituacaoBoleto(boletoEntity.getSituacaoBoleto().ordinal())
                .build();
    }

    public static BoletoEntity toEntity(Boleto boleto) {
        return BoletoEntity.builder()
                .codigoBarras(boleto.getCodigoBarras().toString())
                .situacaoBoleto(SituacaoBoleto.values()[boleto.getSituacaoBoleto()])
                .build();
    }

}
