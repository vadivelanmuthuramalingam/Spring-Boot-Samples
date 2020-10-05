package com.pst.httpclient.builder;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.pst.httpclient.builder.service.HttpClientTemplate;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;

@ConfigurationProperties(prefix = "http.rest-clients")
@Configuration("httpClientBuilder")
@Getter
@Setter
@ConditionalOnProperty(
	    value="http.rest-clients.enabled", 
	    havingValue = "true", 
	    matchIfMissing = false)

@Lazy
@Order(value = 3)
public class HttpClientServiceRegistry {

	@Autowired
    private ConfigurableBeanFactory beanFactory;
	
	@Autowired
	private ReleaseHttpConnection releaseHttpConnection;

	private List<HttpConnectionDetails> httpConnectionDetails;

	public HttpClientServiceRegistry(List<HttpConnectionDetails> httpConnectionDetails) {
		this.httpConnectionDetails = httpConnectionDetails;
	} 

	public HttpClientServiceRegistry() {

	}

	@Getter
	@Setter
	public static class HttpConnectionDetails {
		private String serviceProviderName;
		private String url ;
		private boolean conectionPooling;
		private Integer maxTotal;
		private Integer defaultMaxPerRoute;
		private Integer connectTimeout;
		private Integer connectionRequestTimeout;
		private Integer socketTimeout;
		private Integer validateAfterInactivity;
		private boolean sslEnabled;
		private String keystorePath;
		private boolean oauthEnabled;
		private String oauthReferenceBeanName;
		private String connType;
	}

	@PostConstruct
	public void buildApiClientAndRegisterServiceToBean()
	{
		List<HttpConnectionDetails> lstHttpConnectionDetails = this.getHttpConnectionDetails();
		
		if(lstHttpConnectionDetails != null && lstHttpConnectionDetails.size() > 0)
		{
			for(HttpConnectionDetails httpConnectionDetails: lstHttpConnectionDetails)
			{
				if(httpConnectionDetails.getConnType().toString().equalsIgnoreCase("OKHTTP"))
					registerOkHttpClient(httpConnectionDetails);
				else
					registerSpringHttpClient(httpConnectionDetails);

			}
		}
	}

	private void registerSpringHttpClient(HttpConnectionDetails httpConnectionDetails)
	{
		RestTemplate restTemplate = (RestTemplate) ApiClientConnectionFactory.createHttpConnectionTemplate(httpConnectionDetails)
				.build(httpConnectionDetails);
				
		ClientHttpRequestFactory clientHttpRequestFactory = restTemplate.getRequestFactory();
		
		releaseHttpConnection.setRestTemplates(restTemplate);
		
		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setBeanClass(HttpClientTemplate.class);

		ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
		constructorArgumentValues.addGenericArgumentValue(restTemplate);

		genericBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
		
		genericBeanDefinition.setScope("singleton");
		genericBeanDefinition.setAutowireCandidate(true);
		genericBeanDefinition.setAutowireMode(1);
		
		String beanId = httpConnectionDetails.getServiceProviderName();
		((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanId,
				genericBeanDefinition);

	}
	
	private void registerOkHttpClient(HttpConnectionDetails httpConnectionDetails)
	{
		OkHttpClient restTemplate = (OkHttpClient) ApiClientConnectionFactory.createHttpConnectionTemplate(httpConnectionDetails)
				.build(httpConnectionDetails);
				
		
		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setBeanClass(HttpClientTemplate.class);

		ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
		constructorArgumentValues.addGenericArgumentValue(restTemplate);

		genericBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
		
		genericBeanDefinition.setScope("singleton");
		genericBeanDefinition.setAutowireCandidate(true);
		genericBeanDefinition.setAutowireMode(1);
		
		String beanId = httpConnectionDetails.getServiceProviderName();
		((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanId,
				genericBeanDefinition);

	}
}