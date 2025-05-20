package ProjectConsole.Utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.sql.Struct;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class EmailCheck {

    // function to generate a verification code
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // generate a 6 digits code
        return String.valueOf(code);
    }

    //function to send email with verification code
    public boolean sendVerificationEmail(String recipientEmail) {
        Scanner scanner=new Scanner(System.in);
        boolean flage=false;
        boolean code=false;
        String senderEmail = "campusgo.app@gmail.com"; // a email of app
        String senderPassword = "ochp cbcx fgic attl"; // password for email to use for api (I Get It From Google)

        // make a code
        String verificationCode = generateVerificationCode();

        // a setting for my email from the library to connect with server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // make a session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // make  a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("التحقق من البريد الإلكتروني");

            // contain of the email
            String emailContent = "<h1>مرحبا!</h1><p>يرجى إدخال رمز التحقق التالي للتحقق من بريدك الإلكتروني: </p>"
                    + "<h2>" + verificationCode + "</h2>";
            message.setContent(emailContent, "text/html; charset=UTF-8");

            // sending the message
            Transport.send(message);
            System.out.println("تم إرسال رمز التحقق إلى " + recipientEmail);
            flage=true;// the email sent
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("not sent");
            return flage=false; // get wrong with sent the email
        }
        if (flage==true){
            System.out.println("enter the code that sent to you : ");
            String enteredcode= scanner.nextLine();
            if (verifyCode(enteredcode,verificationCode)){
                System.out.println("email verified");
                code=true;
            }else {
                System.out.println("wrong code");
                code=false;
            }
        }
        return code;
    }
    // function to compare between the entered code and a sent code
    public  boolean verifyCode(String enteredCode, String generatedCode) {
        Scanner scanner=new Scanner(System.in);
        System.out.println();
        return enteredCode.equals(generatedCode);
    }
}