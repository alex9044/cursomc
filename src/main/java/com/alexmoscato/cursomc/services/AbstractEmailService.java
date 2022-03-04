package com.alexmoscato.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.alexmoscato.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());// Destinatario
		sm.setFrom(sender);// Remetente
		sm.setSubject("Pedido Confirmado! Codigo: " + obj.getId());// Assunto
		sm.setSentDate(new Date(System.currentTimeMillis()));// Data do email
		sm.setText(obj.toString());// Corpo
		return sm;
	}

}
