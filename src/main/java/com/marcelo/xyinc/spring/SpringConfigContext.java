package com.marcelo.xyinc.spring;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(value = "springConfigContext")
@EnableTransactionManagement
public class SpringConfigContext extends SpringConfigContextGeneric implements ApplicationContext {

    private static final Log LOG = LogFactory.getLog(SpringConfigContext.class);

    public SpringConfigContext() {
	LOG.debug(SpringConfigContextGeneric.class.getSimpleName());
    }

    /**
     * This causes the factory to be notified of the changes and does not generate a
     * debug
     */
    @PostConstruct
    public void init() {
	refresh();
    }
}
