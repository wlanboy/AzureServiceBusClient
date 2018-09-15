package com.wlanboy.demo.servicebus;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.MessageHandlerOptions;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;

@Service
public class ServiceBusClient {

	private static final Logger logger = Logger.getLogger(ServiceBusClient.class.getCanonicalName());

	@Autowired
	private QueueClient queueClient;
	@Autowired
	private MessageHandler handler;
	private boolean test = true;

	public ServiceBusClient() {

	}

	public void sendQueueMessage(String text) throws ServiceBusException, InterruptedException {
		final String messageBody = text;
		logger.info("Sending message: " + messageBody);
		final Message message = new Message(messageBody.getBytes(StandardCharsets.UTF_8));
		queueClient.send(message);
	}

	public String getLatestMessage() {
		if (test) {
			try {
				receiveQueueMessage();
				test = false;
			} catch (ServiceBusException | InterruptedException e) {
				logger.severe(e.getMessage());
			}
		}

		String text = handler.getLatestMessage();

		return text;
	}

	private void receiveQueueMessage() throws ServiceBusException, InterruptedException {
		queueClient.registerMessageHandler(handler, new MessageHandlerOptions());

		//TimeUnit.SECONDS.sleep(5);
		//queueClient.close();
	}

}
