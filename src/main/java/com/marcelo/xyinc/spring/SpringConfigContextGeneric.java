package com.marcelo.xyinc.spring;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration(value = "springConfigContextGeneric")
@EnableTransactionManagement
@ComponentScan(basePackages = {

	// mapping repository
	"com.marcelo.xyinc.spring.repository",

	// mapping services
	"com.marcelo.xyinc.spring.service",

	// maping the resouces extra project
	"com.marcelo.xyinc.filters" })
public class SpringConfigContextGeneric extends AnnotationConfigApplicationContext implements BeanFactory {

    private static final Log LOG = LogFactory.getLog(SpringConfigContextGeneric.class);

    /**
     * DataSource to access the system database
     *
     * @return: DataSource
     */
    @Bean(name = "dataSource")
    @Autowired
    public DataSource restDataSource() {
	Properties properties = loadPropertiesForHibernate();
	DataSourceBuilder<?> dataSouce = DataSourceBuilder.create();
	dataSouce.username(properties.getProperty("hibernate.connection.username"));
	dataSouce.password(properties.getProperty("hibernate.connection.password"));
	dataSouce.url(properties.getProperty("hibernate.connection.url"));
	dataSouce.driverClassName(properties.getProperty("hibernate.connection.driver_class"));
	return dataSouce.build();
    }

    /**
     * SessionFactory of the Hibernate,to build the access factory to the bank by
     * the hibernate, a hibernateSessionFactory
     *
     * @param: DataSource.
     * @return: SessionFactory is singleton
     */
    @Bean(name = "sessionFactory")
    @Autowired
    public SessionFactory sessionFactory(@Qualifier(value = "dataSource") final DataSource dataSource)
	    throws IOException {
	final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	sessionFactoryBean.setDataSource(dataSource);
	sessionFactoryBean.setHibernateProperties(loadPropertiesForHibernate());
	final Class<?>[] annotatedClasses = new Class<?>[] { /* add our class here */ };
	sessionFactoryBean.setAnnotatedClasses(annotatedClasses);
	sessionFactoryBean.afterPropertiesSet();
	LOG.info("configured SessionFactory bean: sessionFactory");
	return sessionFactoryBean.getObject();
    }

    /** define date access database */
    private Properties loadPropertiesForHibernate() {
	final Properties properties = new Properties();
	properties.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
	properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:testdb");
	properties.put("hibernate.connection.username", "sa");
	properties.put("hibernate.connection.password", "");
	// only for session factory
	properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
	properties.put("hibernate.hbm2ddl.auto", "update");
	return properties;
    }

    /**
     * To manage hibernate transactions
     *
     * @param SessionFactory
     * @return HibernateTransactionManager
     */
    @Bean(name = "transactionManager")
    @Autowired
    public HibernateTransactionManager transactionManager(
	    @Qualifier(value = "sessionFactory") final SessionFactory sessionFactory) {
	LOG.info("start HibernateTransactionManager");
	final HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
	transactionManager.afterPropertiesSet();
	LOG.info("finish HibernateTransactionManager");
	return transactionManager;
    }

    /**
     * To manage hibernate TransactionTemplate
     *
     * @param HibernateTransactionManager
     * @return TransactionTemplate
     */
    @Bean(name = "transactionTemplate")
    @Autowired
    public TransactionTemplate transactionTemplate(
	    @Qualifier(value = "transactionManager") final HibernateTransactionManager transactionManager) {
	LOG.info("start HibernateTransactionManager");
	final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
	transactionManager.afterPropertiesSet();
	LOG.info("finish HibernateTransactionManager");
	return transactionTemplate;
    }

}
