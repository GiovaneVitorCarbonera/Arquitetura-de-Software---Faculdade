package br.com.hub.adapter.out.soap;

import br.com.hub.application.port.out.EmissorNotaFiscalPort;
import br.com.hub.domain.Fatura;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalSoapAdapter implements EmissorNotaFiscalPort {

	@Override
	public String emitir(Fatura fatura) {
		return String.format("%044d", fatura.getId());
	}
}
