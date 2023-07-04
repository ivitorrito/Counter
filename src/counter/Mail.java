
package counter;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	private final Properties props; 

	public Mail() {
           
		props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "212.227.15.158");
		props.put("mail.smtp.port", "587");

		props.put("from", "contador@copias.es");
		props.put("username", "sat@copiadorascostaluz.com");
		props.put("password", "Sat1971");
	}

	public void enviar(String to, String subject, String content) throws MessagingException {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        @Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(props.getProperty("from")));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);
		message.setText(content);
		Transport.send(message);

		
        }}


