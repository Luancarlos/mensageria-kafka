package br.com.rocha.apiboleto.service.kafka;

import br.com.rocha.apiboleto.mapper.BoletoMapper;
import br.com.rocha.apiboleto.service.BoletoService;
import br.com.rocha.avro.Boleto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotifcacaoConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifcacaoConsumer.class);

    private final BoletoService boletoService;

    @KafkaListener(topics = "${spring.kafka.topico-notificacao}")
    public void consumer(Boleto boleto) {
        LOGGER.info(String.format("Consumindo notificacao -> %s", boleto));
        this.boletoService.atualizar(BoletoMapper.toEntity(boleto));
    }
}
