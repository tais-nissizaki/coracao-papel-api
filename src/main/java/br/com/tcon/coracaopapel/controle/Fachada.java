package br.com.tcon.coracaopapel.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.tcon.coracaopapel.dao.CidadeDAO;
import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.dao.EstadoDAO;
import br.com.tcon.coracaopapel.dao.IDAO;
import br.com.tcon.coracaopapel.dao.TipoClienteDAO;
import br.com.tcon.coracaopapel.dao.TipoDocumentoDAO;
import br.com.tcon.coracaopapel.dao.TipoEnderecoDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Cidade;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Estado;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCliente;
import br.com.tcon.coracaopapel.modelo.dominio.TipoDocumento;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;
import br.com.tcon.coracaopapel.negocio.IStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.DefinirDataDeCadastro;
import br.com.tcon.coracaopapel.negocio.cliente.DefinirTipoClienteNovoStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.ValidarCPFJaCadastradoStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.ValidarCPFStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.ValidarDadosObrigatoriosStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.ValidarIdentificadorClientePrenchidoStrategy;

@Component
public class Fachada implements IFachada {
	private Map<String, List<IStrategy>> mapaAntesPersistencia = new HashMap<>();
	private Map<String, List<IStrategy>> mapaDepoisPesistencia = new HashMap<>();
	
	private Map<String, List<IStrategy>> mapaAntesInativar = new HashMap<>();
	private Map<String, List<IStrategy>> mapaDepoisInativar = new HashMap<>();
	
	private Map<String, List<IStrategy>> mapaAntesAtivar = new HashMap<>();
	private Map<String, List<IStrategy>> mapaDepoisAtivar = new HashMap<>();
	
	private Map<String, List<IStrategy>> mapaAntesConsultar = new HashMap<>();
	private Map<String, List<IStrategy>> mapaDepoisConsultar = new HashMap<>();
	
	private Map<String, List<IStrategy>> mapaAntesAlteracao = new HashMap<>();
	private Map<String, List<IStrategy>> mapaDepoisAlteracao = new HashMap<>();
	
	private Map<String, IDAO> mapaDaos = new HashMap<>();
	
	public Fachada(
			EstadoDAO estadoDAO,
			CidadeDAO cidadeDAO,
			TipoDocumentoDAO tipoDocumentoDAO,
			TipoEnderecoDAO tipoEnderecoDAO,
			ClienteDAO clienteDAO,
			TipoClienteDAO tipoClienteDAO
			) {
		mapaDaos.put(Estado.class.getName(), estadoDAO);
		mapaDaos.put(Cidade.class.getName(), cidadeDAO);
		mapaDaos.put(TipoDocumento.class.getName(), tipoDocumentoDAO);
		mapaDaos.put(TipoEndereco.class.getName(), tipoEnderecoDAO);
		mapaDaos.put(Cliente.class.getName(), clienteDAO);
		mapaDaos.put(TipoCliente.class.getName(), tipoClienteDAO);
		
		
		List<IStrategy> listaAntesPersistenciaCliente = new ArrayList<>();
		listaAntesPersistenciaCliente.add(new DefinirTipoClienteNovoStrategy());
		listaAntesPersistenciaCliente.add(new ValidarDadosObrigatoriosStrategy());
		listaAntesPersistenciaCliente.add(new ValidarCPFStrategy());
		listaAntesPersistenciaCliente.add(new ValidarCPFJaCadastradoStrategy(clienteDAO));
		listaAntesPersistenciaCliente.add(new DefinirDataDeCadastro());
		mapaAntesPersistencia.put(Cliente.class.getName(), listaAntesPersistenciaCliente);
		
		
		List<IStrategy> listaDepoisPersistenciaCliente = new ArrayList<>();
		mapaDepoisPesistencia.put(Cliente.class.getName(), listaDepoisPersistenciaCliente);
		

		List<IStrategy> listaAntesConsultarCliente = new ArrayList<>();
		mapaAntesConsultar.put(Cliente.class.getName(), listaAntesConsultarCliente);
		

		List<IStrategy> listaDepoisConsultarCliente = new ArrayList<>();
		mapaDepoisConsultar.put(Cliente.class.getName(), listaDepoisConsultarCliente);
		
		List<IStrategy> listaAntesInativarCliente = new ArrayList<>();
		mapaAntesInativar.put(Cliente.class.getName(), listaAntesInativarCliente);
		

		List<IStrategy> listaDepoisInativarCliente = new ArrayList<>();
		mapaDepoisInativar.put(Cliente.class.getName(), listaDepoisInativarCliente);
		
		List<IStrategy> listaAntesAtivarCliente = new ArrayList<>();
		mapaAntesAtivar.put(Cliente.class.getName(), listaAntesAtivarCliente);
		

		List<IStrategy> listaDepoisAtivarCliente = new ArrayList<>();
		mapaDepoisAtivar.put(Cliente.class.getName(), listaDepoisAtivarCliente);

		List<IStrategy> listaAntesAlteracaoCliente = new ArrayList<>();
		listaAntesAlteracaoCliente.add(new ValidarIdentificadorClientePrenchidoStrategy());
		listaAntesAlteracaoCliente.add(new ValidarDadosObrigatoriosStrategy());
		listaAntesAlteracaoCliente.add(new ValidarCPFStrategy());
		listaAntesAlteracaoCliente.add(new ValidarCPFJaCadastradoStrategy(clienteDAO));
		mapaAntesAlteracao.put(Cliente.class.getName(), listaAntesAlteracaoCliente);
	}

	@Override
	public String salvar(EntidadeDominio entidade) {
		String nomeEntidadeDominio = entidade.getClass().getName();
		StringBuilder regrasAntesPersistencia = executarStrategies(mapaAntesPersistencia.get(nomeEntidadeDominio), entidade);
		if(regrasAntesPersistencia.length() == 0) {
			try {
				mapaDaos.get(nomeEntidadeDominio).salvar(entidade);
				regrasAntesPersistencia = executarStrategies(mapaDepoisPesistencia.get(nomeEntidadeDominio), entidade);
			} catch (Exception e ) {
				e.printStackTrace();
				regrasAntesPersistencia.append("Falha ao gravar na base de dados");
			}
		}
		return regrasAntesPersistencia.toString();
	}

	@Override
	public String alterar(EntidadeDominio entidade) {
		String nomeEntidadeDominio = entidade.getClass().getName();
		StringBuilder retornoRegras = executarStrategies(mapaAntesPersistencia.get(nomeEntidadeDominio), entidade);
		if(retornoRegras.length() == 0) {
			try {
				mapaDaos.get(nomeEntidadeDominio).alterar(entidade);
				retornoRegras = executarStrategies(mapaDepoisPesistencia.get(nomeEntidadeDominio), entidade);
			} catch (Exception e ) {
				e.printStackTrace();
				retornoRegras.append("Falha ao gravar na base de dados");
			}
		}
		return retornoRegras.toString();
	}

	@Override
	public String inativar(EntidadeDominio entidade) {
		String nomeEntidadeDominio = entidade.getClass().getName();
		StringBuilder regrasAntesPersistencia = executarStrategies(mapaAntesInativar.get(nomeEntidadeDominio), entidade);
		if(regrasAntesPersistencia.length() == 0) {
			try {
				mapaDaos.get(nomeEntidadeDominio).inativar(entidade);
				regrasAntesPersistencia = executarStrategies(mapaAntesInativar.get(nomeEntidadeDominio), entidade);
			} catch (Exception e ) {
				e.printStackTrace();
				regrasAntesPersistencia.append("Falha ao inativar na base de dados");
			}
		}
		return regrasAntesPersistencia.toString();
	}

	@Override
	public String ativar(EntidadeDominio entidade) {
		String nomeEntidadeDominio = entidade.getClass().getName();
		StringBuilder regrasAntesPersistencia = executarStrategies(mapaAntesAtivar.get(nomeEntidadeDominio), entidade);
		if(regrasAntesPersistencia.length() == 0) {
			try {
				mapaDaos.get(nomeEntidadeDominio).ativar(entidade);
				regrasAntesPersistencia = executarStrategies(mapaAntesAtivar.get(nomeEntidadeDominio), entidade);
			} catch (Exception e ) {
				e.printStackTrace();
				regrasAntesPersistencia.append("Falha ao inativar na base de dados");
			}
		}
		return regrasAntesPersistencia.toString();
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		List<IStrategy> regrasAntesConsulta = mapaAntesConsultar.get(entidade.getClass().getName());
		StringBuilder retornoStrategies = executarStrategies(regrasAntesConsulta, entidade);
		
		List<EntidadeDominio> retornoMetodo = null;
		if(retornoStrategies.length() == 0) {
			retornoMetodo = mapaDaos.get(entidade.getClass().getName()).consultar(entidade);		
			List<IStrategy> regrasDepoisConsulta = mapaDepoisConsultar.get(entidade.getClass().getName());
			StringBuilder retornoStrategiesDepoisConsulta = executarStrategies(regrasDepoisConsulta, entidade);
			retornoStrategies.append(retornoStrategiesDepoisConsulta);
		}

		if(retornoStrategies.length() != 0) { 
			retornoMetodo = null;
		}
		return retornoMetodo;
	}
	
	private StringBuilder executarStrategies(List<IStrategy> strategies, EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		if(strategies != null) {
			for(IStrategy rn:strategies) {
				String msg = rn.processar(entidade);
				if(msg!= null) {
					sb.append(msg);
				}
			}
		}
		return sb;
	}
	

}
