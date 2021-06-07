package br.com.tcon.coracaopapel;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class Encriptador {
	
	public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	
	public static void main(String[] args) {
		System.out.println(passwordEncoder.encode("brunorabello"));
		System.out.println(passwordEncoder.encode("carolinaribeiro"));
		System.out.println(passwordEncoder.encode("eduardomarins"));
		System.out.println(passwordEncoder.encode("daianemartins"));
	}

}
