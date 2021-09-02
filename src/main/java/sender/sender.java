package sender;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.jms.TopicPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class sender {

 public static void main(String[] args) {

  try {

   ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
  
   
   // Create a connection. See
   // https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
   // Open a session without transaction and acknowledge automatic
   // Start the connection
   // Create a sender
   // Create a message
   // Send the message
   // Close the session
   // Close the connection
   // QUEUE ------------------------------------------------------------
   QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
   Queue queue = (Queue) applicationContext.getBean("queue");
   QueueConnection connection = factory.createQueueConnection();
   QueueSession test = connection.createQueueSession(false, 3);
   connection.start();
   QueueSender sender = test.createSender(queue);
   sender.setTimeToLive(20000); // time to live
   sender.setPriority(0);
   sender.setDeliveryMode(DeliveryMode.PERSISTENT);
   TextMessage message = test.createTextMessage("Bonjour camarade de la queue !");
   sender.send(message);
   try{
    QueueSender sender2 = test.createSender(queue);
    TextMessage message2 = test.createTextMessage("Bonjour camarade de la queue from another sender!");
    sender2.setPriority(9);
    sender2.send(message2);
    System.out.println("QUEUE : Message send");
   }catch(Exception e){
    System.out.println(e.getMessage());
   }
   test.close();
   connection.close();
   System.out.println("QUEUE : Message send");
   
   //Topic-----------------------------------
   Topic topic = (Topic) applicationContext.getBean("topic");
   TopicConnectionFactory topicfactory = (TopicConnectionFactory) applicationContext.getBean("connectionFactory");
   TopicConnection topicconnection = topicfactory.createTopicConnection();
   topicconnection.start();
   TopicSession topicsession = topicconnection.createTopicSession(true, 2);
   TopicPublisher topicpublisher = topicsession.createPublisher(topic);
   message = topicsession.createTextMessage("Bonjour camarade du topic !");
   topicpublisher.publish(message);
   System.out.println("TOPIC : Message send");
   try{
    TopicPublisher topicpublisher2 = topicsession.createPublisher(topic);
    TextMessage message2 = topicsession.createTextMessage("Bonjour camarade du topic ! from another publisher");
    topicpublisher2.publish(message2);
    System.out.println("TOPIC : Message send by another publisher");
   }catch(Exception e){
    System.out.println(e.getMessage());
   }
   topicsession.close();
   topicconnection.close();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
}
