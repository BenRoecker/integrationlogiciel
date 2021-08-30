package receiver;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class receiver {

 public static void main(String[] args) {
  try {

   ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
   QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");

   Queue queue = (Queue) applicationContext.getBean("queue");

   // Create a connection. See
   // https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
   // Open a session
   // start the connection
   // Create a receive
   // Receive the message
   QueueConnection connection = factory.createQueueConnection();
   QueueSession test = connection.createQueueSession(false, 3);
   connection.start();
   QueueReceiver receiver = test.createReceiver(queue);
   TextMessage Textmessage = (TextMessage) receiver.receive();
   System.out.println(Textmessage.getText());
   // changement 

  } catch (Exception e) {
   e.printStackTrace();
  }
 }

}
