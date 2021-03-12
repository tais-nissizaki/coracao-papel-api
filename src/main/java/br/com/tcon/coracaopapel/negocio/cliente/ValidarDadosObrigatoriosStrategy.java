package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Documento;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarDadosObrigatoriosStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder retorno = new StringBuilder();
		if(entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			
			validarNome(retorno, cliente);
			
			validarCPF(retorno, cliente);
			
			retorno.append(new ValidarEnderecoEntregaStrategy().processar(entidade));
			
			retorno.append(new ValidarEnderecoCobrancaStrategy().processar(entidade));
		} else {
			retorno.append("Dados de ciente inválidos");
		}
		return retorno.toString();
	}

	private void validarCPF(StringBuilder retorno, Cliente cliente) {
		if (cliente.getDocumentos() != null && !cliente.getDocumentos().isEmpty()) {
			boolean encontrouCPF = false;
			for (int i=0; i< cliente.getDocumentos().size(); i++) {
				Documento documento = cliente.getDocumentos().get(i);
				if (documento.getTipoDocumento().getNome().equalsIgnoreCase("CPF")) {
					encontrouCPF = true;
					break;
				}
			}
			if(!encontrouCPF) {
				retorno.append("É necessário informar o CPF.");
			}
		} else {
			retorno.append("É necessário informar o CPF.");
		}
	}

	private void validarNome(StringBuilder retorno, Cliente cliente) {
		if (cliente.getNome() == null || cliente.getNome().isBlank()) {
			retorno.append("O campo 'nome' é obrigatório.");
		}
	}

}
