package br.com.rocha.validadorboleto.service.kafka;

import br.com.rocha.validadorboleto.mapper.BoletoMapper;
import br.com.rocha.validadorboleto.service.ValidarBoletoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import br.com.rocha.avro.Boleto;

@Service
public class BoletoConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoletoConsumer.class);

    private final ValidarBoletoService validarBoletoService;

    public BoletoConsumer(ValidarBoletoService validarBoletoService) {
        this.validarBoletoService = validarBoletoService;
    }

    @KafkaListener(topics = "${spring.kafka.topico-boleto}", groupId = "${spring.kafka.consumer.group-id}")
    public void consomeBoleto(@Payload Boleto boleto) {
        LOGGER.info(String.format("Consumed message -> %s", boleto));
        validarBoletoService.validar(BoletoMapper.toEntity(boleto));
    }
}
