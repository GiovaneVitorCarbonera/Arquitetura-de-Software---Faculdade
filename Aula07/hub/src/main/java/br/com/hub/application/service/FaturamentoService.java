package br.com.hub.application.service;

import br.com.hub.application.port.in.FaturarPedidoUseCase;
import br.com.hub.application.port.out.EmissorBoletoPort;
import br.com.hub.application.port.out.EmissorNotaFiscalPort;
import br.com.hub.application.port.out.FaturaRepositoryPort;
import br.com.hub.application.port.out.NotificadorEmailPort;
import br.com.hub.domain.Fatura;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FaturamentoService implements FaturarPedidoUseCase {

	private final FaturaRepositoryPort repository;
	private final EmissorBoletoPort emissorBoleto;
	private final EmissorNotaFiscalPort emissorNotaFiscal;
	private final NotificadorEmailPort notificadorEmail;

	public FaturamentoService(FaturaRepositoryPort repository, EmissorBoletoPort emissorBoleto,
			EmissorNotaFiscalPort emissorNotaFiscal, NotificadorEmailPort notificadorEmail) {
		this.repository = repository;
		this.emissorBoleto = emissorBoleto;
		this.emissorNotaFiscal = emissorNotaFiscal;
		this.notificadorEmail = notificadorEmail;
	}

	@Override
	@Transactional
	public Fatura faturar(String pedidoId, String clienteEmail, BigDecimal valor) {
		return repository.buscarPorPedidoId(pedidoId).orElseGet(() -> criarFatura(pedidoId, clienteEmail, valor));
	}

	private Fatura criarFatura(String pedidoId, String clienteEmail, BigDecimal valor) {
		Fatura fatura = repository.salvar(Fatura.criar(pedidoId, clienteEmail, valor));
		fatura.registrarCodigoBarras(emissorBoleto.emitir(fatura));
		fatura.registrarChaveAcesso(emissorNotaFiscal.emitir(fatura));
		fatura.registrarHashEmail(notificadorEmail.notificar(fatura));
		return repository.salvar(fatura);
	}
}
