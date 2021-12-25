package it.nb.batch.feature.producer;


import it.nb.batch.feature.producer.dto.MessagePayload;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface PublishMessage {

    void sendToKafka(final String topic, final Flux<MessagePayload> messages);
}
