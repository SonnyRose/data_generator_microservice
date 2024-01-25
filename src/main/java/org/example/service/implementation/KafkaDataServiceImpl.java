package org.example.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.model.Data;
import org.example.service.interfaces.KafkaDataService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class KafkaDataServiceImpl implements KafkaDataService {

    private final KafkaSender<String, Object> sender;

    @Override
    public void send(Data data) {
        String topic = switch (data.getMeasurementType()) {
            case TEMPERATURE -> "data-temperature";
            case VOLTAGE -> "data-voltage";
            case POWER -> "data-power";
        };
        sender.send(
                        Mono.just(
                                SenderRecord.create(
                                        topic,
                                        0,
                                        System.currentTimeMillis(),
                                        String.valueOf(data.hashCode()),
                                        data,
                                        null
                                )
                        )
                )
                .subscribe();
    }
        //Mono - об'єкт, який інкапсулює в собі дані та відправляє їх у реактивному підході,
        // тобто не треба очікувати відповіді Kafka,
        // виконавши send буде продовжуватись виконання програми,
        // що покращує незалежність та швидкість виконання операцій

        //SenderRecord - запис, який містить інформацію про повідомлення,
        // яке має бути відправлене

        // topic(1) - назва теми Kafka, на яку має бути відправлений запис
        // partition(2) - розділ, який має бути відправлений запис
        // timestamp(3) - відмітка часу запису
        // key(4) - ключ запису(в даному випадку використано в якості ключа hashCode)
        // value(5) - значення запису(в даному випадку це data)
        // correlationMetadata (6) - додаткові метадані, які можуть бути пов'язані з записом
        // (в даному випадку не використовуються, через що і null)

        //subscribe() - метод, який сповіщає, що повідомлення в Kafka було надіслано та коли
    }
