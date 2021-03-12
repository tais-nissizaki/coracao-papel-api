package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.tipo_documento.ConsultarTipoDocumentoCommand;
import br.com.tcon.coracaopapel.controle.tipo_endereco.ConsultarTipoEnderecoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.TipoDocumento;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;

@Controller
@RequestMapping("tipos-endereco")
public class CtrlTipoEndereco {

	@Autowired
	private ConsultarTipoEnderecoCommand consultarTipoEnderecoCommand;

	@GetMapping
	@ResponseBody
	public List<TipoEndereco> obterTodosTiposDocumento() {
		return (List<TipoEndereco>) consultarTipoEnderecoCommand.executar(new TipoEndereco());
	}
}
