package com.learningKafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningKafka.domain.LibraryEvent;

import lombok.extern.slf4j.Slf4j;

//Component annotation indicates that this class is a candidate for auto detection and auto wiring
@Component
@Slf4j
public class LibraryEventProducer {

	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	ObjectMapper objectMapper;

	public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {

		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent); // Value will be send as json string to the topic

		// sending the data to the kafka topic
		kafkaTemplate.sendDefault(key, value);

	}
}
