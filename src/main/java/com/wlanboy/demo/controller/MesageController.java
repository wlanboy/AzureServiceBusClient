package com.wlanboy.demo.controller;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import com.wlanboy.demo.model.MessageParameters;
import com.wlanboy.demo.servicebus.ServiceBusClient;

@RestController
public class MesageController {

	private static final Logger logger = Logger.getLogger(MesageController.class.getCanonicalName());

	static AtomicInteger counter = new AtomicInteger(0);

	@Autowired
	ServiceBusClient serviceBus;

	/**
	 * http://127.0.0.1:8110/message/hello-world
	 * http://127.0.0.1:8110/message
	 */
	@RequestMapping(value = "/message/{text}", method = RequestMethod.GET)
	public HttpEntity<MessageParameters> sendmessage(@PathVariable(value = "text", required = true) String text) {

		try {
			serviceBus.sendQueueMessage(text);
		} catch (ServiceBusException | InterruptedException e) {
			logger.severe(e.getMessage());
		}
		
		MessageParameters helloString = new MessageParameters(new Long(counter.incrementAndGet()), text);
		helloString.add(linkTo(methodOn(MesageController.class).sendmessage(text)).withSelfRel());

		logger.info("HelloParameters created.");
		return new ResponseEntity<MessageParameters>(helloString, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public HttpEntity<MessageParameters> nextmessage() throws InterruptedException {

		String text = null;
		
		text = serviceBus.getLatestMessage();
		
		MessageParameters helloString = new MessageParameters(new Long(counter.incrementAndGet()), text);
		helloString.add(linkTo(methodOn(MesageController.class).nextmessage()).withSelfRel());

		logger.info("HelloParameters created.");
		return new ResponseEntity<MessageParameters>(helloString, HttpStatus.OK);
	}

}