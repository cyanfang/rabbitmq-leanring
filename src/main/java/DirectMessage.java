import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author cyanfang
 * @description MQ sender
 * @date 2020-12-22
 * */
public class DirectMessage {
    public void sendMessage() throws IOException, TimeoutException {
        //create connection factory
        ConnectionFactory factory=new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        String message="hello direct";
        String exchangeName="myExchange";
        String queueName="myQueueDirect";
        String routingKey="myRoutingKey";
        String exchangeType="direct";
        channel.queueDeclare(queueName,true,false,false,null);
        channel.exchangeDeclare(exchangeName,exchangeType,true);// exchangeName, exchange type message is 持久化
        channel.basicPublish(exchangeName,routingKey,null,message.getBytes("UTF-8"));
        System.out.println("message sent successfully "+message);
        channel.close();
        connection.close();
    }


    public void receiveMessage() throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        // declare exchange
        String exchange="myExchange";
        channel.exchangeDeclare(exchange,"direct",true);
        // declare queue
        String queueName=channel.queueDeclare().getQueue();
        String routingKey="myRoutingKey";
        channel.queueBind(queueName,exchange,routingKey);
        String consumerTag="";
        channel.basicConsume("myQueueDirect",true,consumerTag,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String bodyStr=new String(body,"UTF-8");
                System.out.println("receive message: "+bodyStr);
            }
        });
       // channel.close();
        //connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        DirectMessage directMessage=new DirectMessage();
        directMessage.sendMessage();
        directMessage.receiveMessage();
    }
}
