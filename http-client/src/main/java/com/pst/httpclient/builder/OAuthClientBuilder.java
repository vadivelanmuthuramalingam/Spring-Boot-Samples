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
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import com.pst.httpclient.builder.HttpClientServiceRegistry.HttpConnectionDetails;
import com.pst.httpclient.builder.config.oauth.OAuthClient;
import com.pst.httpclient.builder.service.HttpClientTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "http.oauth-clients")
@Configuration("oAuthConfiguration")
@ConditionalOnProperty(value = "http.oauth-clients.enabled", havingValue = "true", matchIfMissing = false)
@Order(value = 2)
public class OAuthClientBuilder {

	@Autowired
    private ConfigurableBeanFactory beanFactory;
	
	private List<Config> config;
	
	public OAuthClientBuilder(List<Config> config) {
		super();
		this.config = config;
	}

	public OAuthClientBuilder() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Getter
	@Setter
	public static class Config {
		private String providerName;
		private String url;
		private String userName;
		private String secrets;
		private boolean sslEnabled;
		private String keystorePath;
	}

	@PostConstruct
	public void buildOAuthClientAndRegisterServiceToBean() {
		List<Config> lstConfig = this.getConfig();

		if (lstConfig != null && lstConfig.size() > 0) {
			for (Config config : lstConfig) {

				String serviceProviderName = config.getProviderName();

				String value = new String("Constructer Value - " + serviceProviderName);

				HttpConnectionDetails httpConnectionDetails = new HttpConnectionDetails();
				//fill http conn details for OAUTH connection
				
				RestTemplate restTemplate = (RestTemplate) ApiClientConnectionFactory
												.createHttpConnectionTemplate(httpConnectionDetails)
												.build(httpConnectionDetails); //vadivelan - pass http connection details
										
				HttpClientTemplate apiClient = new HttpClientTemplate(restTemplate);
				OAuthClient oAuthClient = new OAuthClient(apiClient, config);

				GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
				genericBeanDefinition.setBeanClass(OAuthClient.class);

				ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
				constructorArgumentValues.addGenericArgumentValue(restTemplate);

				genericBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);

				genericBeanDefinition.setScope("singleton");
				genericBeanDefinition.setAutowireCandidate(true);
				genericBeanDefinition.setAutowireMode(1);
				
				

				String beanId = "Beand ID vadivelan"; //httpConnectionDetails.getServiceProviderName();
				((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanId, genericBeanDefinition);

			}
		}

	}
}
