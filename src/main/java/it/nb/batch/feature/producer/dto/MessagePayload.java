package it.nb.batch.feature.producer.dto;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class MessagePayload {

    String metaData;

    byte[] file;

}
