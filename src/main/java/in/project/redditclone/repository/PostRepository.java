/**
 * 
 */
package in.project.redditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.project.redditclone.model.Post;
import in.project.redditclone.model.Subreddit;
import in.project.redditclone.model.User;

/**
 * @author lenovo1
 *
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);

}
