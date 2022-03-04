package com.alexmoscato.cursomc.services;


import org.springframework.mail.SimpleMailMessage;

import com.alexmoscato.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage mds);
}
