package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.tipo_logradouro.ConsultarTipoLogradouroCommand;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;
import br.com.tcon.coracaopapel.modelo.dominio.TipoLogradouro;

@Controller
@RequestMapping("tipos-logradouro")
public class CtrlTipoLogradouro {

	@Autowired
	private ConsultarTipoLogradouroCommand consultarTipoLogradouroCommand;

	@GetMapping
	@ResponseBody
	public List<TipoEndereco> obterTodosTiposLogradouro() {
		return (List<TipoEndereco>) consultarTipoLogradouroCommand.executar(new TipoLogradouro());
	}
}
