/**
 * 
 */
package in.project.redditclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.project.redditclone.model.Comment;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.User;

/**
 * @author lenovo1
 *
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPost(Post post);

	List<Comment> findAllByUser(User user);

}
