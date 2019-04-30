package com.marcelo.xyinc;

import static org.junit.Assert.assertNotNull;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.marcelo.xyinc.spring.SpringConfigContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfigContext.class })
public class SampleConfigTest {

    private static final Log LOG = LogFactory.getLog(SampleConfigTest.class);

    private SpringConfigContext configContext;

    public SampleConfigTest() {
	LOG.debug(SampleConfigTest.class.getSimpleName());
    }

    @PostConstruct
    public void init() {
	LOG.debug("PostConstruct init...");
    }

    @Autowired
    public void setConfigContext(final SpringConfigContext injectContext) {
	configContext = injectContext;
    }

    @Test
    public void springConfigContextTest() {
	assertNotNull(configContext);
    }

}
