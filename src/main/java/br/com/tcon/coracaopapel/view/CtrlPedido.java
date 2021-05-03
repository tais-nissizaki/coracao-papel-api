package br.com.tcon.coracaopapel.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.cupom_cliente.SalvarCupomClienteCommand;
import br.com.tcon.coracaopapel.controle.pedido.AlterarPedidoCommand;
import br.com.tcon.coracaopapel.controle.pedido.ConsultarPedidoCommand;
import br.com.tcon.coracaopapel.controle.pedido.SalvarPedidoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemPedido;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.StatusPedido;
import br.com.tcon.coracaopapel.modelo.dominio.TransacaoPedido;

@Controller
@RequestMapping("pedidos")
public class CtrlPedido {
	
	@Autowired
	private SalvarPedidoCommand salvarPedidoCommand;
	
	@Autowired
	private ConsultarPedidoCommand consultarPedidoCommand;
	
	@Autowired
	private AlterarPedidoCommand alterarPedidoCommand;
	
	@Autowired
	private SalvarCupomClienteCommand salvarCupomCommand;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String efetuarPedido(@RequestBody Pedido pedido) {
		String mensagens = (String) salvarPedidoCommand.executar(pedido);
		if(mensagens != null && !mensagens.trim().isBlank()) {
			return "Erro: " + mensagens;
		}
		
		return pedido.getId().toString();
	}
	
	@GetMapping(path = "{idPedido}")
	@ResponseBody
	public EntidadeDominio obterPedido(@PathVariable("idPedido") Integer idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		List<Pedido> pedidos = (List<Pedido>) consultarPedidoCommand.executar(pedido);
		if(pedidos != null && !pedidos.isEmpty()) {
			return pedidos.get(0);
		}
		return null;
	}

	
	@GetMapping(path = {"", "cliente/{idCliente}"}) 
	@ResponseBody
	public List<Pedido> pesquisarPedidos(
			@PathVariable(name = "idCliente", required = false) Integer idCliente,
			@RequestParam(name = "dtInicioFiltro", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date dtInicioFiltro,
			@RequestParam(name = "dtFimFiltro", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd")  Date dtFimFiltro, 
			@RequestParam(name = "idStatusPedido", required = false) Integer idStatusPedido) {
		Pedido pedido = new Pedido();
		pedido.setInicioFiltro(dtInicioFiltro);
		pedido.setFimFiltro(dtFimFiltro);
		StatusPedido statusPedido = new StatusPedido();
		statusPedido.setId(idStatusPedido);
		pedido.setStatusPedido(statusPedido);
		if(idCliente != null && idCliente> 0) {
			Cliente cliente = new Cliente();
			cliente.setId(idCliente);
			pedido.setCliente(cliente);
		}
		List<Pedido> pedidos = (List<Pedido>) consultarPedidoCommand.executar(pedido);
		return pedidos;
	}
	
	@PutMapping(path = "aprovar-pagamento/{idPedido}")
	@ResponseBody
	public String aprovarPagamentoPedido(@PathVariable("idPedido") Integer idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		pedido.setTransacoesPedido(new ArrayList<>());
		TransacaoPedido transacaoPedido = new TransacaoPedido();
		transacaoPedido.setDescricao("Pagamento aprovado");
		Date dtCadastro = new Date();
		transacaoPedido.setDtCadastro(dtCadastro);
		transacaoPedido.setPedido(pedido);
		pedido.getTransacoesPedido().add(transacaoPedido);
		
		StatusPedido statusPedido = new StatusPedido();
		statusPedido.setId(2);
		pedido.setStatusPedido(statusPedido);
		alterarPedidoCommand.executar(pedido);
		return "";
	}
	
	@PutMapping(path = "enviar-pedido/{idPedido}")
	@ResponseBody
	public String enviarPedido(@PathVariable("idPedido") Integer idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		pedido.setTransacoesPedido(new ArrayList<>());
		TransacaoPedido transacaoPedido = new TransacaoPedido();
		transacaoPedido.setDescricao("Enviado à transportadora");
		Date dtCadastro = new Date();
		transacaoPedido.setDtCadastro(dtCadastro);
		transacaoPedido.setPedido(pedido);
		pedido.getTransacoesPedido().add(transacaoPedido);
		
		StatusPedido statusPedido = new StatusPedido();
		statusPedido.setId(3);
		pedido.setStatusPedido(statusPedido);
		alterarPedidoCommand.executar(pedido);
		return "";
	}
	
	@PutMapping(path = "pedido-entregue/{idPedido}")
	@ResponseBody
	public String entregarPedido(@PathVariable("idPedido") Integer idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		pedido.setTransacoesPedido(new ArrayList<>());
		TransacaoPedido transacaoPedido = new TransacaoPedido();
		transacaoPedido.setDescricao("Pedido entrege o cliente");
		Date dtCadastro = new Date();
		transacaoPedido.setDtCadastro(dtCadastro);
		transacaoPedido.setPedido(pedido);
		pedido.getTransacoesPedido().add(transacaoPedido);
		
		StatusPedido statusPedido = new StatusPedido();
		statusPedido.setId(4);
		pedido.setStatusPedido(statusPedido);
		alterarPedidoCommand.executar(pedido);
		return "";
	}
	
	@PutMapping(path = "aprovar-troca-pedido/{idPedido}")
	@ResponseBody
	public String aprovarTrocaDoPedido(@PathVariable("idPedido") Integer idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		pedido.setTransacoesPedido(new ArrayList<>());
		TransacaoPedido transacaoPedido = new TransacaoPedido();
		transacaoPedido.setDescricao("Pedido de troca aprovado pelo gestor");
		Date dtCadastro = new Date();
		transacaoPedido.setDtCadastro(dtCadastro);
		transacaoPedido.setPedido(pedido);
		pedido.getTransacoesPedido().add(transacaoPedido);
		
		StatusPedido statusPedido = new StatusPedido();
		statusPedido.setId(7);
		pedido.setStatusPedido(statusPedido);
		alterarPedidoCommand.executar(pedido);
		return "";
	}
	
	@PutMapping(path = "finalizar-troca-pedido/{idPedido}")
	@ResponseBody
	public String finalizarTrocaDoPedido(@PathVariable("idPedido") Integer idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		pedido.setTransacoesPedido(new ArrayList<>());
		TransacaoPedido transacaoPedido = new TransacaoPedido();
		transacaoPedido.setDescricao("Troca do pedido finalizado.");
		Date dtCadastro = new Date();
		transacaoPedido.setDtCadastro(dtCadastro);
		transacaoPedido.setPedido(pedido);
		pedido.getTransacoesPedido().add(transacaoPedido);
		
		StatusPedido statusPedido = new StatusPedido();
		statusPedido.setId(8);
		pedido.setStatusPedido(statusPedido);
		String retornoAlteracaoPedido = (String) alterarPedidoCommand.executar(pedido);
		
		if(retornoAlteracaoPedido != null && !retornoAlteracaoPedido.isBlank()) {
			return "Erro: " + retornoAlteracaoPedido;
		}
		
		List<Pedido> pedidos = (List<Pedido>) consultarPedidoCommand.executar(pedido);
		Pedido pedidoBD = pedidos.get(0);
		
		CupomCliente cupomCliente = new CupomCliente();
		cupomCliente.setCliente(pedidoBD.getCliente());
		Cupom cupom = new Cupom();
		cupom.setValor(pedidoBD.getValorTotal());
		cupomCliente.setCupom(cupom);
		cupom.setCliente(cupomCliente);
		String retornoGerarCupomTroca = (String) salvarCupomCommand.executar(cupomCliente);

		if(retornoGerarCupomTroca != null && !retornoGerarCupomTroca.isBlank()) {
			return "Erro: " + retornoGerarCupomTroca;
		}
		
		return cupomCliente.getCupom().getCodigo();
	}

	@PutMapping(path = "trocar-pedido/{idPedido}")
	@ResponseBody
	public String trocarPedidoTotalmente(@PathVariable(name = "idPedido") Integer idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		pedido.setTransacoesPedido(new ArrayList<>());
		TransacaoPedido transacaoPedido = new TransacaoPedido();
		transacaoPedido.setDescricao("Solicitacao de troca efetuada para este pedido.");
		Date dtCadastro = new Date();
		transacaoPedido.setDtCadastro(dtCadastro);
		transacaoPedido.setPedido(pedido);
		pedido.getTransacoesPedido().add(transacaoPedido);
		
		StatusPedido statusPedido = new StatusPedido();
		statusPedido.setId(6);
		pedido.setStatusPedido(statusPedido);
		pedido.setTrocaSolicitada(true);
		String retorno = (String) alterarPedidoCommand.executar(pedido);
		if (retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}
		return "Solicitação de troca efetuada com sucesso. " + pedido.getId();
	}

	@PutMapping(path = "trocar-itens-pedido/{idPedido}")
	@ResponseBody
	public String trocarPedidoTotalmente(@PathVariable(name = "idPedido") Integer idPedido, @RequestBody Pedido pedido) {
		System.out.println(idPedido);
		System.out.println(pedido.getItensPedido());
		//

		Pedido pedidoAlterar = new Pedido();
		pedidoAlterar.setId(idPedido);
		pedidoAlterar.setTrocaSolicitada(true);
		pedidoAlterar.setStatusPedido(pedido.getStatusPedido());
		pedidoAlterar.setTransacoesPedido(new ArrayList<>());
		TransacaoPedido transacaoPedido = new TransacaoPedido();
		transacaoPedido.setDescricao("Solicitacao de troca efetuada para este pedido.");
		Date dtCadastro = new Date();
		transacaoPedido.setDtCadastro(dtCadastro);
		transacaoPedido.setPedido(pedidoAlterar);
		pedidoAlterar.getTransacoesPedido().add(transacaoPedido);
		String retorno = (String) alterarPedidoCommand.executar(pedidoAlterar);
		if (retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}
		
		pedido.setId(null);
		pedido.setTransacoesPedido(null);
		pedido.setCupons(null);
		pedido.setPedidoPagamentos(null);
		pedido.getEnderecoEntrega().setId(null);
		pedido.getEnderecoEntrega().setPedido(pedido);
		pedido.getEnderecoEntrega().setValorFrete(BigDecimal.ZERO);
		pedido.getEnderecoEntrega().setDataEntrega(null);
		for(ItemPedido itemPedido: pedido.getItensPedido()) {
			itemPedido.setId(null);
			itemPedido.setPedido(pedido);
		}

		pedido.setIdStatusPedido(6);
		String mensagens = (String) salvarPedidoCommand.executar(pedido);
		if(mensagens != null && !mensagens.trim().isBlank()) {
			return "Erro: " + mensagens;
		}
		
		return "Solicitação de troca de itens do pedido efetuada com sucesso. " + pedido.getId();
	}
}
