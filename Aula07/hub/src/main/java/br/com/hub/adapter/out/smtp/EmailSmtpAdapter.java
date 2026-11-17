package br.com.hub.adapter.out.smtp;

import br.com.hub.application.port.out.NotificadorEmailPort;
import br.com.hub.domain.Fatura;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class EmailSmtpAdapter implements NotificadorEmailPort {

	@Override
	public String notificar(Fatura fatura) {
		String conteudo = "Fatura " + fatura.getId() + " no valor de " + fatura.getValor();
		try {
			byte[] digest = MessageDigest.getInstance("SHA-256").digest(conteudo.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(digest);
		} catch (NoSuchAlgorithmException exception) {
			throw new IllegalStateException("SHA-256 não está disponível nesta JVM.", exception);
		}
	}
}
