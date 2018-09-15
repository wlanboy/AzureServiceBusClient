package com.wlanboy.demo.servicebus;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.microsoft.azure.servicebus.ExceptionPhase;
import com.microsoft.azure.servicebus.IMessage;
import com.microsoft.azure.servicebus.IMessageHandler;

@Component
public class MessageHandler implements IMessageHandler {

	public ArrayList<String> messages = new ArrayList<String>();

	private static final Logger logger = Logger.getLogger(MessageHandler.class.getCanonicalName());

	public CompletableFuture<Void> onMessageAsync(IMessage message) {
		final String messageString = new String(message.getBody(), StandardCharsets.UTF_8);
		logger.info("Received message: " + messageString);
		messages.add(messageString);
		return CompletableFuture.completedFuture(null);
	}

	public void notifyException(Throwable exception, ExceptionPhase phase) {
		logger.severe(phase + " encountered exception:" + exception.getMessage());
	}

	public String getLatestMessage() {
		String firstElement = null;
		if (messages.isEmpty()) {
			firstElement = "NO MESSAGES";
		} else {
			firstElement = messages.get(0);
			messages.remove(0);
		}
		return firstElement;
	}
}
