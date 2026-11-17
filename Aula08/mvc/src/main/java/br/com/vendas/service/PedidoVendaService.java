package br.com.vendas.service;

import br.com.vendas.integration.FaturamentoClient;
import br.com.vendas.integration.FaturamentoClient.FaturaResponse;
import br.com.vendas.integration.FaturamentoClient.PedidoRequest;
import br.com.vendas.model.PedidoVenda;
import br.com.vendas.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoVendaService {

	private final PedidoRepository repository;
	private final FaturamentoClient faturamentoClient;

	public PedidoVendaService(PedidoRepository repository, FaturamentoClient faturamentoClient) {
		this.repository = repository;
		this.faturamentoClient = faturamentoClient;
	}

	public List<PedidoVenda> listar() {
		return repository.findAll();
	}

	public ResultadoFechamento fechar(String cliente, String email, BigDecimal valor) {
		PedidoVenda pedido = repository.save(new PedidoVenda(cliente, email, valor));
		return faturar(pedido);
	}

	@Transactional
	public ResultadoFechamento faturar(Long pedidoId) {
		PedidoVenda pedido = repository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));
		if (pedido.getFaturaId() != null) {
			return new ResultadoFechamento(pedido, "Pedido já faturado.");
		}
		return faturar(pedido);
	}

	private ResultadoFechamento faturar(PedidoVenda pedido) {
		try {
			FaturaResponse fatura = faturamentoClient.faturar(new PedidoRequest(
					pedido.getChaveIntegracao(), pedido.getClienteEmail(), pedido.getValor()));
			pedido.registrarFatura(fatura.id());
			repository.save(pedido);
			return new ResultadoFechamento(pedido, "Pedido faturado com sucesso.");
		} catch (RestClientException exception) {
			repository.save(pedido);
			return new ResultadoFechamento(pedido,
					"Pedido registrado e pendente de faturamento: hub indisponível ou resposta inválida.");
		}
	}

	public record ResultadoFechamento(PedidoVenda pedido, String mensagem) {
	}
}
