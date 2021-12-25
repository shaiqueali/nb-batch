package it.nb.batch.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "kafka")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaConfigProperties {

    @NotEmpty
    String acks;

    @NotNull
    Client client;

    @NotEmpty
    List<@NotEmpty String> topics;

    @NotNull
    Bootstrap bootstrap;

    @Getter
    @ConstructorBinding
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class Bootstrap {
        List<String> servers;
    }

    @Getter
    @ConstructorBinding
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class Client {
        String id;
    }

}
