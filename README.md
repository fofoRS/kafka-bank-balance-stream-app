# bank-balance sample project
 
This project shows how to use Quarkus and some reactive libraries it provides
to build a simple app which creates and publish events reactively 
which will be consumed and processed by a second service using Kafka Streams

The application have two services one is a producer which emits 
bank transactions periodically using the reactive message as Kafka as the broker.
THe second service takes those events and processes them to finally aggregate by message key and 
publish the back balance for each customer back to kafka.

## Running the application

You can run the entire application on docker using docker compose

1. Compile and create the artifact of the two project 
    1. `cd bank-balance-producer && mvn clean install`
    2. `cd bank-balance-stream && mvn clean install`

2.Run the entire application on docker using docker compose
1. `docker-compose -f docker-compose-back-balance.yml up --build` (in case you need build the images)
2. `docker-compose -f docker-compose-back-balance.yml up` (if images already exists)

## Inspect the application

To check if the application is running and doing what we're expecting
we can inspect how the events are beign sent and aggregate by the stream application.

1. Checking the Transaction Messages
    - ` docker-compose -f docker-compose-bank-balance.yml exec kafka bash`
    - copy/paste the kafka consumer for the **transaction topic** which is inside the kafka_topic_and_consumer.sh
2. Open a second session in the same kafka container in order to check the aggregate back balance
    - ` docker-compose -f docker-compose-bank-balance.yml exec kafka bash`
    - copy/paste the kafka consumer for the **accountBalanceAggregate topic** which is inside the kafka_topic_and_consumer.sh
    
## Stopping the application

` docker-compose -f docker-compose-bank-balance.yml down`

(this command will shutdown all the services in the docker-compose as well as remove any container, volume and network).