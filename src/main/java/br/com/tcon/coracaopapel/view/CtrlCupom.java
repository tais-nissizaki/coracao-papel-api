package br.com.tcon.coracaopapel.view;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcon.coracaopapel.controle.cupom.ConsultarCupomCommand;
import br.com.tcon.coracaopapel.controle.cupom_cliente.ConsultarCupomClienteCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;

@RestController
@RequestMapping("cupons")
public class CtrlCupom {
	
	@Autowired
	private ConsultarCupomCommand consultarCupomCommand;
	
	@Autowired
	private ConsultarCupomClienteCommand consultarCupomClienteCommand;
	
	@GetMapping(path = {
			"cliente/{idCliente}/{cupom}",
			"{cupom}"
			})
	@ResponseBody
	public Cupom verificarValidadeCupom(@PathVariable(name = "idCliente", required = false) Integer idCliente, @PathVariable("cupom") String codigoCupom) {
		Cupom cupom = new Cupom();
		cupom.setCodigo(codigoCupom);
		cupom.setInicioVigencia(new Date());
		cupom.setFinalVigencia(new Date());
		if(idCliente != null) {
			cupom.setCliente(new CupomCliente());
			cupom.getCliente().setCliente(new Cliente());
			cupom.getCliente().getCliente().setId(idCliente);
		}
		List<Cupom> cuponsValidos = (List<Cupom>) consultarCupomCommand.executar(cupom);
		if(cuponsValidos != null && !cuponsValidos.isEmpty()) {
			return cuponsValidos.get(0);
		} else {
			return null;
		}
	}
	
	@GetMapping("cliente/{idCliente}")
	@ResponseBody
	public List obterCuponsCliente(
			@PathVariable(name = "idCliente") Integer idCliente,
			@RequestParam(name = "dataInicial")@DateTimeFormat(pattern = "dd/MM/yyy") Date periodoInicial,
			@RequestParam(name = "dataFinal")@DateTimeFormat(pattern = "dd/MM/yyy") Date periodoFinal) {
		CupomCliente cupomCliente = new CupomCliente();
		cupomCliente.setCliente(new Cliente());
		cupomCliente.getCliente().setId(idCliente);

		Cupom cupom = new Cupom();
		cupom.setInicioVigencia(periodoInicial);
		cupom.setFinalVigencia(periodoFinal);
		cupomCliente.setCupom(cupom);
		return (List<Cupom>) consultarCupomClienteCommand.executar(cupomCliente);
	}
	

}
