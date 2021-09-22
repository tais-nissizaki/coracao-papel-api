package br.com.tcon.coracaopapel.negocio.produto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Dimensao;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarDadosObrigatoriosProdutoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder retorno = new StringBuilder();
		Produto produto = (Produto) entidade;
		if(produto.getGrupoPrecificacao() == null || produto.getGrupoPrecificacao().getId() == null || produto.getGrupoPrecificacao().getId() < 1) {
			retorno.append("O grupo de precificação é obrigatório. ");
		}
		if(produto.getTitulo() == null || produto.getTitulo().isBlank()) {
			retorno.append("O titulo do livro é obrigatório.");
		}
		if(produto.getAutor() == null || produto.getAutor().isBlank()) {
			retorno.append("O nome do autor é obrigatório. ");
		}
		if(produto.getCategorias() == null || produto.getCategorias().isEmpty()) {
			retorno.append("É necessário selecionar pelo menos uma categoria. ");
		}
		if(produto.getAnoEdicao() == null || produto.getAnoEdicao() == 0) {
			retorno.append("O ano da edição é obrigatório. ");
		} else if(produto.getAnoEdicao() < 0 || produto.getAnoEdicao() > Calendar.getInstance().get(Calendar.YEAR)) {
			retorno.append("O ano da edição é inválido. ");
		}
		if(produto.getEditora() == null || produto.getEditora().isEmpty()) {
			retorno.append("A editora é obrigatória. ");
		}
		if(produto.getEdicao() == null || produto.getEdicao().isEmpty()) {
			retorno.append("A edição é obrigatória. ");
		}
		if(produto.getIsbn() == null || produto.getIsbn().isEmpty()) {
			retorno.append("O código ISBN é obrigatório. ");
		}
		if(produto.getNumeroPaginas() == null) {
			retorno.append("O número de páginas é obrigatório. ");
		} else if(produto.getNumeroPaginas() < 1 ) {
			retorno.append("O ano da edição é inválido. ");
		}
		if(produto.getSinopse() == null || produto.getSinopse().isEmpty()) {
			retorno.append("A sinopse é obrigatória. ");
		}
		if(produto.getCodigoBarras() == null || produto.getCodigoBarras().isEmpty()) {
			retorno.append("O código de barras é obrigatório. ");
		}
		if(produto.getDimensao() == null) {
			retorno.append("Os dados da dimensão são obrigatórios. ");
		} else {
			Dimensao dimensao = produto.getDimensao();
			if(dimensao.getAltura() == null || dimensao.getAltura().compareTo(BigDecimal.ZERO) <= 0) {
				retorno.append("Altura do produto inválido. ");
			}
			if(dimensao.getLargura() == null || dimensao.getLargura().compareTo(BigDecimal.ZERO) <= 0) {
				retorno.append("Largura do produto inválido. ");
			}
			if(dimensao.getProfundidade() == null || dimensao.getProfundidade().compareTo(BigDecimal.ZERO) <= 0) {
				retorno.append("Profundidade do produto inválido. ");
			}
			if(dimensao.getPeso() == null || dimensao.getPeso().compareTo(BigDecimal.ZERO) <= 0) {
				retorno.append("Peso do produto inválido. ");
			}
		}
		
		return retorno.toString();
	}

}
