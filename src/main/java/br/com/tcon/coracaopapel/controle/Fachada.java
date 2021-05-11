package br.com.tcon.coracaopapel.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import br.com.tcon.coracaopapel.dao.BandeiraCartaoDAO;
import br.com.tcon.coracaopapel.dao.CarrinhoDAO;
import br.com.tcon.coracaopapel.dao.CartaoDAO;
import br.com.tcon.coracaopapel.dao.CidadeDAO;
import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.dao.CompraDAO;
import br.com.tcon.coracaopapel.dao.CupomClienteDAO;
import br.com.tcon.coracaopapel.dao.CupomDAO;
import br.com.tcon.coracaopapel.dao.EnderecoDAO;
import br.com.tcon.coracaopapel.dao.EstadoDAO;
import br.com.tcon.coracaopapel.dao.FornecedorDAO;
import br.com.tcon.coracaopapel.dao.GeneroDAO;
import br.com.tcon.coracaopapel.dao.GrupoPrecificacaoDAO;
import br.com.tcon.coracaopapel.dao.IDAO;
import br.com.tcon.coracaopapel.dao.PaisDAO;
import br.com.tcon.coracaopapel.dao.PedidoDAO;
import br.com.tcon.coracaopapel.dao.ProdutoDAO;
import br.com.tcon.coracaopapel.dao.StatusPedidoDAO;
import br.com.tcon.coracaopapel.dao.TipoCartaoDAO;
import br.com.tcon.coracaopapel.dao.TipoClienteDAO;
import br.com.tcon.coracaopapel.dao.TipoDocumentoDAO;
import br.com.tcon.coracaopapel.dao.TipoEnderecoDAO;
import br.com.tcon.coracaopapel.dao.TipoLogradouroDAO;
import br.com.tcon.coracaopapel.dao.TipoResidenciaDAO;
import br.com.tcon.coracaopapel.dao.TipoTelefoneDAO;
import br.com.tcon.coracaopapel.modelo.dominio.BandeiraCartao;
import br.com.tcon.coracaopapel.modelo.dominio.Carrinho;
import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Cidade;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Compra;
import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Estado;
import br.com.tcon.coracaopapel.modelo.dominio.Fornecedor;
import br.com.tcon.coracaopapel.modelo.dominio.Genero;
import br.com.tcon.coracaopapel.modelo.dominio.GrupoPrecificacao;
import br.com.tcon.coracaopapel.modelo.dominio.Pais;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;
import br.com.tcon.coracaopapel.modelo.dominio.StatusPedido;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCartao;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCliente;
import br.com.tcon.coracaopapel.modelo.dominio.TipoDocumento;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;
import br.com.tcon.coracaopapel.modelo.dominio.TipoLogradouro;
import br.com.tcon.coracaopapel.modelo.dominio.TipoResidencia;
import br.com.tcon.coracaopapel.modelo.dominio.TipoTelefone;
import br.com.tcon.coracaopapel.negocio.IStrategy;
import br.com.tcon.coracaopapel.negocio.carrinho.DesanexarCarrinhoBDStrategy;
import br.com.tcon.coracaopapel.negocio.carrinho.PreencherClienteCarrinhoStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.CriptografarSenhaStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.DefinirDataDeCadastro;
import br.com.tcon.coracaopapel.negocio.cliente.DefinirTipoClienteNovoStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.DefinirTipoUsuarioCliente;
import br.com.tcon.coracaopapel.negocio.cliente.DesanexarClienteBDStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.ValidarCPFJaCadastradoStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.ValidarCPFStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.ValidarDadosObrigatoriosStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.ValidarIdentificadorClientePrenchidoStrategy;
import br.com.tcon.coracaopapel.negocio.compra.AtribuirDataCadastroCompraStrategy;
import br.com.tcon.coracaopapel.negocio.compra.CarregarFornecedorCompraStrategy;
import br.com.tcon.coracaopapel.negocio.compra.CarregarProdutoItemCompraStrategy;
import br.com.tcon.coracaopapel.negocio.cupom.DefinirValidadeCupomStrategy;
import br.com.tcon.coracaopapel.negocio.cupom.DesanexarCupomBDStrategy;
import br.com.tcon.coracaopapel.negocio.cupom.GerarCodigoCupomStrategy;
import br.com.tcon.coracaopapel.negocio.cupom_cliente.AtribuirClienteCupomClienteStrategy;
import br.com.tcon.coracaopapel.negocio.cupom_cliente.DefinirDataDeCadastroCupomCliente;
import br.com.tcon.coracaopapel.negocio.cupom_cliente.DesanexarCupomClienteBDStrategy;
import br.com.tcon.coracaopapel.negocio.endereco.DesanexarEnderecoBDStrategy;
import br.com.tcon.coracaopapel.negocio.estado.DesanexarEstadoBdStrategy;
import br.com.tcon.coracaopapel.negocio.fornecedor.DesanexarFornecedorBDStrategy;
import br.com.tcon.coracaopapel.negocio.pais.DesanexarPaisDBDStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.CalcularPrazoEntregaStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.CarregaEnderecoDBStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.CarregarCartaoDBStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.CarregarCupomDBStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.DefinirStatusInicialPedidoStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.DesanexarPedidoDBStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.PreencherTransacaoPedidoStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.PrencherDataCadastroStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.ValidarDadosPagamentoStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.ValidarEnderecoEntregaPedidoStrategy;
import br.com.tcon.coracaopapel.negocio.pedido.ValidarProdutosCarrinhoStrategy;

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
			EntityManager entityManager,
			EstadoDAO estadoDAO,
			CidadeDAO cidadeDAO,
			TipoDocumentoDAO tipoDocumentoDAO,
			TipoEnderecoDAO tipoEnderecoDAO,
			ClienteDAO clienteDAO,
			TipoClienteDAO tipoClienteDAO,
			GeneroDAO generoDAO,
			TipoTelefoneDAO tipoTelefoneDAO,
			TipoResidenciaDAO tipoResidenciaDAO,
			TipoLogradouroDAO tipoLogradouroDAO,
			CarrinhoDAO carrinhoDAO,
			EnderecoDAO enderecoDAO,
			CartaoDAO cartaoDAO,
			TipoCartaoDAO tipoCartaoDAO,
			BandeiraCartaoDAO bandeiraCartaoDAO,
			CupomDAO cupomDAO,
			PedidoDAO pedidoDAO,
			StatusPedidoDAO statusPedidoDAO,
			ProdutoDAO produtoDAO,
			CupomClienteDAO cupomClienteDAO,
			FornecedorDAO fornecedorDAO,
			PaisDAO paisDAO,
			CompraDAO compraDAO,
			GrupoPrecificacaoDAO grupoPrecificacaoDAO
			) {
		mapaDaos.put(Estado.class.getName(), estadoDAO);
		mapaDaos.put(Cidade.class.getName(), cidadeDAO);
		mapaDaos.put(TipoDocumento.class.getName(), tipoDocumentoDAO);
		mapaDaos.put(TipoEndereco.class.getName(), tipoEnderecoDAO);
		mapaDaos.put(Cliente.class.getName(), clienteDAO);
		mapaDaos.put(TipoCliente.class.getName(), tipoClienteDAO);
		mapaDaos.put(Genero.class.getName(), generoDAO);
		mapaDaos.put(TipoTelefone.class.getName(), tipoTelefoneDAO);
		mapaDaos.put(TipoResidencia.class.getName(), tipoResidenciaDAO);
		mapaDaos.put(TipoLogradouro.class.getName(), tipoLogradouroDAO);
		mapaDaos.put(TipoCartao.class.getName(), tipoCartaoDAO);
		mapaDaos.put(BandeiraCartao.class.getName(), bandeiraCartaoDAO);
		mapaDaos.put(Cupom.class.getName(), cupomDAO);

		mapaDaos.put(Carrinho.class.getName(), carrinhoDAO);
		mapaDaos.put(Endereco.class.getName(), enderecoDAO);
		mapaDaos.put(Cartao.class.getName(), cartaoDAO);
		mapaDaos.put(Pedido.class.getName(), pedidoDAO);
		mapaDaos.put(StatusPedido.class.getName(), statusPedidoDAO);
		mapaDaos.put(Produto.class.getName(), produtoDAO);
		mapaDaos.put(CupomCliente.class.getName(), cupomClienteDAO);
		mapaDaos.put(Fornecedor.class.getName(), fornecedorDAO);
		mapaDaos.put(Pais.class.getName(), paisDAO);
		mapaDaos.put(Compra.class.getName(), compraDAO);
		mapaDaos.put(GrupoPrecificacao.class.getName(), grupoPrecificacaoDAO);
			
		List<IStrategy> listaAntesPersistenciaCliente = new ArrayList<>();
		listaAntesPersistenciaCliente.add(new DefinirTipoClienteNovoStrategy());
		listaAntesPersistenciaCliente.add(new ValidarDadosObrigatoriosStrategy());
		listaAntesPersistenciaCliente.add(new ValidarCPFStrategy());
		listaAntesPersistenciaCliente.add(new ValidarCPFJaCadastradoStrategy(clienteDAO));
		listaAntesPersistenciaCliente.add(new DefinirDataDeCadastro());
		listaAntesPersistenciaCliente.add(new CriptografarSenhaStrategy());
		listaAntesPersistenciaCliente.add(new DefinirTipoUsuarioCliente());
		
		mapaAntesPersistencia.put(Cliente.class.getName(), listaAntesPersistenciaCliente);
		
		
		List<IStrategy> listaDepoisPersistenciaCliente = new ArrayList<>();
		mapaDepoisPesistencia.put(Cliente.class.getName(), listaDepoisPersistenciaCliente);
		

		List<IStrategy> listaAntesConsultarCliente = new ArrayList<>();
		mapaAntesConsultar.put(Cliente.class.getName(), listaAntesConsultarCliente);
		

		List<IStrategy> listaDepoisConsultarCliente = new ArrayList<>();
		listaDepoisConsultarCliente.add(new DesanexarClienteBDStrategy(entityManager));
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
		
		///// CARRINHO
		List<IStrategy> listaAntesPersistenciaCarrinho = new ArrayList<>();
		listaAntesPersistenciaCarrinho.add(new PreencherClienteCarrinhoStrategy(clienteDAO));
		mapaAntesPersistencia.put(Carrinho.class.getName(), listaAntesPersistenciaCarrinho);

		List<IStrategy> listaDepoisPersistenciaCarrinho = new ArrayList<>();
		listaDepoisPersistenciaCarrinho.add(new DesanexarCarrinhoBDStrategy(entityManager));
		mapaDepoisPesistencia.put(Carrinho.class.getName(), listaDepoisPersistenciaCarrinho);
		
		List<IStrategy> listaAntesAlteracaoCarrinho = new ArrayList<>();
		listaAntesAlteracaoCarrinho.add(new PreencherClienteCarrinhoStrategy(clienteDAO));
		mapaAntesAlteracao.put(Carrinho.class.getName(), listaAntesAlteracaoCarrinho);

		List<IStrategy> listaDepoisAlteracaoCarrinho = new ArrayList<>();
		listaDepoisAlteracaoCarrinho.add(new DesanexarCarrinhoBDStrategy(entityManager));
		mapaDepoisAlteracao.put(Carrinho.class.getName(), listaDepoisAlteracaoCarrinho);
		

		List<IStrategy> listaDepoisConsultarCarrinho = new ArrayList<>();
		listaDepoisConsultarCarrinho.add(new DesanexarCarrinhoBDStrategy(entityManager));
		mapaDepoisConsultar.put(Carrinho.class.getName(), listaDepoisConsultarCarrinho);

		///// ENDERECO
		List<IStrategy> listaDepoisConsultarEndereco = new ArrayList<>();
		listaDepoisConsultarEndereco.add(new DesanexarEnderecoBDStrategy(entityManager));
		mapaDepoisConsultar.put(Endereco.class.getName(), listaDepoisConsultarEndereco);

		///// CUPOM
		List<IStrategy> listaDepoisConsultarCupom = new ArrayList<>();
		listaDepoisConsultarCupom.add(new DesanexarCupomBDStrategy(entityManager));
		mapaDepoisConsultar.put(Cupom.class.getName(), listaDepoisConsultarCupom);
		
		///// PEDIDO
		List<IStrategy> listaAntesPersistenciaPedido = new ArrayList<>();
		listaAntesPersistenciaPedido.add(new ValidarDadosPagamentoStrategy());
		listaAntesPersistenciaPedido.add(new ValidarEnderecoEntregaPedidoStrategy());
		listaAntesPersistenciaPedido.add(new ValidarProdutosCarrinhoStrategy());
		listaAntesPersistenciaPedido.add(new PrencherDataCadastroStrategy());
		listaAntesPersistenciaPedido.add(new CalcularPrazoEntregaStrategy());
		listaAntesPersistenciaPedido.add(new CarregarCartaoDBStrategy(cartaoDAO));
		listaAntesPersistenciaPedido.add(new CarregaEnderecoDBStrategy(enderecoDAO));
		listaAntesPersistenciaPedido.add(new CarregarCupomDBStrategy(cupomDAO));
		listaAntesPersistenciaPedido.add(new DefinirStatusInicialPedidoStrategy());
		listaAntesPersistenciaPedido.add(new PreencherTransacaoPedidoStrategy());
		mapaAntesPersistencia.put(Pedido.class.getName(), listaAntesPersistenciaPedido);

		List<IStrategy> listaDepoisConsultarPedido = new ArrayList<>();
		listaDepoisConsultarPedido.add(new DesanexarPedidoDBStrategy(entityManager));
		mapaDepoisConsultar.put(Pedido.class.getName(), listaDepoisConsultarPedido);
		
		///// CUPOM DE TROCA
		List<IStrategy> listaAntesPersistenciaCupomCliente = new ArrayList<>();
		listaAntesPersistenciaCupomCliente.add(new GerarCodigoCupomStrategy());
		listaAntesPersistenciaCupomCliente.add(new DefinirValidadeCupomStrategy());
		listaAntesPersistenciaCupomCliente.add(new DefinirDataDeCadastroCupomCliente());
		listaAntesPersistenciaCupomCliente.add(new AtribuirClienteCupomClienteStrategy(entityManager));
		mapaAntesPersistencia.put(CupomCliente.class.getName(), listaAntesPersistenciaCupomCliente);
		
		List<IStrategy> listaDepoisConsultaCupomCliente = new ArrayList<>();
		listaDepoisConsultaCupomCliente.add(new DesanexarCupomClienteBDStrategy(entityManager));
		mapaDepoisConsultar.put(CupomCliente.class.getName(), listaDepoisConsultaCupomCliente);
		
		///// CUPOM DE TROCA
		List<IStrategy> listaDepoisConsultaFornecedorCliente = new ArrayList<>();
		listaDepoisConsultaFornecedorCliente.add(new DesanexarFornecedorBDStrategy(entityManager));
		mapaDepoisConsultar.put(Fornecedor.class.getName(), listaDepoisConsultaFornecedorCliente);
		
		///// PAIS
		List<IStrategy> listaDepoisConsultaPaisCliente = new ArrayList<>();
		listaDepoisConsultaPaisCliente.add(new DesanexarPaisDBDStrategy(entityManager));
		mapaDepoisConsultar.put(Pais.class.getName(), listaDepoisConsultaPaisCliente);
		
		///// ESTADO
		List<IStrategy> listaDepoisConsultaEstadoCliente = new ArrayList<>();
		listaDepoisConsultaEstadoCliente.add(new DesanexarEstadoBdStrategy(entityManager));
		mapaDepoisConsultar.put(Estado.class.getName(), listaDepoisConsultaEstadoCliente);
		
		//// COMPRA
		List<IStrategy> listaAntesPersistenciaCompra = new ArrayList<>();
		listaAntesPersistenciaCompra.add(new AtribuirDataCadastroCompraStrategy());
		listaAntesPersistenciaCompra.add(new CarregarFornecedorCompraStrategy(entityManager));
		listaAntesPersistenciaCompra.add(new CarregarProdutoItemCompraStrategy(entityManager));
		mapaAntesPersistencia.put(Compra.class.getName(), listaAntesPersistenciaCompra);
		
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
		StringBuilder retornoRegras = executarStrategies(mapaAntesAlteracao.get(nomeEntidadeDominio), entidade);
		if(retornoRegras.length() == 0) {
			try {
				boolean alterado = mapaDaos.get(nomeEntidadeDominio).alterar(entidade);
				if(!alterado) {
					return "Falha ao efetuar alteração no banco de dados.";
				}
				retornoRegras = executarStrategies(mapaDepoisAlteracao.get(nomeEntidadeDominio), entidade);
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
				boolean inativar = mapaDaos.get(nomeEntidadeDominio).inativar(entidade);
				if(inativar) {
					regrasAntesPersistencia = executarStrategies(mapaAntesInativar.get(nomeEntidadeDominio), entidade);
				} else {
					regrasAntesPersistencia.append("Falha ao inativar na base de dados");					
				}
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
			for(EntidadeDominio entidadeDominio: retornoMetodo) {
				StringBuilder retornoStrategiesDepoisConsulta = executarStrategies(regrasDepoisConsulta, entidadeDominio);
				retornoStrategies.append(retornoStrategiesDepoisConsulta);
			}
		}

		if(retornoStrategies.length() != 0) { 
			retornoMetodo = null;
		} else {
			List<EntidadeDominio> retornoFinal = new ArrayList<>(); 
			for(EntidadeDominio entidadeDominio: retornoMetodo) {
				if(entidadeDominio != null) {
					retornoFinal.add(entidadeDominio);
				}
			}
			retornoMetodo = retornoFinal;
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
