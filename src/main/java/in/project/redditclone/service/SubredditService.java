/**
 * 
 */
package in.project.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.project.redditclone.dto.SubredditDto;
import in.project.redditclone.mapper.SubredditMapper;
import in.project.redditclone.model.Subreddit;
import in.project.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lenovo1
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	// private final SubredditMapper subredditMapper;

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit = subredditRepository.save(mapSubredditDto(subredditDto));
		subredditDto.setId(subreddit.getId());
		subredditDto.setNumberOfPosts(subreddit.getPosts() != null ? subreddit.getPosts().size() : 0);
		return subredditDto;

	}

	
	 private Subreddit mapSubredditDto(SubredditDto subredditDto) { return
	  Subreddit.builder() .description(subredditDto.getDescription())
	  .name(subredditDto.getName()) .build();
	  
	  }
	 

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return (List<SubredditDto>) subredditRepository.findAll().stream().map(this::mapToDto)
				.collect(Collectors.toList());

	}

	
	  private SubredditDto mapToDto(Subreddit subreddit) { return
	  SubredditDto.builder() .name(subreddit.getName())
	  .description(subreddit.getDescription())
	  .numberOfPosts(subreddit.getPosts().size()) .build(); }
	 

}
