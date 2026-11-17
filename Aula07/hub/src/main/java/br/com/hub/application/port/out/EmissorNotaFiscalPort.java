package br.com.hub.application.port.out;

import br.com.hub.domain.Fatura;

public interface EmissorNotaFiscalPort {
	String emitir(Fatura fatura);
}
