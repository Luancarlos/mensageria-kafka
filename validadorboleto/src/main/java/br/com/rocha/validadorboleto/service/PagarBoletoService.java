package br.com.rocha.validadorboleto.service;

import br.com.rocha.validadorboleto.entity.BoletoEntity;
import br.com.rocha.validadorboleto.entity.enums.SituacaoBoleto;
import br.com.rocha.validadorboleto.mapper.BoletoMapper;
import br.com.rocha.validadorboleto.repository.BoletoRepository;
import br.com.rocha.validadorboleto.service.kafka.NotificacaoProducer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PagarBoletoService {

    private final BoletoRepository boletoRepository;
    private final NotificacaoProducer notificacaoProducer;

    @SneakyThrows
    public void pagar(BoletoEntity boletoEntity) {
        Thread.sleep(10000);
        String codigoBarrasNumeros = boletoEntity.getCodigoBarras().replaceAll("[^0-9]", "");
        if (codigoBarrasNumeros.length() > 47) {
            complementarBoletoErro(boletoEntity);
        } else {
            complementarBoletoSucesso(boletoEntity);
        }

        boletoRepository.save(boletoEntity);
        notificacaoProducer.enviarMensagem(BoletoMapper.toAvro(boletoEntity));
    }

    private void complementarBoletoErro(BoletoEntity boletoEntity) {
        boletoEntity.setSituacaoBoleto(SituacaoBoleto.ERRO_PAGAMENTO);
        boletoEntity.setDataAtualizacao(LocalDateTime.now());
    }

    private void complementarBoletoSucesso(BoletoEntity boletoEntity) {
        boletoEntity.setSituacaoBoleto(SituacaoBoleto.PAGO);
        boletoEntity.setDataAtualizacao(LocalDateTime.now());
    }
}
