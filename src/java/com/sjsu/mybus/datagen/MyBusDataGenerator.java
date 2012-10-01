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
public class MyBusDataGenerator {

    @Resource(mappedName = "jms/BusDataConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/BusDataQueue")
    private static Queue queue;

    public void sendMessage(String msg) {
        MessageProducer messageProducer;
        TextMessage textMessage;
        try {
            
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            textMessage = session.createTextMessage();

            textMessage.setText(msg);
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);

            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyBusDataBuilder builder = new MyBusDataBuilder();
        MyBusDataGenerator datagen = new MyBusDataGenerator();
        int busList[] = {22,24};
        int interval = 30000;
        
        while(true){
            //construct the JSON message
            String busData = "{\"busdata\":[";

            for(int i=0;i<busList.length;i++){
                busData += builder.getData(busList[i]);
                if(i < busList.length-1){ busData += ",";}
            }
            
            busData += "]}";

            //send out the data
            datagen.sendMessage(busData);
            
            //sleep for some time before sending more data 
            try{
                Thread.sleep(interval);
            }
            catch(Exception e){e.printStackTrace();}
        }        
        
        
    }
}
