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
public class PostResponse {
	
	private Integer postId;
	private String description;
	private String postName;
	private String url;
	private String subredditName;
	private String userName;
	
	//new fields
	private Integer voteCount;
	private Integer commentCount;
	private String duration;

}
