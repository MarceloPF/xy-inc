package com.marcelo.xyinc;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.marcelo.xyinc.spring.SpringConfigContext;

@Configuration(value = "appTest")
@ComponentScan
@EnableWebMvc
@EnableTransactionManagement
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = SpringConfigContext.class)
public class SampleConfigTest {

    private static final Log LOG = LogFactory.getLog(SampleConfigTest.class);

    public SampleConfigTest() {
	LOG.debug(SampleConfigTest.class.getSimpleName());
    }

    @PostConstruct
    public void init() {
	LOG.debug("PostConstruct init...");
    }

}
