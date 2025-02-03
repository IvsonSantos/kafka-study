STARTAR O SERVER KAFKA LOCALMENTE

- ````
    ./bin/kafka-server-start.sh config/kraft/server.properties
    
START MULTIPLOS SERVERS KAFKA
- gerar uma UUID
- ````
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

Parar todos os kafka servers
- ````
  ./bin/kafka-server-stop.sh
  
Executar o producer

- ````
  ./bin/kafka-console-producer.sh --topic my-topic --bootstrap-server localhost:9092 

Executar o producer com chave
- /bin/kafka-console-producer.sh --topic my-topic --bootstrap-server localhost:9092 --property "parse.key=true" --property "key.separator=:"


Executar o consumer
- ````
  ./bin/kafka-console-consumer.sh --topic my-topic --from-beginning --bootstrap-server localhost:9092
  ./bin/kafka-console-consumer.sh --topic product-created-events-topic --bootstrap-server localhost:9092 --property print.key=true

Executar com chave
- ./bin/kafka-console-consumer.sh --topic my-topic --bootstrap-server localhost:9092 --property print.key=true
- ./bin/kafka-console-consumer.sh --topic my-topic --bootstrap-server localhost:9092 --property print.key=true --property print.value=true --from-beginning

- ````
  POST http://localhost:<port>/products
  {
    "title": "iPhone 11",
    "price": 800,
    "quantity": 19
  }
  

Criar um topico

- ````
  ./bin/kafka-topics.sh --create --topic messages-order --partitions 3 --replication-factor 3 --bootstrap-server localhost:9092

Criar um produto

- ````
  ./bin/kafka-console-producer.sh --topic product-created-events-topic --bootstrap-server localhost:9092 --property parse.ket=true --property ket.separator=:

Checar o Dead Letter Topic

- ````
  ./bin/kafka-console-consumer.sh --topic product-created-events-topic.DLT --bootstrap-server localhost:9092 --from-beginning --property print.key=true --property print.value=true