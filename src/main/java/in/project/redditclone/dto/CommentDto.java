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
 * @author prostriker23
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
	
	private Long id;	
	private String text;	
	private Long  postId;	
	private Instant createDate;
	private String userName;

}
