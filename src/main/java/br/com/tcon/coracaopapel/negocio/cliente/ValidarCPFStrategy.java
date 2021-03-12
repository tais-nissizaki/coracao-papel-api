package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Documento;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarCPFStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder retorno = new StringBuilder();
		if(entidade instanceof Documento) {
			Documento documentoCPF = (Documento) entidade;
			String cpfSemMascara = documentoCPF.getCodigo().replace("\\D", "");
			while (cpfSemMascara.length() < 11) {
				cpfSemMascara = "0"+cpfSemMascara;
			}
			
			// considera-se erro CPF's formados por uma sequencia de numeros iguais
			if (cpfSemMascara.equals("00000000000") || cpfSemMascara.equals("11111111111")
					|| cpfSemMascara.equals("22222222222") || cpfSemMascara.equals("33333333333")
					|| cpfSemMascara.equals("44444444444") || cpfSemMascara.equals("55555555555")
					|| cpfSemMascara.equals("66666666666") || cpfSemMascara.equals("77777777777")
					|| cpfSemMascara.equals("88888888888") || cpfSemMascara.equals("99999999999")
					|| (cpfSemMascara.length() != 11)) {
				retorno.append("CPF inválido");
			} else {
		
				String primeiroDV, segundoDV;
				int somatorio, resto, peso;
				// Calculo do 1o. Digito Verificador
				somatorio = 0;
				peso = 10;
				for (int i = 0; i < 9; i++) {
					somatorio = somatorio + (Integer.valueOf(cpfSemMascara.substring(i, i+1)) * (peso-i));
				}
	
				resto = 11 - (somatorio % 11);
				if ((resto == 10) || (resto == 11)) {
					primeiroDV = "0";
				} else {
					primeiroDV = String.valueOf(resto);
				}
				if (!primeiroDV.equals(cpfSemMascara.substring(9, 10))) {
					retorno.append("CPF inválido.");
				} else {
					// Calculo do 2o. Digito Verificador
					somatorio = 0;
					peso = 11;
					for (int i = 0; i < 10; i++) {
						somatorio = somatorio + (Integer.valueOf(cpfSemMascara.substring(i, i+1)) * (peso-i));
					}
		
					resto = 11 - (somatorio % 11);
					if ((resto == 10) || (resto == 11)) {
						segundoDV = "0";
					} else {
						segundoDV = String.valueOf(resto);
					}
		
					// Verifica se os digitos calculados conferem com os digitos informados.
					if (!segundoDV.equals(cpfSemMascara.substring(10, 11))) {
						retorno.append("CPF inválido.");
					}
				}
			}
		} else if(entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			if(cliente.getDocumentos() != null && !cliente.getDocumentos().isEmpty()) {
				for(int i=0; i< cliente.getDocumentos().size(); i++) {
					if(cliente.getDocumentos().get(i).getTipoDocumento().getNome().equalsIgnoreCase("CPF")) {
						return processar(cliente.getDocumentos().get(i));
					}
				}
			}
		}
		return retorno.toString();
	}

}
