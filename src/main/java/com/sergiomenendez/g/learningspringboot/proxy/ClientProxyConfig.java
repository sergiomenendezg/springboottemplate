package com.sergiomenendez.g.learningspringboot.proxy;

import javax.ws.rs.client.ClientBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProxyConfig {

  @Value("${users.api.url.v1}")
  private String usersEndpointUrl;

  @Bean
  public UserResourceV1 getUserResourceV1() {
    /*
     * Client client = ResteasyClientBuilder.newBuilder().build();
     * WebTarget target = client.target(this.usersEndpointUrl);
     * return target.register(UserResourceV1.class);
     */

    ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
    ResteasyWebTarget target = client.target(this.usersEndpointUrl);
    return target.proxy(UserResourceV1.class);
  }
}
