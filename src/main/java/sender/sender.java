package sender;

import javax.jms.Connection;
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
   // QUEUE -----
   QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
   Queue queue = (Queue) applicationContext.getBean("queue");
   QueueConnection connection = factory.createQueueConnection();
   QueueSession test = connection.createQueueSession(false, 3);
   connection.start();
   QueueSender sender = test.createSender(queue);
   TextMessage message = test.createTextMessage("Bonjour camarade de la queue !");
   sender.send(message);
   test.close();
   connection.close();
   System.out.println("QUEUE : Message send");
   //Topic-----------
   Topic topic = (Topic) applicationContext.getBean("topic");
   TopicConnectionFactory topicfactory = (TopicConnectionFactory) applicationContext.getBean("connectionFactory");
   TopicConnection topicconnection = topicfactory.createTopicConnection();
   TopicSession topicsession = topicconnection.createTopicSession(false, 3);
   TopicPublisher topicpublisher = topicsession.createPublisher(topic);
   message = test.createTextMessage("Bonjour camarade du topic !");
   topicpublisher.publish(message);
   System.out.println("TOPIC : Message send");
   topicconnection.close();
   topicsession.close();
   // Topic 
  } catch (Exception e) {
   e.printStackTrace();
  }

 }

}
