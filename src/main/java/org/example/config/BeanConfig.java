package org.example.config;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;


@Configuration
public class BeanConfig {
    @SneakyThrows
    @Bean
    public XML producerXml() {
        return new XMLDocument(
                Objects.requireNonNull(getClass().getResourceAsStream("/kafka/producer.xml")).readAllBytes()
        );
    }
}
