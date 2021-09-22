package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Usuario;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarSenhaStrategy implements IStrategy {
	private final String caraceresEspeciais = "?!@#$%*";
	private final String letrasMinusculas = "abcdefghijklmnopqrstuvwxyz";
	private final String letrasMaiusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String numeros = "0123456789";
	private final Integer tamanmhoMinimo = 8;

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder retorno = new StringBuilder();
		Usuario usuario = null;
		if (entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			usuario = cliente.getUsuario();
		} else if (entidade instanceof Usuario) {
			usuario = (Usuario) entidade;
		}
		
		if(usuario != null && usuario.getSenha() != null) {
			if(usuario.getSenha().length() < tamanmhoMinimo) {
				retorno.append("A senha deve ter no mínimo 8 caracteres. ");
			}
			
			if(!contem(usuario.getSenha(), letrasMinusculas)) {
				retorno.append("A senha deve ter no pelo menos 1 letra minúscula. ");
			}
			
			if(!contem(usuario.getSenha(), letrasMaiusculas)) {
				retorno.append("A senha deve ter no pelo menos 1 letra maiúscula. ");
			}
			
			if(!contem(usuario.getSenha(), numeros)) {
				retorno.append("A senha deve ter no pelo menos 1 número. ");
			}
			
			if(!contem(usuario.getSenha(), caraceresEspeciais)) {
				retorno.append("A senha deve ter no pelo menos 1 caractere especial (? ! @ # $ % *). ");
			}
		}
		return retorno.toString();
	}
	
	private boolean contem(String senha, String caracteres) {
		for(int i=0; i< senha.length(); i++) {
			for(int j=0; j< caracteres.length(); j++) {
				if(senha.charAt(i) == caracteres.charAt(j)) {
					return true;
				}
			}
		}
		return false;
	}

}
