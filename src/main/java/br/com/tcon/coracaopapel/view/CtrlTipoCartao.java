package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.tipo_cartao.ConsultarTipoCartaoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCartao;

@Controller
@RequestMapping("tipos-cartao")
public class CtrlTipoCartao {

	@Autowired
	private ConsultarTipoCartaoCommand consultarTipoCartaoCommand;

	@GetMapping
	@ResponseBody
	public List<EntidadeDominio> obterTodosTiposCartao() {
		return consultarTipoCartaoCommand.executar(new TipoCartao());
	}
}
