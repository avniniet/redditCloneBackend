/**
 * 
 */
package in.project.redditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.project.redditclone.exception.SpringRedditException;
import in.project.redditclone.model.RefreshToken;
import in.project.redditclone.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;

/**
 * @author prostriker23
 *
 */
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreateDate(Instant.now());

		return refreshTokenRepository.save(refreshToken);
	}
	
	public void validateRefreshToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(()-> new SpringRedditException("Invalid refresh token"));
	}
	
	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteByToken(token);
	}
}
