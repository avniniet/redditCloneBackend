/**
 * 
 */
package in.project.redditclone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author lenovo1
 *
 */

@Entity
@Table(name = "token")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String token;
	
	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	
	
	private Instant expiryDate;

}
