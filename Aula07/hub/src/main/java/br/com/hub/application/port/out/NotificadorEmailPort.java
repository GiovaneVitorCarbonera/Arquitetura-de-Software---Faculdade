package br.com.hub.application.port.out;

import br.com.hub.domain.Fatura;

public interface NotificadorEmailPort {
	String notificar(Fatura fatura);
}
