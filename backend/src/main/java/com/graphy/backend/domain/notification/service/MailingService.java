package com.graphy.backend.domain.notification.service;

import com.graphy.backend.domain.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailingService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    private static final String EMAIL_TITLE_PREFIX = "[Graphy] ";

    @Async
    public void sendNotificationEmail(Notification notification) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        messageHelper.setSubject(EMAIL_TITLE_PREFIX + notification.getContent());
        messageHelper.setTo(notification.getMember().getEmail());

        HashMap<String, String> emailValues = new HashMap<>();
        emailValues.put("content", notification.getContent());
        String text = setContext(emailValues);

        messageHelper.setText(text, true);

        messageHelper.addInline("logo", new ClassPathResource("static/images/image-2.png"));
        messageHelper.addInline("notice-icon", new ClassPathResource("static/images/image-1.png"));

        javaMailSender.send(message);
    }

    private String setContext(Map<String, String> emailValues) {
        Context context = new Context();
        emailValues.forEach(context::setVariable);
        return templateEngine.process("email/index", context);
    }
}
