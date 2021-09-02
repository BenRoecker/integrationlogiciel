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
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.jms.Topic;

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
   System.out.println("Message from queue :" + Textmessage.getText() + " priorité :" + Textmessage.getJMSPriority());
   /*try{
    QueueReceiver receiver2 = test.createReceiver(queue); 
    TextMessage Textmessage2 = (TextMessage) receiver2.receive();
    System.out.println("Message from queue :"+Textmessage2.getText());
   }catch(Exception e){
    System.out.println(e.getMessage());
   }*/   // It didn't work with 2 receiver
   test.close();
   connection.close();


   // TOPIC--------------------------------------------------------------------
   Topic topic = (Topic) applicationContext.getBean("topic");
   TopicConnectionFactory topicfactory = (TopicConnectionFactory) applicationContext.getBean("connectionFactory");
   TopicConnection topicconnection = topicfactory.createTopicConnection();
   topicconnection.start();
   TopicSession topicsession = topicconnection.createTopicSession(true, 2);
   TopicSubscriber topicsubscriber = topicsession.createSubscriber(topic);
   TextMessage textmessage = (TextMessage) topicsubscriber.receive();
   System.out.println("topicsubscriber Message from Topic:"+textmessage.getText());
   // recevoir 2 message
   try{
    TopicSubscriber topicsubscriber2 = topicsession.createSubscriber(topic);
    TextMessage textmessage2 = (TextMessage) topicsubscriber2.receive();
    System.out.println("topicsubscriber2 Message from Topic:"+textmessage2.getText());
   }catch(Exception e){
    System.out.println(e.getMessage());
   }
   topicconnection.close();
   topicsession.close();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

}
