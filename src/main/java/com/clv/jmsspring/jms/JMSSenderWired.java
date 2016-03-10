package com.clv.jmsspring.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by Usuario on 09/03/2016.
 */
@Component("jmsSender")
public class JMSSenderWired {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination destination;

    public void sendMessage(final String message){
        MessageCreator messageCreator = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(message);

                return textMessage;
            }
        };
        System.out.println("Sending message  -- " +  message);
        jmsTemplate.send(destination, messageCreator);

    }

}
