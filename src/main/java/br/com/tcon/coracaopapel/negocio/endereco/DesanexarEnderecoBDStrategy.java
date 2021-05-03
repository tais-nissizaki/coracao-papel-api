package br.com.tcon.coracaopapel.negocio.endereco;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarEnderecoBDStrategy implements IStrategy {
	
	private EntityManager entityManager;
	
	public DesanexarEnderecoBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Endereco endereco = (Endereco) entidade;
		if(endereco != null) {
			if(endereco.getCliente() != null && endereco.getCliente().getId() != null) {
				entityManager.detach(endereco.getCliente());
				endereco.getCliente().setCarrinho(null);
				endereco.getCliente().setCartoes(null);
				endereco.getCliente().setCuponsCliente(null);
				endereco.getCliente().setDocumentos(null);
				endereco.getCliente().setEnderecos(null);
				endereco.getCliente().setGenero(null);
				endereco.getCliente().setTelefones(null);
				endereco.getCliente().setTipoCliente(null);
				endereco.getCliente().setUsuario(null);
				endereco.getCidade().getEstado().getPais().setEstados(null);
			}
			entityManager.detach(entidade);
		}
		return null;
	}

}
