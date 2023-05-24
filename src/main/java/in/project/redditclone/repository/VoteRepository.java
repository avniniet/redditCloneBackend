/**
 * 
 */
package in.project.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.project.redditclone.model.Post;
import in.project.redditclone.model.User;
import in.project.redditclone.model.Vote;

/**
 * @author lenovo1
 *
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
