package com.lifesupport.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lifesupport.models.MailInfo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailer;

	public void send(MailInfo mail) throws MessagingException {
		MimeMessage message = mailer.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		helper.setReplyTo(mail.getFrom());

		if (mail.getCc() != null) {
			helper.setCc(mail.getCc());
		}
		if (mail.getBcc() != null) {
			helper.setBcc(mail.getBcc());
		}
		if (mail.getFile() != null) {

			String[] paths = mail.getFile().split(";");
			for (String path : paths) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}

		mailer.send(message);
	}
}
