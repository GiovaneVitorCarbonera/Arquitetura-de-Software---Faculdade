package br.com.vendas.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
class FaturamentoClientConfiguration {

	@Bean
	RestClient faturamentoRestClient(
			@Value("${hub.faturamento.url}") String baseUrl,
			@Value("${hub.faturamento.timeout:3s}") Duration timeout) {
		HttpClient httpClient = HttpClient.newBuilder().connectTimeout(timeout).build();
		JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
		factory.setReadTimeout(timeout);
		return RestClient.builder().baseUrl(baseUrl).requestFactory(factory).build();
	}
}

@Component
public class FaturamentoClient {

	private final RestClient restClient;

	public FaturamentoClient(RestClient faturamentoRestClient) {
		this.restClient = faturamentoRestClient;
	}

	public FaturaResponse faturar(PedidoRequest pedido) {
		FaturaResponse response = restClient.post()
				.uri("/api/faturas")
				.body(pedido)
				.retrieve()
				.body(FaturaResponse.class);
		if (response == null || response.id() == null) {
			throw new IllegalStateException("O hub respondeu sem o ID da fatura.");
		}
		return response;
	}

	public record PedidoRequest(String pedidoId, String clienteEmail, java.math.BigDecimal valor) {
	}

	public record FaturaResponse(Long id, String codigoBarras, String chaveAcesso, String hashEmail) {
	}
}
