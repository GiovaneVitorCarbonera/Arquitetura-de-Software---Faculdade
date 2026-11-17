package br.com.hub.application.service;

import br.com.hub.application.port.out.EmissorBoletoPort;
import br.com.hub.application.port.out.EmissorNotaFiscalPort;
import br.com.hub.application.port.out.FaturaRepositoryPort;
import br.com.hub.application.port.out.NotificadorEmailPort;
import br.com.hub.domain.Fatura;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class FaturamentoServiceTest {

	@Test
	void criaFaturaExecutaAdaptadoresEReutilizaFaturaEmRepeticao() {
		Map<String, Fatura> faturas = new HashMap<>();
		AtomicLong ids = new AtomicLong();
		FaturaRepositoryPort repository = new FaturaRepositoryPort() {
			@Override
			public Fatura salvar(Fatura fatura) {
				if (fatura.getId() == null) {
					fatura.atribuirId(ids.incrementAndGet());
				}
				faturas.put(fatura.getPedidoId(), fatura);
				return fatura;
			}

			@Override
			public Optional<Fatura> buscarPorPedidoId(String pedidoId) {
				return Optional.ofNullable(faturas.get(pedidoId));
			}
		};
		EmissorBoletoPort boleto = fatura -> "1".repeat(44);
		EmissorNotaFiscalPort nota = fatura -> "2".repeat(44);
		NotificadorEmailPort email = fatura -> "hash-email";
		FaturamentoService service = new FaturamentoService(repository, boleto, nota, email);

		Fatura primeira = service.faturar("pedido-1", "cliente@exemplo.com", new BigDecimal("25.00"));
		Fatura repetida = service.faturar("pedido-1", "cliente@exemplo.com", new BigDecimal("25.00"));

		assertSame(primeira, repetida);
		assertEquals(1L, primeira.getId());
		assertEquals("1".repeat(44), primeira.getCodigoBarras());
		assertEquals("2".repeat(44), primeira.getChaveAcesso());
		assertEquals("hash-email", primeira.getHashEmail());
	}
}
