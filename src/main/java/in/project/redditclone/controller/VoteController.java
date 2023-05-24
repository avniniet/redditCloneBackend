/**
 * 
 */
package in.project.redditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.project.redditclone.dto.VoteDto;
import in.project.redditclone.service.VoteService;
import lombok.AllArgsConstructor;

/**
 * @author prostriker23
 *
 */
@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {
	private final VoteService voteService;
	
	
	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
		voteService.vote(voteDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
