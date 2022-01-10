package com.example.demo.service;

public class MessagingService {
	private String url;
	private int port;
	
	public MessagingService(String url, int port) {
		this.url = url;
		this.port = port;
	}
	
	public void sendMessage(String msg) {
		System.out.println("message " + msg + "  sent to " +url);
	}
}
