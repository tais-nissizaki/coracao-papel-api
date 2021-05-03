package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.bandeira_cartao.ConsultarBandeiraCartaoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.BandeiraCartao;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Controller
@RequestMapping("bandeiras-cartao")
public class CtrlBandeiraCartao {

	@Autowired
	private ConsultarBandeiraCartaoCommand consultarBandeiraCartaoCommand;

	@GetMapping
	@ResponseBody
	public List<EntidadeDominio> obterTodosBandeiraCartao() {
		return consultarBandeiraCartaoCommand.executar(new BandeiraCartao());
	}
}
