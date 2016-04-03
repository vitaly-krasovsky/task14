package com.epam.training;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author vkrasovsky
 */
//TODO: use ResourceConfig class instead of Application
@ApplicationPath("/webapi/*")
public class AppConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> returnValue = new HashSet<>();
        returnValue.add(ClientResource.class);
        returnValue.add(AccountResource.class);
        return returnValue;
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.server.provider.classnames", "org.glassfish.jersey.media.multipart.MultiPartFeature");
        return props;
    }
}
