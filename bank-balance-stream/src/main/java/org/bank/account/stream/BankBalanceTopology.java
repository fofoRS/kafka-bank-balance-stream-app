package org.bank.account.stream;

import io.quarkus.kafka.client.serialization.JsonbSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class BankBalanceTopology {

    public static final String ACCOUNT_TRANSACTION_TOPIC = "transaction";

    @Produces
    public Topology bankBalanceTopologyProducer() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        JsonbSerde<AccountTransaction> transactionValueSerde = new JsonbSerde<>(AccountTransaction.class);
        JsonbSerde<AccountBalance> accountBalanceValueSerde = new JsonbSerde<>(AccountBalance.class);

        System.out.println("Initiating Pipeline");

        KTable<String, AccountBalance> accountBalanceKTable=
        streamsBuilder.stream(
                ACCOUNT_TRANSACTION_TOPIC,
                Consumed.with(Serdes.String(),transactionValueSerde))
                .groupByKey()
                .aggregate(
                        AccountBalance::initialize,
                        (name, transaction,balance) -> balance.updateFrom(transaction),
                        Materialized
                                .<String,AccountBalance, KeyValueStore<Bytes, byte[]>>as("accountBalance")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(accountBalanceValueSerde));

        accountBalanceKTable.toStream()
                .peek((name,balance) -> System.out.println("Sending aggregation for " + name ))
                .to("accountBalanceAggregate", Produced.<String,AccountBalance>with(Serdes.String(),accountBalanceValueSerde));

        return streamsBuilder.build();
    }
}
