package br.com.tcon.coracaopapel.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.pedido.ConsultarPedidoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Categoria;
import br.com.tcon.coracaopapel.modelo.dominio.ItemPedido;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.view.dto.ItemDashboard;
import br.com.tcon.coracaopapel.view.dto.ItemPorData;

@Controller
@RequestMapping(path = "dashboard")
public class CtrlDashboard {
	
	@Autowired
	private ConsultarPedidoCommand consultarPedidoCommand;
	
	@GetMapping(path = "produtos")
	@ResponseBody
	public List<ItemDashboard> obterDadosGraficoPorProduto(
			@RequestParam("dataInicial") @DateTimeFormat(pattern = "yyyy-MM-dd")Date dataInicial,
			@RequestParam("dataFinal") @DateTimeFormat(pattern = "yyyy-MM-dd")Date dataFinal) {
		Pedido pedidoFiltro = new Pedido();
		pedidoFiltro.setInicioFiltro(dataInicial);
		pedidoFiltro.setFimFiltro(dataFinal);
		List<Pedido> pedidos = (List<Pedido>) consultarPedidoCommand.executar(pedidoFiltro);
		List<ItemDashboard> itensDashboard = new ArrayList<>();
		if(pedidos != null && !pedidos.isEmpty()) {
			for(Pedido pedido: pedidos) {
				for(ItemPedido itemPedido: pedido.getItensPedido()) {
					adicionarItemDashboardPorTituloProduto(itensDashboard, pedido.getDtCadastro(), itemPedido);
				}
			}
		}
		return itensDashboard;
	}
	
	private void adicionarItemDashboardPorTituloProduto(List<ItemDashboard> itensDashboard, Date dataPedido, ItemPedido itemPedido) {
		ItemDashboard itemDashboard = encontrarItemDashboard(itensDashboard, itemPedido.getProduto().getTitulo());
		boolean encontrou = false;
		for(ItemPorData itemPorData: itemDashboard.getItensPorData()) {
			if(itemPorData.getData().equals(dataPedido)) {
				if(itemPedido.getQuantidade() != null) {					
					itemPorData.adicionarQuantidade(itemPedido.getQuantidade());
				} else {
					itemPorData.adicionarQuantidade(0);
				}
				encontrou = true;
			}
		}
		if(!encontrou) {
			ItemPorData itemPorDataAdd = new ItemPorData();
			dataPedido.setHours(0);
			dataPedido.setMinutes(0);
			dataPedido.setSeconds(0);
			itemPorDataAdd.setData(dataPedido);
			if(itemPedido.getQuantidade() != null) {					
				itemPorDataAdd.adicionarQuantidade(itemPedido.getQuantidade());
			} else {
				itemPorDataAdd.adicionarQuantidade(0);
			}
			itemDashboard.getItensPorData().add(itemPorDataAdd);
		}
	}

	private ItemDashboard encontrarItemDashboard(List<ItemDashboard> itensDashboard, String item) {
		for(ItemDashboard itemDashboard: itensDashboard) {
			if(itemDashboard.getItem().equals(item)) {
				return itemDashboard;
			}
		}
		
		ItemDashboard itemDashboard = new ItemDashboard(item);
		itensDashboard.add(itemDashboard);
		return itemDashboard;
		
	}
	

	@GetMapping(path = "categorias")
	@ResponseBody
	public List<ItemDashboard> obterDadosGraficoPorCategoria(
			@RequestParam("dataInicial") @DateTimeFormat(pattern = "yyyy-MM-dd")Date dataInicial,
			@RequestParam("dataFinal") @DateTimeFormat(pattern = "yyyy-MM-dd")Date dataFinal) {
		Pedido pedidoFiltro = new Pedido();
		pedidoFiltro.setInicioFiltro(dataInicial);
		pedidoFiltro.setFimFiltro(dataFinal);
		List<Pedido> pedidos = (List<Pedido>) consultarPedidoCommand.executar(pedidoFiltro);
		List<ItemDashboard> itensDashboard = new ArrayList<>();
		if(pedidos != null && !pedidos.isEmpty()) {
			for(Pedido pedido: pedidos) {
				for(ItemPedido itemPedido: pedido.getItensPedido()) {
					adicionarItemDashboardPorCategoria(itensDashboard, pedido.getDtCadastro(), itemPedido);
				}
			}
		}
		return itensDashboard;
	}
	
	private void adicionarItemDashboardPorCategoria(List<ItemDashboard> itensDashboard, Date dataPedido, ItemPedido itemPedido) {
		Date dataPedidoComparacao = new Date(dataPedido.getTime());
		dataPedidoComparacao.setHours(0);
		dataPedidoComparacao.setMinutes(0);
		dataPedidoComparacao.setSeconds(0);
		for(Categoria categoria: itemPedido.getProduto().getCategorias()) {
			ItemDashboard itemDashboard = encontrarItemDashboard(itensDashboard, categoria.getDescricao());
			boolean encontrou = false;
			for(ItemPorData itemPorData: itemDashboard.getItensPorData()) {
				if(itemPorData.getData().equals(dataPedidoComparacao)) {
					if(itemPedido.getQuantidade() != null) {					
						itemPorData.adicionarQuantidade(itemPedido.getQuantidade());
					} else {
						itemPorData.adicionarQuantidade(0);
					}
					encontrou = true;
				}
			}
			if(!encontrou) {
				ItemPorData itemPorDataAdd = new ItemPorData();
				itemPorDataAdd.setData(dataPedidoComparacao);
				if(itemPedido.getQuantidade() != null) {					
					itemPorDataAdd.adicionarQuantidade(itemPedido.getQuantidade());
				} else {
					itemPorDataAdd.adicionarQuantidade(0);
				}
				itemDashboard.getItensPorData().add(itemPorDataAdd);
			}
		}
	}

	
}
