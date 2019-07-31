package com.ruan.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.ruan.cursomc.domains.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);

}
