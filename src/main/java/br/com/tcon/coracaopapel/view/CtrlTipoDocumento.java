package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.tipo_documento.ConsultarTipoDocumentoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.TipoDocumento;

@Controller
@RequestMapping("tipos-documento")
public class CtrlTipoDocumento {

	@Autowired
	private ConsultarTipoDocumentoCommand consultarTipoDocumentoCommand;

	@GetMapping
	@ResponseBody
	public List<TipoDocumento> obterTodosTiposDocumento() {
		return (List<TipoDocumento>) consultarTipoDocumentoCommand.executar(new TipoDocumento());
	}
}
