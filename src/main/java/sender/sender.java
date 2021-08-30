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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class sender {

 public static void main(String[] args) {

  try {

   ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
   //Queue
   QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");

   Queue queue = (Queue) applicationContext.getBean("queue");

   // Create a connection. See
   // https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
   // Open a session without transaction and acknowledge automatic
   // Start the connection
   // Create a sender
   // Create a message
   // Send the message
   // Close the session
   // Close the connection
   QueueConnection connection = factory.createQueueConnection();
   QueueSession test = connection.createQueueSession(false, 3);
   connection.start();
   QueueSender sender = test.createSender(queue);
   TextMessage message = test.createTextMessage("Bonjour camarade !");
   sender.send(message);
   test.close();
   connection.close();
   System.out.println("Message send");
   //Topic
   TopicConnectionFactory topicfactory = (TopicConnectionFactory) applicationContext.getBean("connectionFactory");


  } catch (Exception e) {
   e.printStackTrace();
  }

 }

}
