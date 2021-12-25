package it.nb.batch.feature.reader.impl;

import it.nb.batch.config.NBBatchConfigProp;
import it.nb.batch.feature.producer.PublishMessage;
import it.nb.batch.feature.producer.dto.MessagePayload;
import it.nb.batch.feature.reader.FileReader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileReaderScheduler implements FileReader {

    PublishMessage publishMessage;

    NBBatchConfigProp nbBatchConfigProp;

    @SneakyThrows
    @Scheduled(cron = "${app.file-read-cron-expression:0/30 * * * * ?}")
    public void readFiles() {
        log.debug("Start reading files");
        try {
            final FileSystemResource filePath = new FileSystemResource(nbBatchConfigProp.getDirPath());
            FileUtils.streamFiles(filePath.getFile(), false, "pdf")
                    .forEach(file -> {
                        try {
                            publishMessage.sendToKafka("files-topic", Flux.just(MessagePayload.builder()
                                    .file(FileUtils.readFileToByteArray(file)).metaData(file.getName()).build()));
                            FileUtils.forceDelete(file);
                        } catch (final IOException ex) {
                            log.error("An error occurred while sending file to kafka", ex);
                        }
                    });
        } catch (Exception ex) {
            log.error("Transfer file to kafka failed due to ", ex);
        }
        log.debug("Finish reading files");
    }


}
