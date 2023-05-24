/**
 * 
 */
package in.project.redditclone.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.project.redditclone.model.User;
import in.project.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;

/**
 * @author lenovo1
 *
 */

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUserName(username);
		User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("No user found with user name "+username));
		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(), user.isEnabled(),true,true,true,getAuthorities("User"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
		
	}

}
