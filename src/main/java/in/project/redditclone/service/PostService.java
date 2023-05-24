/**
 * 
 */
package in.project.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.project.redditclone.dto.PostRequest;
import in.project.redditclone.dto.PostResponse;
import in.project.redditclone.exception.PostNotFoundException;
import in.project.redditclone.exception.SubredditNotFoundException;
import in.project.redditclone.mapper.PostMapper;
import in.project.redditclone.mapper.PostMapperImpl;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.Subreddit;
import in.project.redditclone.model.User;
import in.project.redditclone.repository.PostRepository;
import in.project.redditclone.repository.SubredditRepository;
import in.project.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author prostriker23
 *
 */
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
	
	private final PostRepository postRepository;
	private final SubredditRepository subredditRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	
	public PostResponse save(PostRequest postRequest) {
		
		
		
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
		.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));		
		User currentUser = authService.getCurrentUser();
		
		Post post = postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
		return postMapper.mapToDto(post);
		
		
	}
	
	@Transactional(readOnly = true)
	public PostResponse getPost(Long postId) {
		
		Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId.toString()));
		return postMapper.mapToDto(post);
		
	}
	
	
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		
		List<PostResponse> postResponse = postRepository.findAll().stream().map(postMapper::mapToDto)
				.collect(Collectors.toList());
		return postResponse;
		
	}

	public List<PostResponse> getPostBySubreddit(Long subredditId) {
	    Subreddit subreddit = subredditRepository.findById(subredditId)
	    		.orElseThrow(() -> new SubredditNotFoundException("There is no subreddit with subreddit id "+subredditId));
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		List<PostResponse> postResponseList = posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	    return postResponseList;
	    
	}

	public List<PostResponse> getPostByUserName(String userName) {
		
	    User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
		List<PostResponse> postResponseList = postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
		return postResponseList;
	}
	

	
	

}
