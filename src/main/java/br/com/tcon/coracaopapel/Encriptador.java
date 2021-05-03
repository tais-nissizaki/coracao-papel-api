package br.com.tcon.coracaopapel;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class Encriptador {
	
	public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

}
