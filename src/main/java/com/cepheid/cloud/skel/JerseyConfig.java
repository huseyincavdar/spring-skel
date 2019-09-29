package com.cepheid.cloud.skel;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.stereotype.Component;

import com.cepheid.cloud.skel.controller.ItemController;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@Component
public class JerseyConfig extends ResourceConfig {
  public JerseyConfig() {
    packages(ItemController.class.getPackage().getName());
    
    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    register(RolesAllowedDynamicFeature.class);

    packages("org.glassfish.jersey.examples.multipart");
    register(MultiPartFeature.class);

    
    configureSwagger();    
    
  }
  
  
  private BeanConfig configureSwagger() {
    // support Swagger
    register(ApiListingResource.class);
    register(SwaggerSerializers.class);
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("API " + "1.0");
    beanConfig.setSchemes(new String[] { "https" });
    beanConfig.setHost("localhost:9443");
    beanConfig.setBasePath("/app");
    // comma separated string
    beanConfig.setResourcePackage(ItemController.class.getPackage().getName());
    beanConfig.setPrettyPrint(true);
    beanConfig.setScan(true);
    beanConfig.setTitle("REST API");
    beanConfig.setDescription("The REST API is used from the JavaScript web GUI.");
    return beanConfig;
  }  
  
}