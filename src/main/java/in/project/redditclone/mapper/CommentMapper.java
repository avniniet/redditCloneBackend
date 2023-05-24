/**
 * 
 */
package in.project.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import in.project.redditclone.dto.CommentDto;
import in.project.redditclone.model.Comment;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.User;

/**
 * @author prostriker23
 *
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	@Mapping(target = "postId", expression="java(comment.getPost().getPostId())")
	@Mapping(target = "userName", expression="java(comment.getUser().getUserName())")
	CommentDto mapToDto(Comment comment);
	
	
	@Mapping(target = "post", source="post")
	@Mapping(target = "user", source="user")
	@Mapping(target = "createDate", expression="java(java.time.Instant.now())")
	@Mapping(target = "id", ignore = true)
	Comment map(CommentDto commentDto, Post post, User user);

}
