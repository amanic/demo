package com.example.demo.conf;

import org.springframework.context.ApplicationEvent;

public class EmailEvent extends ApplicationEvent {

	public String address;
	public String text;

	public EmailEvent(Object source) {
		super(source);
	}

	public EmailEvent(Object source, String address, String text) {
		super(source);
		this.address = address;
		this.text = text;
	}

	public void print() {
		System.out.println("hello spring event!");
	}

}