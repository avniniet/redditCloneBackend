/**
 * 
 */
package in.project.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lenovo1
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
	private String userName;
	private String password;
	private String email;
	

}



