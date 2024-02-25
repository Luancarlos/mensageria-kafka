package br.com.rocha.validadorboleto.service;

import br.com.rocha.validadorboleto.entity.BoletoEntity;
import br.com.rocha.validadorboleto.entity.enums.SituacaoBoleto;
import br.com.rocha.validadorboleto.mapper.BoletoMapper;
import br.com.rocha.validadorboleto.repository.BoletoRepository;
import br.com.rocha.validadorboleto.service.kafka.NotificacaoProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ValidarBoletoService {
    
    private final BoletoRepository boletoRepository;
    private final NotificacaoProducer notificacaoProducer;
    private final PagarBoletoService pagarBoletoService;

    public void validar(BoletoEntity boleto) {
        var codigo = Integer.parseInt(boleto.getCodigoBarras().substring(0,1));
        if (codigo % 2 == 0) {
            complementarBoletoErro(boleto);
            tratarNotificacaoPagamento(boleto);
        } else {
            complementarBoletoSucesso(boleto);
            var boletoSalvo = tratarNotificacaoPagamento(boleto);
            pagarBoletoService.pagar(boletoSalvo);
        }
    }
    
    private void complementarBoletoErro(BoletoEntity boleto) {
        boleto.setDataAtualizacao(LocalDateTime.now());
        boleto.setDataCriacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.ERRO_VALIDACAO);
    }

    private void complementarBoletoSucesso(BoletoEntity boleto) {
        boleto.setDataAtualizacao(LocalDateTime.now());
        boleto.setDataCriacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.VALIDADO);
    }

    private BoletoEntity tratarNotificacaoPagamento(BoletoEntity boletoEntity) {
        var boletoSalvo = boletoRepository.save(boletoEntity);
        notificacaoProducer.enviarMensagem(BoletoMapper.toAvro(boletoEntity));
        return boletoSalvo;
    }
}
