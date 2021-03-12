package br.com.tcon.coracaopapel.negocio.cliente;

import java.util.ArrayList;
import java.util.List;

import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Documento;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarCPFJaCadastradoStrategy implements IStrategy {
	
	private ClienteDAO clienteDAO;

	public ValidarCPFJaCadastradoStrategy(ClienteDAO clienteDAO) {
		super();
		this.clienteDAO = clienteDAO;
	}


	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder retorno = new StringBuilder();
		Cliente cliente = (Cliente) entidade;
		Cliente clienteComCPF = new Cliente();
		ArrayList<Documento> documentos = new ArrayList<Documento>();
		for(int i=0; i<cliente.getDocumentos().size(); i++) {
			if(cliente.getDocumentos().get(i).getTipoDocumento().getNome().equalsIgnoreCase("CPF")) {				
				documentos.add(cliente.getDocumentos().get(i));
			}
		}
		if(!documentos.isEmpty()) {
			clienteComCPF.setDocumentos(documentos);
			List<EntidadeDominio> consultar = clienteDAO.consultar(clienteComCPF);
			if(consultar != null && !consultar.isEmpty()) {
				if(consultar.size() != 1 || entidade.getId() == null ||consultar.get(0).getId().intValue() != entidade.getId().intValue()) {
					retorno.append("Já existe cliente cadastrado com este mesmo CPF");
				}
			}
		}
		return retorno.toString();
	}

}
