/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.uniworks.groupware.admin.common.util.ApplicationConfigReader;
import org.uniworks.groupware.admin.intercept.SessionCheckInterceptor;

/**
 * @author Park Chungwan
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "org.uniworks.groupware.admin.controller"}, useDefaultFilters = false, includeFilters = { @Filter(Controller.class),  @Filter(ControllerAdvice.class) })
@PropertySource(value = "classpath:application.properties")
@Import({SecurityConfig.class})
public class GroupwareConfig implements WebMvcConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(GroupwareConfig.class);	
	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getResolver() throws IOException {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSizePerFile(5 * 10 * 1024 * 1024);	//50MB
		resolver.setMaxUploadSize(5 * 10 * 1024 * 1024);
		resolver.setUploadTempDir(getUploadTempDir());
		resolver.setDefaultEncoding("UTF-8");
		return resolver;
	}
	
	@Bean
	public FileSystemResource getUploadTempDir() {
		String strTempDir = ApplicationConfigReader.get("file.tepmUpload");
		FileSystemResource tempDir = new FileSystemResource(strTempDir);
		return tempDir;
	}	
	
	@Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver exceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");
        exceptionMappings.put("org.springframework.web.multipart.MaxUploadSizeExceededException", "error/file_maxsize");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/403", "403");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
	}
			
	@Bean
    public InternalResourceViewResolver viewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);  
        return resolver;
    }

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	    localeChangeInterceptor.setParamName("lang");
	    return localeChangeInterceptor;
	}
	
	@Bean(name = "localeResolver")
	public LocaleResolver sessionLocaleResolver(){
	    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	    localeResolver.setDefaultLocale(new Locale("ko"));	      
	    return localeResolver;
	}
	
    /**
     * 인터셉터 추가
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new SessionCheckInterceptor());        
    }        
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
    	registry.addResourceHandler("/easyui/css/**").addResourceLocations("/easyui/css/").setCachePeriod(31556926);
    	registry.addResourceHandler("/easyui/js/**").addResourceLocations("/easyui/js/").setCachePeriod(31556926);    	
    }
    
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer
			.useJaf(true)
			.favorPathExtension(false)
			.favorParameter(false)
			.ignoreAcceptHeader(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJacksonHttpMessageConverter());
		converters.add(marshallingHttpMessageConverter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setPrettyPrint(true);
		ArrayList<MediaType> mediaTypeList = new ArrayList<MediaType>();
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		converter.setSupportedMediaTypes(mediaTypeList);
		return converter;
	}	
	
	@Bean 
	public MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
		MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter();
		converter.setMarshaller(jaxb2Marshaller());
		converter.setUnmarshaller(jaxb2Marshaller());
		return converter;
	}

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller()  {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(new String[] {
				"org.uniworks.groupware.admin.domain",
				"org.springframework.hateoas"});
		
		Map<String, Object> marshallerProperties = new HashMap<String, Object>();
		marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setMarshallerProperties(marshallerProperties);

		return marshaller;
	}
	
	@Bean
	public AcceptHeaderLocaleResolver acceptHeaderLocaleResolver() {
	    return new AcceptHeaderLocaleResolver();
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setDefaultEncoding("UTF-8");
	    messageSource.setBasename("classpath:messages");
	    messageSource.setFallbackToSystemLocale(false);
	    messageSource.setCacheSeconds(5);
	    return messageSource;
	}	
			
	//파일 다운로드 viewResolver
	@Bean
	public BeanNameViewResolver fileViewResolver() {
		BeanNameViewResolver fileViewResolver = new BeanNameViewResolver();
		fileViewResolver.setOrder(0);
		return fileViewResolver;
	}
	
	@Bean
    public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
        return new BeanNameUrlHandlerMapping();
    }	
}
