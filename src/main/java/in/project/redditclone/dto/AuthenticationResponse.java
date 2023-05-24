/**
 * 
 */
package in.project.redditclone.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lenovo1
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
	
	private String authenticationToken;
	private String userName;
	private Instant expiresAt;
	private	String refreshToken;
			

}
