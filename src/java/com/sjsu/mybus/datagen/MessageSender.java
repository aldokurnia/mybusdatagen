/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjsu.mybus.datagen;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author aldok
 */
public class MessageSender {
    @Resource(mappedName = "jms/BusDataConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/BusDataQueue")
    private static Queue queue;

    public void sendMessage(String message) {
        MessageProducer messageProducer;
        TextMessage textMessage;
        try {
            
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            textMessage = session.createTextMessage();

            textMessage.setText(message);
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);

            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
