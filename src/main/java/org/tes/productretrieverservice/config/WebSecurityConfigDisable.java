package org.tes.productretrieverservice.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@Configuration
public class WebSecurityConfigDisable {
}
