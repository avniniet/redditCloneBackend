/**
 * 
 */
package in.project.redditclone.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author prostriker23
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequest {
	
	@NotBlank
	private String refreshToken;
	private String userName;

}
