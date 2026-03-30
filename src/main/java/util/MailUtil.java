package util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class MailUtil {

    private static final String FROM_EMAIL = "kienltts01654@gmail.com";
    private static final String PASSWORD = "aros sxtg qyxc ibkb";

    public static void sendEmail(String toEmail, String newPassword) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.setProperty("mail.smtp.ssl.protocols","TLSv1.2");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("Khôi phục mật khẩu - Poly Coffee");
            message.setText("Mật khẩu mới của bạn là: " + newPassword);

            Transport.send(message);

        } catch (Exception e) {
            System.out.println("Lỗi gửi mail:");
            e.printStackTrace();
        }
    }
}