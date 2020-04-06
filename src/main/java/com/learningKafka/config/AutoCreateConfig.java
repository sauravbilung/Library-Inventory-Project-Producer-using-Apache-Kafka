package com.learningKafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("local") // by this topic will be created only for local profile
public class AutoCreateConfig {

// We do not want to hard code the topic creation so
// this method it is not suited for prod environment	

	@Bean
	public NewTopic libraryEvents() {

		return TopicBuilder.name("library-events").partitions(3).replicas(3).build();
	}

}
