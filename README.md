STARTAR O SERVER KAFKA LOCALMENTE

- ````
    ./bin/kafka-server-start.sh config/kraft/server.properties
    
START MULTIPLOS SERVERS KAFKA
- gerar uma UUID
- - ````
    ./bin/kafka-storage.sh random-uuid

- startar os servers 1,2 e 3 com o UUID gerado

- ````
  ./bin/kafka-storage.sh format -t XCzvhiq_TY-WswzUfyrC9A -c config/kraft/server-1.properties
  ./bin/kafka-storage.sh format -t XCzvhiq_TY-WswzUfyrC9A -c config/kraft/server-2.properties
  ./bin/kafka-storage.sh format -t XCzvhiq_TY-WswzUfyrC9A -c config/kraft/server-3.properties


Executar em terminais diferentes
- ````
  ./bin/kafka-server-start.sh config/kraft/server-1.properties
  ./bin/kafka-server-start.sh config/kraft/server-2.properties
  ./bin/kafka-server-start.sh config/kraft/server-3.properties


comando para exceutar o kafka localmente e escutar o consumer
./bin/kafka-console-consumer.sh --topic product-created-events-topic --bootstrap-server localhost:9092 --property print.key=true

POST http://localhost:<port>/products
{
    "title": "iPhone 11",
    "price": 800,
    "quantity": 19
}


