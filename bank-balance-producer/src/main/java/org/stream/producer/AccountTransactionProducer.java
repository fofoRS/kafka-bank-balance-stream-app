package org.stream.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecord;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class AccountTransactionProducer {

    static final Logger logger = LoggerFactory.getLogger(AccountTransactionProducer.class);

    @Outgoing("transaction")
    public Multi<OutgoingKafkaRecord<String,String>> initTransactions() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
                .onItem()
                .transform(tick -> generateTransactions())
                .onItem()
                .transform(transaction -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                       return KafkaRecord.of(transaction.getName(),mapper.writeValueAsString(transaction));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return KafkaRecord.of("","");
                    }
                })
                .onOverflow()
                .buffer(10);
    }

    private AccountTransaction generateTransactions() {
         Map<Integer,String> users = new HashMap<Integer, String>() {{
            put(1, "Alicia");
            put(2, "Ozzy");
            put(3, "Axel");
            put(4, "Michael");
        }};
        Integer randomKey = ThreadLocalRandom.current().nextInt(1,5);
        Integer randomAmount = ThreadLocalRandom.current().nextInt(0,100);
        return new AccountTransaction(users.get(randomKey), randomAmount, Instant.now());
    }
}
