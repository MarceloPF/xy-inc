package com.marcelo.xyinc.spring;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelo.xyinc.filters.CharacterEncodingFilter;
@Configuration(value = "webapp")
@EnableWebMvc
@ComponentScan
@EnableScheduling
public class WebAppInitializer extends WebMvcConfigurationSupport implements WebApplicationInitializer {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final List<MediaType> MEDIA_TYPE = Arrays.asList(
	    new MediaType("application", "json", WebAppInitializer.UTF8),
	    new MediaType("text", "plain", WebAppInitializer.UTF8),
	    new MediaType("text", "html", WebAppInitializer.UTF8),
	    new MediaType("multipart", "form-data", WebAppInitializer.UTF8),
	    new MediaType("application", "x-www-form-urlencoded", WebAppInitializer.UTF8));

    public static final String REST_MAP = "/rest";

    public static final String MARCELO_ACECCESS = "/{sys_can_aceccess}";

    public static final String MARCELO_ACECCESS_TEXT = "sys_can_aceccess";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

	final AnnotationConfigWebApplicationContext context = createContext();
	context.register(SpringConfigContext.class);
	addListenerForContext(servletContext, context);
	addFilters(servletContext);
	context.refresh();

	configureRest(servletContext, createDispatcherServlet(context));
    }

    private AnnotationConfigWebApplicationContext createContext() {
	return new AnnotationConfigWebApplicationContext();
    }

    private void addFilters(ServletContext servletContext) {
	servletContext.addFilter("encoding", new CharacterEncodingFilter());
    }

    private void configureRest(ServletContext servletContext, final DispatcherServlet servlet) {
	final Dynamic dynamic = servletContext.addServlet(WebAppInitializer.REST_MAP, servlet);
	dynamic.addMapping(WebAppInitializer.REST_MAP + "/*");
	dynamic.setAsyncSupported(true);
	dynamic.setLoadOnStartup(1);
    }

    private DispatcherServlet createDispatcherServlet(final AnnotationConfigWebApplicationContext context) {
	return new DispatcherServlet(context);
    }

    private void addListenerForContext(ServletContext servletContext,
	    final AnnotationConfigWebApplicationContext context) {
	// define listener to spring for context
	servletContext.addListener(new ContextLoaderListener(context));
	servletContext.addListener(new RequestContextListener());
	context.setServletContext(servletContext);
    }

    @Bean(name = "httpComponentsClientHttpRequestFactoryAll")
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactoryAll() {
	return new HttpComponentsClientHttpRequestFactory();
    }

    /** Configure our restTemplate */
    @Bean(name = "restTemplate")
    @Autowired
    public RestTemplate restTemplate(
	    @Qualifier("httpComponentsClientHttpRequestFactoryAll") final HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {
	final RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
	return restTemplate;
    }

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
	converters.clear();
	converters.add(createStringHttpConverter());
	converters.add(formHttpConverter());
	converters.add(createResourceHttpMessageConverter());
	converters.add(createAllEncompassingFormHttpMessageConverter());
	converters.add(mappingJackson2HttpMessageConverter());
	converters.add(createFormHttpMessage(converters));
	super.addDefaultHttpMessageConverters(converters);
    }

    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	jsonConverter.setSupportedMediaTypes(WebAppInitializer.MEDIA_TYPE);
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	jsonConverter.setObjectMapper(objectMapper);
	return jsonConverter;
    }

    private FormHttpMessageConverter createFormHttpMessage(final List<HttpMessageConverter<?>> converters) {
	converters.add(createByteArrayHttpConverter());
	final FormHttpMessageConverter converter = new FormHttpMessageConverter();
	final MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
	converter.setSupportedMediaTypes(Arrays.asList(mediaType));
	return converter;
    }

    /** Configure our converter for String */
    private HttpMessageConverter<?> createStringHttpConverter() {
	final StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
	stringHttpMessageConverter.setSupportedMediaTypes(WebAppInitializer.MEDIA_TYPE);
	return stringHttpMessageConverter;
    }

    /** Configure our converter for http form */
    private HttpMessageConverter<?> formHttpConverter() {
	final FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
	formHttpMessageConverter.setSupportedMediaTypes(WebAppInitializer.MEDIA_TYPE);
	return formHttpMessageConverter;
    }

    /** Configure our converter for resource */
    private HttpMessageConverter<?> createResourceHttpMessageConverter() {
	final ResourceHttpMessageConverter byteArrayHttp = new ResourceHttpMessageConverter();
	byteArrayHttp.setSupportedMediaTypes(WebAppInitializer.MEDIA_TYPE);
	return byteArrayHttp;
    }

    /** Configure our converter for AllEncompassingFormHttp */
    private HttpMessageConverter<?> createAllEncompassingFormHttpMessageConverter() {
	final AllEncompassingFormHttpMessageConverter byteArrayHttp = new AllEncompassingFormHttpMessageConverter();
	byteArrayHttp.setSupportedMediaTypes(WebAppInitializer.MEDIA_TYPE);
	return byteArrayHttp;
    }

    /** Configure our converter for AllEncompassingFormHttp */
    private HttpMessageConverter<?> createByteArrayHttpConverter() {
	final ByteArrayHttpMessageConverter byteArrayHttp = new ByteArrayHttpMessageConverter();
	return byteArrayHttp;
    }

}
