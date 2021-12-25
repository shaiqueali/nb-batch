package it.nb.batch.feature.producer.impl;

import it.nb.batch.feature.producer.PublishMessage;
import it.nb.batch.feature.producer.dto.MessagePayload;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaPublishMessage implements PublishMessage {

    KafkaSender<String, byte[]> kafkaSender;

    @Override
    public void sendToKafka(final String topic, final Flux<MessagePayload> messages) {
        final String uuid = UUID.randomUUID().toString();
        log.debug("Start sending message to kafka with uuid [{}]",  uuid);
        kafkaSender.send(messages.map(m -> SenderRecord.create(new ProducerRecord<>(topic, m.getMetaData(), m.getFile()), uuid)))
                .doOnError(e -> log.error("Send failed", e)).subscribe(r -> log.info("{}", r.recordMetadata()));
        log.debug("Finish sending message to kafka with uuid [{}]",  uuid);
    }
}
