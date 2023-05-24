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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {
  private Long id;
  private String name;
  private String description;
  private Integer numberOfPosts;
}
