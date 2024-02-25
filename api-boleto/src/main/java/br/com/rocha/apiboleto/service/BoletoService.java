package br.com.rocha.apiboleto.service;

import br.com.rocha.apiboleto.controller.exception.ApplicationException;
import br.com.rocha.apiboleto.controller.exception.NotFoundException;
import br.com.rocha.apiboleto.dto.BoletoDTO;
import br.com.rocha.apiboleto.entity.BoletoEntity;
import br.com.rocha.apiboleto.entity.enums.SituacaoBoleto;
import br.com.rocha.apiboleto.mapper.BoletoMapper;
import br.com.rocha.apiboleto.repository.BoletoRepository;
import br.com.rocha.apiboleto.service.kafka.BoletoProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final BoletoProducer boletoProducer;

    public BoletoDTO salvar(String codigoBarras) {
        var boletoOption = boletoRepository.findByCodigoBarras(codigoBarras);
        if (boletoOption.isPresent()) {
            throw new ApplicationException("Boleto já existe uma solicitação de pagamento");
        }

        var boletoEntity = BoletoEntity.builder()
                .codigoBarras(codigoBarras)
                .situacaoBoleto(SituacaoBoleto.INICIALIZADO)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now()).build();

        boletoRepository.save(boletoEntity);
        this.boletoProducer.enviarMensagem(BoletoMapper.toAvro(boletoEntity));
        return BoletoMapper.toDTO(boletoEntity);
    }

    public BoletoDTO buscarBoletoPorCodigoBarras(String codigoBarras) {
        return BoletoMapper.toDTO(recuperarBoleto(codigoBarras));
    }

    private BoletoEntity recuperarBoleto(String codigoBarras) {
        return boletoRepository.findByCodigoBarras(codigoBarras)
                .orElseThrow(() -> new NotFoundException("Boleto não encontrado"));
    }

    public void atualizar(BoletoEntity boletoEntity) {
        BoletoEntity boletoAtual = recuperarBoleto(boletoEntity.getCodigoBarras());

        boletoAtual.setSituacaoBoleto(boletoEntity.getSituacaoBoleto());
        boletoAtual.setDataAtualizacao(LocalDateTime.now());
        boletoRepository.save(boletoAtual);
    }
}
