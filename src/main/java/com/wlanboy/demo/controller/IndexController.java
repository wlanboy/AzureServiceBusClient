package com.wlanboy.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wlanboy.demo.servicebus.ServiceBusClient;

@Controller
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	ServiceBusClient serviceBus;
	
    @RequestMapping("/")
    public String index(Model model) {
    	
    	String error = null;
		String latestMessage = null;
			
		try {
			latestMessage = serviceBus.getLatestMessage();
		}
		catch (Exception ex) {
			logger.error("group", ex);
			error = ex.getMessage();
		}
		
        model.addAttribute("latestMessage", latestMessage);
        model.addAttribute("error", error);
        return "index";
    }
    
    @PostMapping("/newmessage")
    public String newmessage(@RequestBody MultiValueMap<String, String> formData, Model model) {
    	String error = null;
		String latestMessage = null;
		String text = null;
			
		try {
			text = formData.getFirst("newmessagetext");
			serviceBus.sendQueueMessage(text);
			latestMessage = serviceBus.getLatestMessage();
		}
		catch (Exception ex) {
			logger.error("group", ex);
			error = ex.getMessage();
		}
		
        model.addAttribute("latestMessage", latestMessage);
        model.addAttribute("error", error);
        
        return "index";
    }    
    
}
