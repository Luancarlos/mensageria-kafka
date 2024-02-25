package br.com.rocha.apiboleto.service.kafka;

import br.com.rocha.avro.Boleto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BoletoProducer {

    @Value("${spring.kafka.topico-boleto}")
    private String topicoBoleto;

    private final KafkaTemplate<String, Boleto> kafkaTemplate;

    public BoletoProducer(KafkaTemplate<String, Boleto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarMensagem(Boleto boleto)  {
        this.kafkaTemplate.send(this.topicoBoleto, boleto);
    }
}
