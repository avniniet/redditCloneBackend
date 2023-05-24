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
@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoginRequest {
	
	private String userName;
	private String password;

}
