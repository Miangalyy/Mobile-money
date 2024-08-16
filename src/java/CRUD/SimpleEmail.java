/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import java.util.Properties;
import javax.mail.Session;

/**
 *
 * @author pc
 */
public class SimpleEmail {
        public void envoyerMail(String email, String subject, String body) {
        
        System.out.println("SimpleEmail Start");
        String smtpHostServer = "smtp.gmail.com";
        String emailID = email;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Utiliser votre adresse email et mot de passe
        final String username = "miantsoramiara2452@gmail.com";
        final String password = "moab ielo jnup ouot";

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        EmailUtil.sendEmail(session, emailID, subject, body);
    }
   /* public static void main(String[] args) {

        System.out.println("SimpleEmail Start");
        String smtpHostServer = "smtp.gmail.com";
        String emailID = "nomenjanaharyantsa10@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Utiliser votre adresse email et mot de passe
        final String username = "miantsoramiara2452@gmail.com";
        final String password = "moab ielo jnup ouot";

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        EmailUtil.sendEmail(session, emailID, "SimpleEmail Testing Subject", "SimpleEmail Testing Body");
    }*/
}
