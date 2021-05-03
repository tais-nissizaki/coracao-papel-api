package br.com.tcon.coracaopapel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

//@Configuration
//@EnableAuthorizationServer
public class OAuth2AuthorizationServer { //extends AuthorizationServerConfigurerAdapter {
	
//	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.allowFormAuthenticationForClients();
	}

//	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
			.withClient("coracao-papel-front")
			.secret(passwordEncoder.encode("123456"))
			.authorizedGrantTypes("password", "authorization_code", "refresh_token")
//			.authorities("READ_ONLY_CLIENT")
//			.scopes("read_profile_info")
			.resourceIds("oauth2-resource")
			.redirectUris("http://localhost:8083/login")
			.accessTokenValiditySeconds(5000)
			.refreshTokenValiditySeconds(50000);
	}
}
