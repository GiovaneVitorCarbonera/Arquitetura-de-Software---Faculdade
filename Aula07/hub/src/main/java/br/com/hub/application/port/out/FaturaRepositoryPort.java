package br.com.hub.application.port.out;

import br.com.hub.domain.Fatura;

import java.util.Optional;

public interface FaturaRepositoryPort {
	Fatura salvar(Fatura fatura);
	Optional<Fatura> buscarPorPedidoId(String pedidoId);
}
