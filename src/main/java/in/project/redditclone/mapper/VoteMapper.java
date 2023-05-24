/**
 * 
 */
package in.project.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import in.project.redditclone.dto.VoteDto;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.User;
import in.project.redditclone.model.Vote;
import in.project.redditclone.model.VoteType;

/**
 * @author prostriker23
 *
 */
@Mapper(componentModel = "spring")
public interface VoteMapper {

	@Mapping(target ="voteType", source="voteType")
	@Mapping(target="post", source="post")
	@Mapping(target="user", source="user")
	Vote map(VoteType voteType, Post post, User user);
	
	
	@Mapping(target="voteType", expression="java(voteType.toString())")
	@Mapping(target="postId", source="post.getPostId()")
	@Mapping(target="userName", source="user.getUserName()")
	@Mapping(target="voteId", ignore = true)
	VoteDto mapToDto(Vote vote);
}
