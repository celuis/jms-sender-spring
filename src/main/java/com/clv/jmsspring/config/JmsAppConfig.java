package com.clv.jmsspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiTemplate;
import weblogic.jndi.WLInitialContextFactory;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Spring configuration file.
 */
@Configuration
@ComponentScan(basePackages = {"com.clv.jmsspring.jms"})
public class JmsAppConfig {
    /**
     * Create InitialContext.
     * @return
     */
    @Bean
    public Properties jndiProperties(){
        Properties properties = new Properties();
        properties.put("java.naming.factory.initial", WLInitialContextFactory.class.getName());
        properties.put("java.naming.provider.url", "t3://PROVIDER_URL:PORT"); //JNDI connection factory name stored in weblogic.
        return properties;
    }

    /**
     * Create InitialContext.
     * @return
     */
    @Bean
    public JndiTemplate jndiTemplate(){
        JndiTemplate jndiTemplate = new JndiTemplate();
        jndiTemplate.setEnvironment(this.jndiProperties());
        return jndiTemplate;
    }

    /**
     * Create connection factory.
     * @return
     */
    @Bean
    public QueueConnectionFactory queueConnectionFactory(){
        return (QueueConnectionFactory)lookupResource("CONNECTION_FACTORY_JNDI"); //JNDI connection factory name stored in weblogic.
    }

    /**
     * Create Queue.
     * @return
     */
    @Bean
    public Queue queueDestination(){
        return (Queue)lookupResource("QUEUE_JNDI"); //JNDI queue name stored in weblogic.
    }

    /**
     * Create DestinationResolver
     * @return
     */
    @Bean
    public JndiDestinationResolver jndiDestinationResolver(){
        JndiDestinationResolver jndiDestinationResolver = new JndiDestinationResolver();
        jndiDestinationResolver.setJndiTemplate(this.jndiTemplate());
        jndiDestinationResolver.setCache(true);
        return jndiDestinationResolver;
    }
    /**
     * Create JmsTemplate
     * @return
     */
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setDestinationResolver(this.jndiDestinationResolver());
        template.setConnectionFactory(this.queueConnectionFactory());
        return template;
    }

    /**
     * Lookup the resource stored in weblogic.
     * @param resource
     * @return
     */
    private Object lookupResource(String resource){
        try {
            InitialContext initialContext = new InitialContext(this.jndiProperties());
            Object o = initialContext.lookup(resource);
            initialContext.close();
            return o;

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
