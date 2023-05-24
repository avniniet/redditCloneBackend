/**
 * 
 */
package in.project.redditclone.service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.project.redditclone.dto.AuthenticationResponse;
import in.project.redditclone.dto.LoginRequest;
import in.project.redditclone.dto.NotificationEmail;
import in.project.redditclone.dto.RefreshTokenRequest;
import in.project.redditclone.dto.RegisterRequest;
import in.project.redditclone.exception.SpringRedditException;
import in.project.redditclone.model.User;
import in.project.redditclone.model.VerificationToken;
import in.project.redditclone.repository.UserRepository;
import in.project.redditclone.repository.VerificationTokenRepository;
import in.project.redditclone.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lenovo1
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setUserName(registerRequest.getUserName());
		user.setEnabled(false);
		user.setCreateDate(Instant.now());

		userRepository.save(user);

		String token = generateVerificationToken(user);

		String mailBody = "Thanks for signing up to reddit account. Please click on below link to activate account"
				+ "http:localhost:8080/api/auth/accountVerification/" + token;
		String mailSubject = "Please activate your account";

		mailService.sendMail(new NotificationEmail(mailSubject, user.getEmail(), mailBody));

	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		verificationRepository.save(verificationToken);
		return token;

	}

	/**
	 * 
	 * @param token
	 * 
	 * @author lenovo1 This method will verify token and if token is valid then set
	 *         user enable status to true.
	 */
	public void verifyToken(String token) {

		Optional<VerificationToken> verificationToken = verificationRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
		fetchUserAndEnable(verificationToken.get());

	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String userName = verificationToken.getUser().getUserName();
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new SpringRedditException("No valid user found with user name=" + userName));
		user.setEnabled(true);
		userRepository.save(user);

	}

	public AuthenticationResponse login(LoginRequest loginRequest)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);

		String token = jwtProvider.generateToken(authenticate);

//		return new AuthenticationResponse(token, loginRequest.getUserName());
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.userName(loginRequest.getUserName())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
				.build();
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User principle = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUserName(principle.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User is not present with userName " + principle.getUsername()));
	}

	public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUserName());
		return AuthenticationResponse.builder().authenticationToken(token)
				.refreshToken(refreshTokenRequest.getRefreshToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.userName(refreshTokenRequest.getUserName()).build();
	}

}
