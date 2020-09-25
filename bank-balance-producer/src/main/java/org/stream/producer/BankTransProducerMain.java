package org.stream.producer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import javax.inject.Inject;

@QuarkusMain
public class BankTransProducerMain implements QuarkusApplication {

    @Inject
    AccountTransactionProducer producer;

    public static void main(String[] args) {
        Quarkus.run(BankTransProducerMain.class,args);
    }
    @Override
    public int run(String... args) throws Exception {
        Quarkus.waitForExit();
        return 1;
    }
}
