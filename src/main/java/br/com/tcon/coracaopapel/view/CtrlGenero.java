package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.genero.ConsultarGeneroCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Genero;

@Controller
@RequestMapping("generos")
public class CtrlGenero {

	@Autowired
	private ConsultarGeneroCommand consultarGeneroCommand;

	@GetMapping
	@ResponseBody
	public List<Genero> obterTodosTiposDocumento() {
		return (List<Genero>) consultarGeneroCommand.executar(new Genero());
	}
}
