package com.wlanboy.demo.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessageParameters extends RepresentationModel<MessageParameters> {

	public MessageParameters(Long id, String text) {
		this.identifier = id;
		this.message = text;
	}

	private Long identifier;
	private String message;
}
