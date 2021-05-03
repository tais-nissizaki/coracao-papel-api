
package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.pais.ConsultarPaisCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Pais;

@Controller
@RequestMapping("paises")
public class CtrlPais {
	
	@Autowired
	private ConsultarPaisCommand consultarPaisCommand;
	
	@GetMapping
	@ResponseBody
	public List obterPaises() {
		return (List) consultarPaisCommand.executar(new Pais());
	}
}
