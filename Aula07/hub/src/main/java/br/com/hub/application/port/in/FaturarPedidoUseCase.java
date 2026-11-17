package br.com.hub.application.port.in;

import br.com.hub.domain.Fatura;

import java.math.BigDecimal;

public interface FaturarPedidoUseCase {
	Fatura faturar(String pedidoId, String clienteEmail, BigDecimal valor);
}
