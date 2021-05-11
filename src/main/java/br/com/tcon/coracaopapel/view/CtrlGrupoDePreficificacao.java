package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.grupo_precificacao.ConsultarGrupoPrecificacaoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.GrupoPrecificacao;

@Controller
@RequestMapping("grupos-precificacao")
public class CtrlGrupoDePreficificacao {

	@Autowired
	private ConsultarGrupoPrecificacaoCommand consultarGrupoPrecificacaoCommand;

	@GetMapping
	@ResponseBody
	public List<EntidadeDominio> obterTodosGruposPrecificacao() {
		return (List<EntidadeDominio>) consultarGrupoPrecificacaoCommand.executar(new GrupoPrecificacao());
	}
}
