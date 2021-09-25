package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.controller.ItemController;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.stereotype.Component;

@Deprecated(forRemoval = true)
/**
 * Controller configuration is handled by Spring
 * Swagger configuration has been moved to {@link SpringFoxConfig}
 * */
@Component
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    packages(ItemController.class.getPackage().getName());
    
    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    register(RolesAllowedDynamicFeature.class);

    packages("org.glassfish.jersey.examples.multipart");
    register(MultiPartFeature.class);

  }
}
