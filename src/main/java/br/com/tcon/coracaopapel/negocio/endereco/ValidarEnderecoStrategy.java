package br.com.tcon.coracaopapel.negocio.endereco;

import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarEnderecoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Endereco endereco = (Endereco) entidade;
		StringBuilder erros = new StringBuilder();
		if(endereco == null) {
			return "Endereço não informado";
		}
		if(endereco.getIdentificadorEndereco() == null || endereco.getIdentificadorEndereco().isBlank()) {
			erros.append("Identificação do endereço não informado.").append("\n");
		} else if(!endereco.getIdentificadorEndereco().trim().contains(" ")) {
			erros.append("Identificação do endereço invalido.").append("\n");
		}
		if(endereco.getTipoEndereco() == null || endereco.getTipoEndereco().getId() == null) {
			erros.append("Tipo de endereço não informado.").append("\n");
			
		}
		if(endereco.getTipoLogradouro() == null || endereco.getTipoLogradouro().getId() == null) {
			erros.append("Tipo de logradouro não informado.").append("\n");
			
		}
		if(endereco.getLogradouro() == null || endereco.getLogradouro().isBlank()) {
			erros.append("Logradouo não informado.").append("\n");
			
		}
		if(endereco.getNumero() == null || endereco.getNumero().isBlank()) {
			erros.append("Número do endereço não informado.").append("\n");
			
		}
		if(endereco.getCidade() == null || endereco.getCidade().getId() == null) {
			erros.append("Cidade não informado.").append("\n");
			
		}
		if(endereco.getCep() == null || endereco.getCep().isBlank()) {
			erros.append("CEP não informado.").append("\n");
			
		}
		if(endereco.getTipoResidencia() == null || endereco.getTipoResidencia().getId() == null) {
			erros.append("Tipo de residência não informado.").append("\n");
			
		}
		return erros.toString();
	}

}
