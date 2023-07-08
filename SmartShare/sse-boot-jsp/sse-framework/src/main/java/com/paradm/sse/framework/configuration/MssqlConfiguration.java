package com.paradm.sse.framework.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.paradm.sse")
@EnableTransactionManagement
public class MssqlConfiguration {

}
