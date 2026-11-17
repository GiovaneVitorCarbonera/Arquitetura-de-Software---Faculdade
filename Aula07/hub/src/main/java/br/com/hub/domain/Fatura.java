package br.com.hub.domain;

import java.math.BigDecimal;

public class Fatura {

	private Long id;
	private String pedidoId;
	private String clienteEmail;
	private BigDecimal valor;
	private String codigoBarras;
	private String chaveAcesso;
	private String hashEmail;

	public Fatura(Long id, String pedidoId, String clienteEmail, BigDecimal valor,
			String codigoBarras, String chaveAcesso, String hashEmail) {
		this.id = id;
		this.pedidoId = pedidoId;
		this.clienteEmail = clienteEmail;
		this.valor = valor;
		this.codigoBarras = codigoBarras;
		this.chaveAcesso = chaveAcesso;
		this.hashEmail = hashEmail;
	}

	public static Fatura criar(String pedidoId, String clienteEmail, BigDecimal valor) {
		return new Fatura(null, pedidoId, clienteEmail, valor, null, null, null);
	}

	public void atribuirId(Long id) {
		this.id = id;
	}

	public void registrarCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public void registrarChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}

	public void registrarHashEmail(String hashEmail) {
		this.hashEmail = hashEmail;
	}

	public Long getId() {
		return id;
	}

	public String getPedidoId() {
		return pedidoId;
	}

	public String getClienteEmail() {
		return clienteEmail;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public String getChaveAcesso() {
		return chaveAcesso;
	}

	public String getHashEmail() {
		return hashEmail;
	}
}
