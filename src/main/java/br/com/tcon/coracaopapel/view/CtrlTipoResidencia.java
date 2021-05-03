package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.tipo_residencia.ConsultarTipoResidenciaCommand;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;
import br.com.tcon.coracaopapel.modelo.dominio.TipoResidencia;

@Controller
@RequestMapping("tipos-residencia")
public class CtrlTipoResidencia {

	@Autowired
	private ConsultarTipoResidenciaCommand consultarTipoResidenciaCommand;

	@GetMapping
	@ResponseBody
	public List<TipoEndereco> obterTodosTiposResidencia() {
		return (List<TipoEndereco>) consultarTipoResidenciaCommand.executar(new TipoResidencia());
	}
}
