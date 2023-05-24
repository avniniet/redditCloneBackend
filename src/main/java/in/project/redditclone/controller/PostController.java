/**
 * 
 */
package in.project.redditclone.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.project.redditclone.dto.PostRequest;
import in.project.redditclone.dto.PostResponse;
import in.project.redditclone.service.PostService;
import lombok.AllArgsConstructor;

/**
 * @author prostriker23
 *
 */
@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	
	@PostMapping
	public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
		
		PostResponse postResponse = postService.save(postRequest);
		return ResponseEntity.ok(postResponse);
			 	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse>  getPost(@PathVariable Long id) {
		PostResponse postResponse=  postService.getPost(id);
		return ResponseEntity.ok(postResponse);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<PostResponse>> getAllPosts(){
		List<PostResponse> postResponseList =  postService.getAllPosts();
		return ResponseEntity.ok(postResponseList);
		
	}
	
	@GetMapping("/by-subreddit/{subredditId}")
	public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long subredditId){
		List<PostResponse> postResponseList =   postService.getPostBySubreddit(subredditId);
		return ResponseEntity.ok(postResponseList);
	}
	
	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable String userName){
		List<PostResponse> postResponseList =   postService.getPostByUserName(userName);
		return ResponseEntity.ok(postResponseList);
	}
	
	
	
	
	
	
	
	
	

}
