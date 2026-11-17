package br.com.hub.adapter.in.rest;

import br.com.hub.application.port.in.FaturarPedidoUseCase;
import br.com.hub.domain.Fatura;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/faturas")
public class FaturamentoController {

	private final FaturarPedidoUseCase useCase;

	public FaturamentoController(FaturarPedidoUseCase useCase) {
		this.useCase = useCase;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FaturaResponse faturar(@Valid @RequestBody PedidoRequest pedido) {
		Fatura fatura = useCase.faturar(pedido.pedidoId(), pedido.clienteEmail(), pedido.valor());
		return new FaturaResponse(fatura.getId(), fatura.getCodigoBarras(),
				fatura.getChaveAcesso(), fatura.getHashEmail());
	}

	public record PedidoRequest(
			@NotBlank String pedidoId,
			@NotBlank @Email String clienteEmail,
			@NotNull @Positive BigDecimal valor) {
	}

	public record FaturaResponse(Long id, String codigoBarras, String chaveAcesso, String hashEmail) {
	}
}
