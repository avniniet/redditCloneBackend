/**
 * 
 */
package in.project.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lenovo1
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
	private String subject;
	private String recipient;
	private String body;

}
