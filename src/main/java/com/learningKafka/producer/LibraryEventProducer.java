package com.learningKafka.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningKafka.domain.LibraryEvent;

//Component annotation indicates that this class is a candidate for auto detection
@Component
public class LibraryEventProducer {

	// Hard coding topic name
	String topic = "library-events";

	@Autowired // template for executing high level operations
	KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	ObjectMapper objectMapper;

	// Approach 1 (Asynchronous Way)
	public void sendLibraryEventAsynchronous(LibraryEvent libraryEvent) throws JsonProcessingException {

		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent); // Value will be send as json string to the topic

		// sending the data to the default kafka topic configured in application.yml
		// ListenableFuture indicates what will be send to topic in the future
		// by future I mean the time at which data will be sent to kafka topic
		// when the batch is full or linger.ms condition is met
		// sendResult is actual result sent from kafka topic
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);

		// this will be called when the future happens.
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			// and upon the success or failure of the future the methods will be called

			@Override
			public void onFailure(Throwable ex) {

				// called when data is not successfully published to Kafka topic
				handleFailure(key, value, ex);
			}

			@Override
			public void onSuccess(SendResult<Integer, String> result) {

				// called when data is successfully published to Kafka topic
				handleSuccess(key, value, result);
			}

		});
	}

	// Approach 2 (Synchronous Way)
	public SendResult<Integer, String> sendLibraryEventSynchronous(LibraryEvent libraryEvent)
			throws JsonProcessingException {

		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);
		SendResult<Integer, String> sendResult = null;

		try {
			sendResult = kafkaTemplate.sendDefault(key, value).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return sendResult;
	}

	// Approach 3 (Just a different technique for publishing in Approach 1)
	public void sendLibraryEventUsingProducerRecord(LibraryEvent libraryEvent) throws JsonProcessingException {

		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);

		// ProducerRecord : A key/value pair to be sent to Kafka. This consists of a
		// topic name to which the record is being sent, an optional partition number,
		// and an optional key and value.
		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);

		// the above two approaches read the topic name from application.yml file but
		// through this approach(producer record) we send the data to N number of topics
		// this happens because of send() of KafkaTemple. Inside it we manually set
		// the topic name
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onFailure(Throwable ex) {

				handleFailure(key, value, ex);
			}

			@Override
			public void onSuccess(SendResult<Integer, String> result) {

				handleSuccess(key, value, result);
			}

		});
	}

	// Producer Record is generic class <Key,Value>.
	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

		// Adding header to the ProducerRecord.
		// Kafka Headers add additional information to the record that
		// we are publishing to the topic.
		List<Header> recordHeaders= new ArrayList<Header>();
		recordHeaders.add(new RecordHeader("event-source", "scanner".getBytes()));
		
		return new ProducerRecord<Integer, String>(topic, null, key, value, recordHeaders);
	}

	protected void handleFailure(Integer key, String value, Throwable ex) {

		System.out.println("Error sending the message to kafka topic " + ex.getMessage());

		try {
			throw ex;
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {

		System.out.println("Message sent successfully for the key :" + key + " and the value is :" + value
				+ ", partition is :" + result.getRecordMetadata().partition());

	}

}
