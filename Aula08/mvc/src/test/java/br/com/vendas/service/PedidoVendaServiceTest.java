package br.com.vendas.service;

import br.com.vendas.integration.FaturamentoClient;
import br.com.vendas.integration.FaturamentoClient.FaturaResponse;
import br.com.vendas.model.PedidoVenda;
import br.com.vendas.model.StatusPedido;
import br.com.vendas.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoVendaServiceTest {

	@Test
	void guardaIdDaFaturaRetornadoPeloHub() {
		PedidoRepository repository = mock(PedidoRepository.class);
		FaturamentoClient client = mock(FaturamentoClient.class);
		when(repository.save(any(PedidoVenda.class))).thenAnswer(invocation -> {
			PedidoVenda pedido = invocation.getArgument(0);
			return pedido;
		});
		when(client.faturar(any())).thenReturn(new FaturaResponse(42L, null, null, null));
		PedidoVendaService service = new PedidoVendaService(repository, client);

		var resultado = service.fechar("Ana", "ana@exemplo.com", new BigDecimal("49.90"));

		assertEquals(42L, resultado.pedido().getFaturaId());
		assertEquals(StatusPedido.FATURADO, resultado.pedido().getStatus());
		verify(repository, org.mockito.Mockito.times(2)).save(any(PedidoVenda.class));
	}

	@Test
	void mantemPedidoPendenteQuandoHubNaoResponde() {
		PedidoRepository repository = mock(PedidoRepository.class);
		FaturamentoClient client = mock(FaturamentoClient.class);
		AtomicLong ids = new AtomicLong();
		when(repository.save(any(PedidoVenda.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(repository.findById(any())).thenReturn(Optional.empty());
		when(client.faturar(any())).thenThrow(new ResourceAccessException("timeout"));
		PedidoVendaService service = new PedidoVendaService(repository, client);

		var resultado = service.fechar("Ana", "ana@exemplo.com", new BigDecimal("49.90"));

		assertEquals(StatusPedido.PENDENTE_FATURAMENTO, resultado.pedido().getStatus());
		assertNull(resultado.pedido().getFaturaId());
		assertEquals("Pedido registrado e pendente de faturamento: hub indisponível ou resposta inválida.",
				resultado.mensagem());
	}
}
