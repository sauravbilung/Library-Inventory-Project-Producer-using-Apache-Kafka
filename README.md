**Information :**

This is a microservice for the producer part of the project **Library Inventory Project using Kafka**. It takes a "library event" (new book information/information to be updated for an existing book) through the APIs and then add the record(event) to the "libraray-events" topic. The consumer then reads the data from the topic. The reading of data from the topic by the consumer happens in real time. By default this application runs on port 8090 and the API call is Asynchronous. The port number can be changed in application.properties file and API call properties can be changed in LibraryEventsController.class. The code to make the API call Synchronous is also present in the project inside the comments. Uncomment it and comment the Asynchronous part for the change.


**Steps for running the project :**

1. Download the project, compile and create the jar file, and deploy using the command "java -jar jar_file_name"

2. Run the kafka console consumer. It is the one which is going to consume the records from Kafka topic.

3. Open the postman application and send a post/put request to ``` "http://localhost:8090/v1/libraryevent" ``` with the body as follows : 

```
{
    "libraryEventId": 123,
    "book": {
        "bookId": 2,
        "bookName": "The Power of Your subconscious Mind",
        "bookAuthor": "Dr. Joseph Murphy"
    }
}
```
Post verb for new record insertion.
Put verb for updation of existing record.

4. If the request is successfully recieved and processed we will see a response body and we will also find the 
Kafka Console Consumer reading the records from the topic.

5. Event(Record) in the topic will be retained for a certain time.

**Developed in Java version 8 and Spring Boot version 2**
Consumer project related to it : https://github.com/sauravbilung/Library-Inventory-Project-Consumer-using-Apache-Kafka



 






