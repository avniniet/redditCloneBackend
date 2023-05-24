/**
 * 
 */
package in.project.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import in.project.redditclone.dto.PostRequest;
import in.project.redditclone.dto.PostResponse;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.Subreddit;
import in.project.redditclone.model.User;
import in.project.redditclone.repository.CommentRepository;
import in.project.redditclone.repository.PostRepository;
import in.project.redditclone.service.AuthService;

/**
 * @author prostriker23
 *
 */
@Mapper(componentModel = "spring")
public abstract class PostMapper {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private AuthService authService;

	@Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target="voteCount", constant = "0")
	abstract
	public Post map(PostRequest postRequest, Subreddit subreddit, User user);
	
	
	
	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source = "user.userName")
	@Mapping(target="commentCount", expression="java(commentCount(post))")
	@Mapping(target="duration", expression="java(getDuration(post))")
	
	public abstract PostResponse mapToDto(Post post)	;
	
	Integer commentCount(Post post) {
		return commentRepository.findAllByPost(post).size();
	}
	
	String getDuration(Post post) {
		return TimeAgo.using(post.getCreateDate().toEpochMilli());
	}
	
}
