package com.yassir.budgetbuddy.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    // so the user won't wait until the email send, we need to enable it in the main class
    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplate == null){
            templateName = "confirm-email";
        }else{
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> proporties = new HashMap<>();
        proporties.put("username" , username);
        proporties.put("confirmationUrl" , confirmationUrl);
        proporties.put("activation_code" , activationCode);

        // send the properties to our template, context from thymeleaf
        Context context = new Context();
        context.setVariables(proporties);

        helper.setFrom("contact@budgetBuddy.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName,context);

        helper.setText(template,true);

        mailSender.send(mimeMessage);

    }

}
