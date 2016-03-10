package com.clv.jmsspring.jms;

import com.clv.jmsspring.config.JmsAppConfig;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Usuario on 09/03/2016.
 */
@ContextConfiguration(classes = {JmsAppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly = true)
public class JMSSenderWiredTest {

    private final static Logger logger = Logger.getLogger(JMSSenderWired.class);

    @Autowired
    private JMSSenderWired jmsSender;

    @Test
    public void testSendMessage(){
        logger.info("-- Begin task --");
        jmsSender.sendMessage("HOLA");
        logger.info("-- Finish task --");
    }
}
