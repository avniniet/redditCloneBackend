/**
 * 
 */
package in.project.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.project.redditclone.dto.CommentDto;
import in.project.redditclone.dto.NotificationEmail;
import in.project.redditclone.exception.PostNotFoundException;
import in.project.redditclone.mapper.CommentMapper;
import in.project.redditclone.model.Comment;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.User;
import in.project.redditclone.repository.CommentRepository;
import in.project.redditclone.repository.PostRepository;
import in.project.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;

/**
 * @author prostriker23
 *
 */
@Service
@AllArgsConstructor
public class CommentService {

	private final PostRepository postRepository;
	private final AuthService authService;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

	@Transactional
	public CommentDto save(CommentDto commentDto) {

		Post post = postRepository.findById(commentDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("There is no post for postId " + commentDto.getPostId()));

		Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
		Comment commentAfterSave = commentRepository.save(comment);
		String message = mailContentBuilder.build(post.getUser().getUserName() + " commented on your post");
		sendCommentNotification(message, post.getUser());
		return commentMapper.mapToDto(commentAfterSave);

	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUserName() + " commented on your post", user.getEmail(), message));

	}

	public List<CommentDto> getAllCommentsByPost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post for " + postId + " is not found"));

		List<CommentDto> commetListForGivenPost = commentRepository.findAllByPost(post).stream()
				.map(commentMapper::mapToDto).collect(Collectors.toList());
		return commetListForGivenPost;
	}

	public List<CommentDto> getAllCommentsByUserName(String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("user for " + userName + " is not found"));

		List<CommentDto> commetListForGivenUser = commentRepository.findAllByUser(user).stream()
				.map(commentMapper::mapToDto).collect(Collectors.toList());
		return commetListForGivenUser;
	}

}
