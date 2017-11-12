package io.junq.examples.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:web-${webTarget:local}.properties")
@ComponentScan({ "com.baeldung.um.model" })
public class UserCenterLiveTestConfiguration {

}
