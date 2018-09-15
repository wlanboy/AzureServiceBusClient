package com.wlanboy.demo.model;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessageParameters extends ResourceSupport {

	public MessageParameters(Long id, String text) {
		this.identifier = id;
		this.message = text;
	}

	private Long identifier;
	private String message;
}
