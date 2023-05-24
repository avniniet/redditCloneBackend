/**
 * 
 */
package in.project.redditclone.service;

import static in.project.redditclone.model.VoteType.UPVOTE;

import java.util.Optional;

import org.springframework.stereotype.Service;

import in.project.redditclone.dto.VoteDto;
import in.project.redditclone.exception.PostNotFoundException;
import in.project.redditclone.exception.SpringRedditException;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.Vote;
import in.project.redditclone.repository.PostRepository;
import in.project.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
/**
 * @author prostriker23
 *
 */
@Service
@AllArgsConstructor
public class VoteService {

	private final PostRepository postRepository;
	private final VoteRepository voteRepository;
	private final AuthService authService;

	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(
				() -> new PostNotFoundException("Post for given post id " + voteDto.getPostId() + " is not present"));

		Optional<Vote>  voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already upvote for this post");
		}
		
		if(UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount()+1);
		}
		else {
			post.setVoteCount(post.getVoteCount()-1);
		}
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
		
	}
	
	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}

}







