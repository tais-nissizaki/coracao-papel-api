package br.com.tcon.coracaopapel;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT nome_usuario as username, senha as password, true FROM usuario where nome_usuario = ? ")
			.authoritiesByUsernameQuery(""
					+ "select u.nome_usuario, p.nome from usuario u "
					+ "join usuario_permissao up on u.id = up.id_usuario "
					+ "join permissao p on up.id_permissao = p.id where u.nome_usuario = ? ");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(
						"/estados/**",
						"/cidades/**",
						"/clientes/**",
						"/tipos-documento",
						"/tipos-endereco",
						"/tipos-cartao",
						"/tipos-cliente",
						"/bandeiras-cartao",
						"/generos",
						"/tipos-telefone",
						"/tipos-residencia",
						"/tipos-logradouro",
						"/status-pedido",
						"/login",
						"/login/**",
						"/carrinho/**",
						"/enderecos/**",
						"/cartoes/**",
						"/compras/**",
						"/cupons/**",
						"/produtos/**",
						"/paises/**",
						"/dashboard/**",
						"/carrinhos/produto*",
						"/grupos-precificacao/**",
						"*").permitAll()
				.anyRequest().authenticated()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().csrf().disable()
			.httpBasic();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return Encriptador.passwordEncoder;
	}
	
}
