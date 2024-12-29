package com.yassir.budgetbuddy.email;

import com.yassir.budgetbuddy.report.controller.ReportResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> proporties = new HashMap<>();
        proporties.put("username", username);
        proporties.put("confirmationUrl", confirmationUrl);
        proporties.put("activation_code", activationCode);

        // send the properties to our template, context from thymeleaf
        Context context = new Context();
        context.setVariables(proporties);

        helper.setFrom("noreply@budgetBuddy.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);

    }

    public void sendOtherEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String url,
            String subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "welcome-email";
        } else {
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> proporties = new HashMap<>();
        proporties.put("username", username);
        proporties.put("url", url);

        // send the properties to our template, context from thymeleaf
        Context context = new Context();
        context.setVariables(proporties);

        helper.setFrom("noreply@budgetBuddy.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);

    }


    public void sendEmailWithMonthlyReport(
            List<String> to,
            List<String> username,
            EmailTemplateName emailTemplate,
            String url,
            String subject,
            BigDecimal totalExpenses,
            BigDecimal totalIncome,
            String month,
            int year
    ) throws MessagingException {
        String templateName = (emailTemplate == null) ? "monthly-report-email" : emailTemplate.name();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        // Ensure both lists are the same size
        if (to.size() != username.size()) {
            throw new IllegalArgumentException("Email list and username list must have the same size.");
        }

        // Prepare the email properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("url", url);
        properties.put("month", month);
        properties.put("year", year);
        properties.put("totalExpenses", totalExpenses);
        properties.put("totalIncome", totalIncome);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("noreply@budgetBuddy.com");
        helper.setSubject(subject);

        // Iterate over the list of recipients and send an email for each
        for (int i = 0; i < to.size(); i++) {
            String recipientEmail = to.get(i);
            String recipientUsername = username.get(i);

            // Set recipient-specific variables in the context
            properties.put("username", recipientUsername);

            // Recreate the context for each recipient with the updated properties
            context.setVariables(properties);

            String template = templateEngine.process(templateName, context);
            helper.setTo(recipientEmail);
            helper.setText(template, true);

            // Send the email
            mailSender.send(mimeMessage);
        }
    }

    public void sendEmailWithYearlyReport(
            List<String> to,
            List<String> username,
            EmailTemplateName emailTemplate,
            String url,
            String subject,
            BigDecimal totalExpenses,
            BigDecimal totalIncome,
            BigDecimal savingRate,
            int year
    ) throws MessagingException {
        String templateName = (emailTemplate == null) ? "monthly-report-email" : emailTemplate.name();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        // Ensure both lists are the same size
        if (to.size() != username.size()) {
            throw new IllegalArgumentException("Email list and username list must have the same size.");
        }

        // Prepare the email properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("url", url);
        properties.put("year", year);
        properties.put("totalYearlyExpenses", totalExpenses);
        properties.put("totalYearlyIncome", totalIncome);
        properties.put("savingRate", savingRate);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("noreply@budgetBuddy.com");
        helper.setSubject(subject);

        // Iterate over the list of recipients and send an email for each
        for (int i = 0; i < to.size(); i++) {
            String recipientEmail = to.get(i);
            String recipientUsername = username.get(i);

            // Set recipient-specific variables in the context
            properties.put("username", recipientUsername);

            // Recreate the context for each recipient with the updated properties
            context.setVariables(properties);

            String template = templateEngine.process(templateName, context);
            helper.setTo(recipientEmail);
            helper.setText(template, true);

            // Send the email
            mailSender.send(mimeMessage);
        }
    }



}
