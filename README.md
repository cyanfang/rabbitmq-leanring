# RabbitMQ
## RabbitMQ 是一个基于AMQP协议，使用Erlang语言开发，开源的消息代理和队列服务器
###AMQP协议
AMQP的核心思想是生产者和消费者的解耦，生产者从不直接将消息发送给队列。<br/>
生产者通常不知道是否一个消息会被发送懂队列中，只是将消息发送到一个交换机。<br/>
先有交换机来接收，然后Exchange按照特定的策略转发到Queue进行存储。Exchange就类似于一个交换机，将各个消息发送到相应的队列中。<br/>
实际应用中，只需定义好Exchange的路由策略，生产者不用关心消息被发送到哪个Queue或者被哪个消费者消费。<br/>
这种模式下，生产者只面向Exchange发布消息，消费者只面向Queue消费消息。
Exchange定义了消息路由到Queue的规则，将各个层面的消息传递隔离开，使每一层只需关心自己面向的下一层，降低了整体的耦合度。


### RabbitMQ 特点
1. ***Queue 的消息只能被一个消费者消费，如果没有消费者监听队列那么消息会被存放到队列中持久化保存，<br/>直到有消费者监听队列则立即消费发送到队中的消息***
2. ***Queue的消息可以保证每个消息一定能被消费***

### RabbitMQ
### Java 绑定Exchange发送和接收消息

###Rabbitmq 的消息发送和接收机制

 所有的MQ产品从模型抽象来说都是一个过程：consumer订阅某个队列。 producer创建消息，然后发布到队列Queue中，最后将消息发送到监听的消费者。
 不同的MQ产品有不同的机制，Rabbitmq是基于AMQP协议的一个开源实现：

![rabbitmq](rabbitmq.png 'rabbitmq')  
  
  1. Message:
     消息是有消息头和消息体组成。
  2. Publisher
     消息的生产者
  3. Exchange
     交换器，接收生产者发送的消息并将这些消息路由给服务器中的队列。
  4. Binding
     绑定，用于queue和exchange之间的关联。
     一个Binding就是给予路由键将Exchange和queue连接起来的路由规则。 Exchange即一个由Binding构成的路由表。
  5. Queue
     消息队列，保存消息直到发送给consumer
  6. Connection
     网络连接，比如一个TCP连接。
  7. Channel
     信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真正的TCP连接内的虚拟连接，AMQP命令是通过信道发出去的。
    （因为对于操作系统，建立和销毁TCP都是非常昂贵的开销，所以引入channel，以复用一条TCP连接。）
  8. Consumer
     消息的消费者
  9. Virtual Host
  10. Broker
      消息队列服务器实体
  
  
### Exchange 类型 
  一共四种类型：direct, fanout, topic and headers

#### Direct
  
  消息中的routingKey（路由键）和Binding中的bindingKey一致，Exchange会将message发送到对应的queue中。
  是完全匹配，单播的模式
  
![DirectExchange](DirectExchange.png 'DirectExchange')    

![TopicExchange](TopicExchange.png 'TopicExchange')

![FanoutExchange](FanoutExchange.png 'FanoutExchange')  