/**
 * 
 */
package in.project.redditclone.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import in.project.redditclone.dto.SubredditDto;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.Subreddit;

/**
 * @author prostriker23
 *
 */
@Mapper(componentModel = "spring")
public interface SubredditMapper {
	
	@Mapping(target="numberOfPosts" , expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit) ;
	
	default Integer mapPosts(List<Post> posts) {
		return posts.size();
	}
	
	@InheritInverseConfiguration
	@Mapping(target="posts", ignore = true)
	Subreddit mapDtoToSubreddit(SubredditDto subredditDto);

}
