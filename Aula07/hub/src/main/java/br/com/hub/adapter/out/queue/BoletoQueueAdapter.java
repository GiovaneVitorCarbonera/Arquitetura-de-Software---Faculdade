package br.com.hub.adapter.out.queue;

import br.com.hub.application.port.out.EmissorBoletoPort;
import br.com.hub.domain.Fatura;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class BoletoQueueAdapter implements EmissorBoletoPort {

	private final LinkedBlockingQueue<Long> fila = new LinkedBlockingQueue<>();

	@Override
	public String emitir(Fatura fatura) {
		fila.add(fatura.getId());
		Long idProcessado = fila.poll();
		if (idProcessado == null) {
			throw new IllegalStateException("Não foi possível consumir a solicitação de boleto.");
		}
		return String.format("%044d", idProcessado);
	}
}
