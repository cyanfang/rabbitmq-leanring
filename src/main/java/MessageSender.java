import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author fangminmin
 * @description MQ sender
 * */
public class MessageSender {
    Logger logger= LoggerFactory.getLogger(MessageSender.class);
    public void sendMessage() throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost"); // set rabbitmq host
        factory.setPort(5672); // set rabbitmq port
        factory.setUsername("guest"); // set username
        factory.setPassword("guest"); // set password
        Connection connection=null; // define connection
        Channel channel=null; // set channel
        connection=factory.newConnection();
        channel=connection.createChannel();
        String message="helloWorld";
        // create queue, the name is myQueue
        channel.queueDeclare("myQueue",true,false,false,null);
        // send message to queue
        channel.basicPublish("","myQueue",null,message.getBytes("UTF-8"));
        logger.debug("send message successfully: "+message);
        //channel.close();
        //connection.close();
    }

    public void receive() throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection connection=factory.newConnection();
        final  Channel channel=connection.createChannel();
        channel.queueDeclare("myQueue",true,false,false,null);
        boolean autoAck=true;
        String consumerTag="";
        channel.basicConsume("myQueue",autoAck,consumerTag,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String bodyStr=new String(body,"UTF-8");
                logger.info("receive message: "+bodyStr);
            }
        });
        //channel.close();
        //connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        MessageSender messageSender =new MessageSender();
        messageSender.sendMessage();
        messageSender.receive();
    }
}
