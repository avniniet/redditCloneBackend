/**
 * 
 */
package in.project.redditclone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.project.redditclone.dto.CommentDto;
import in.project.redditclone.service.CommentService;
import lombok.AllArgsConstructor;

/**
 * @author prostriker23
 *
 */
@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
	
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
		CommentDto commentResponse = commentService.save(commentDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
	}
	
	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getAllCommentForPost(@PathVariable Long postId) {
		List<CommentDto> commentsByPost = commentService.getAllCommentsByPost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(commentsByPost)	;
	}
	
	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<CommentDto>> getAllCommentForUser(@PathVariable String userName) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByUserName(userName))	;
	}

}
