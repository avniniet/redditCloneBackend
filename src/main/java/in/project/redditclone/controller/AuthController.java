/**
 * 
 */
package in.project.redditclone.controller;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.project.redditclone.dto.AuthenticationResponse;
import in.project.redditclone.dto.LoginRequest;
import in.project.redditclone.dto.RefreshTokenRequest;
import in.project.redditclone.dto.RegisterRequest;
import in.project.redditclone.service.AuthService;
import in.project.redditclone.service.RefreshTokenService;
import lombok.AllArgsConstructor;
/**
 * @author lenovo1
 *
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity<>("User registration successfull", HttpStatus.OK);
	}
	
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyToken(@PathVariable String token){
		authService.verifyToken(token);
		return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		return authService.login(loginRequest);
	}
	
	@PostMapping("refresh/token")
	public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		return authService.refreshToken(refreshTokenRequest);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted successfully!!");
	}
}
