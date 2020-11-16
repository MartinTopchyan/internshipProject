package edu.training.interviewproject.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "edu.training.interviewproject.repository")
@ComponentScan(basePackages = { "edu.training.interviewproject.service" })
public class ESConfig {

  @Value ("${spring.data.elasticsearch.rest.uris}")
  private String es;


  @Bean
  RestHighLevelClient client() {
    ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo(es)
        .build();

    return RestClients.create(clientConfiguration)
        .rest();
  }


  @Bean
  public ElasticsearchOperations elasticsearchTemplate() {
    return new ElasticsearchRestTemplate(client());
  }
}
