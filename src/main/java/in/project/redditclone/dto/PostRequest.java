/**
 * 
 */
package in.project.redditclone.dto;

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
public class PostRequest {
	
	private Integer postId;
	private String postName;
	private String subredditName;
	private String description;
	private String url;
	

}
