package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.tipo_telefone.ConsultarTipoTelefoneCommand;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;
import br.com.tcon.coracaopapel.modelo.dominio.TipoTelefone;

@Controller
@RequestMapping("tipos-telefone")
public class CtrlTipoTelefone {

	@Autowired
	private ConsultarTipoTelefoneCommand consultarTipoTelefoneCommand;

	@GetMapping
	@ResponseBody
	public List<TipoEndereco> obterTodosTiposTelefoneo() {
		return (List<TipoEndereco>) consultarTipoTelefoneCommand.executar(new TipoTelefone());
	}
}
