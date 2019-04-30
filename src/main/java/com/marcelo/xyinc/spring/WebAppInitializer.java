package com.marcelo.xyinc.spring;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
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

import com.marcelo.xyinc.filters.CharacterEncodingFilter;

@Configuration(value = "webapp")
@EnableWebMvc
@EnableScheduling
public class WebAppInitializer implements WebApplicationInitializer {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final List<MediaType> MEDIA_TYPE = Arrays.asList(
	    new MediaType("application", "json", WebAppInitializer.UTF8),
	    new MediaType("text", "plain", WebAppInitializer.UTF8),
	    new MediaType("text", "html", WebAppInitializer.UTF8),
	    new MediaType("multipart", "form-data", WebAppInitializer.UTF8),
	    new MediaType("application", "x-www-form-urlencoded", WebAppInitializer.UTF8));

    public static final String REST_MAP = "/rest/";

    public static final String SYSTEMWEB_ACECCESS = "/{sys_can_aceccess}";

    public static final String SYSTEMWEB_ACECCESS_TEXT = "sys_can_aceccess";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
	final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	// define listener to spring for contexto
	servletContext.addListener(new ContextLoaderListener(context));
	servletContext.addListener(new RequestContextListener());
	context.setServletContext(servletContext);

	servletContext.addFilter("encoding", new CharacterEncodingFilter());

	// dipacher Spring
	final DispatcherServlet dispatcher = new DispatcherServlet(context);
	dispatcher.setDispatchTraceRequest(true);
	dispatcher.setThrowExceptionIfNoHandlerFound(true);
	dispatcher.setPublishContext(true);
	final Dynamic dynamic = servletContext.addServlet("dispatcher", dispatcher);
	dynamic.addMapping(WebAppInitializer.REST_MAP + "*");
	dynamic.setAsyncSupported(true);
	dynamic.setLoadOnStartup(1);
    }

    /** Configure our restTemplate */
    @Bean(name = "restTemplateAll")
    @Scope("prototype")
    @Autowired
    public RestTemplate restTemplateAll(
	    @Qualifier("httpComponentsClientHttpRequestFactoryAll") final HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory,
	    @Qualifier("mappingJackson2HttpMessageConverter") final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
	final RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
	restTemplate.setMessageConverters(buildListConverters(mappingJackson2HttpMessageConverter));
	return restTemplate;
    }

    /** Create converts for our system */
    private List<HttpMessageConverter<?>> buildListConverters(
	    final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
	final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
	// string
	messageConverters.add(createStringHttpConverter());
	// http
	messageConverters.add(formHttpConverter());
	// resource
	messageConverters.add(createResourceHttpMessageConverter());
	// AllEncompassingFormHttp
	messageConverters.add(createAllEncompassingFormHttpMessageConverter());
	if (mappingJackson2HttpMessageConverter != null) {
	    messageConverters.add(mappingJackson2HttpMessageConverter);
	}
	// byte array
	messageConverters.add(createByteArrayHttpConverter());
	final FormHttpMessageConverter converter = new FormHttpMessageConverter();
	final MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
	converter.setSupportedMediaTypes(Arrays.asList(mediaType));
	messageConverters.add(converter);
	return messageConverters;
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
