/**
 * 
 */
package in.project.redditclone.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import in.project.redditclone.dto.NotificationEmail;
import in.project.redditclone.exception.SpringRedditException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lenovo1
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	
	private final MailContentBuilder mailContentBuilder;
	private final JavaMailSender javaMailSender;
	
	@Async
	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper  = new MimeMessageHelper(mimeMessage) ;
			messageHelper.setFrom("avnish0813313023@gmail.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			log.info("Recipient mail"+notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody())	);
		};
		try {
			javaMailSender.send(messagePreparator);
			log.info("Activation email sent");
		}
		catch(MailException mailException) {
			log.error(mailException.getMessage());
			throw new SpringRedditException(mailException.getMessage());
		}
	}

}
