package br.com.tcon.coracaopapel.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcon.coracaopapel.controle.cliente.ConsultarClienteCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Usuario;
import br.com.tcon.coracaopapel.view.dto.DadosUsuario;

@RestController
public class CtrlLogin {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ConsultarClienteCommand consultarClienteCommand;

	@RequestMapping("/login")
	@ResponseBody
	public DadosUsuario login(@RequestBody Usuario user) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getNomeUsuario(), user.getSenha()));
		
		User userCredential = (User) authenticate.getPrincipal();
		
		List<String> permissoes = new ArrayList<>();
		boolean isCliente = false;
		Iterator<GrantedAuthority> iteratorAuthorities = userCredential.getAuthorities().iterator();
		while (iteratorAuthorities.hasNext()) {
			String permissao = iteratorAuthorities.next().getAuthority();
			if(permissao.equalsIgnoreCase("CLIENTE")) {
				isCliente = true;
			}
			permissoes.add(permissao);
		}
		
		DadosUsuario dadosUsuario = new DadosUsuario();
		if(isCliente) {
			Cliente cliente = new Cliente();
			cliente.setEmail(user.getNomeUsuario());
			List<Cliente> clientes = (List<Cliente>) consultarClienteCommand.executar(cliente);
			if(clientes != null && !clientes.isEmpty()) {				
				dadosUsuario.setIdCliente(clientes.get(0).getId());
				dadosUsuario.setAtivo(clientes.get(0).getAtivo());
			}
		}
		dadosUsuario.setPermissoes(permissoes);
		
		return dadosUsuario;
	}

}
