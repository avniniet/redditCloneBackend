/**
 * 
 */
package in.project.redditclone.dto;

import in.project.redditclone.model.VoteType;
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
public class VoteDto {

	private VoteType  voteType;	
	private Long postId;
}
