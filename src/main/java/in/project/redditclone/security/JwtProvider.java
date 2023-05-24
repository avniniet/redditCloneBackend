/**
 * 
 */
package in.project.redditclone.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import in.project.redditclone.exception.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author lenovo1
 *
 */

@Service
public class JwtProvider {

	private KeyStore keyStore;
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceInputStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceInputStream, "secret".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occurred while loading key");
		}
	}

	public String generateToken(Authentication authentication)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		/*
		 * keyStore = KeyStore.getInstance("JKS"); InputStream resourceInputStream =
		 * getClass().getResourceAsStream("/springblog.jks");
		 * keyStore.load(resourceInputStream,"secret".toCharArray());
		 */
		User principle = (User) authentication.getPrincipal();
		return Jwts.builder().setSubject(principle.getUsername()).signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis))).compact();
	}
	
	public String generateTokenWithUserName(String userName)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		/*
		 * keyStore = KeyStore.getInstance("JKS"); InputStream resourceInputStream =
		 * getClass().getResourceAsStream("/springblog.jks");
		 * keyStore.load(resourceInputStream,"secret".toCharArray());
		 */
		return Jwts.builder().setSubject(userName).signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis))).compact();
	}


	private PrivateKey getPrivateKey() {
		try {
			PrivateKey privateKey = (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
			return privateKey;
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new SpringRedditException("Exception occurred while retrieving public key from key store");
		}
	}

	public boolean validateToken(String jwt) {
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;

	}

	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		} catch (KeyStoreException e) {
			throw new SpringRedditException("Exception occurred during retriving public key from key store");
		}

	}

	public String getUserNameFromJwt(String jwtToken) {
		Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwtToken).getBody();

		return claims.getSubject();

	}
	
	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}

}
