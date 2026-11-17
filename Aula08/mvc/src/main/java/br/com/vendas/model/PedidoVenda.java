package br.com.vendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "pedidos_venda")
public class PedidoVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String chaveIntegracao;

	@Column(nullable = false)
	private String cliente;

	@Column(nullable = false)
	private String clienteEmail;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal valor;

	private Long faturaId;

	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	protected PedidoVenda() {
	}

	public PedidoVenda(String cliente, String clienteEmail, BigDecimal valor) {
		this.chaveIntegracao = UUID.randomUUID().toString();
		this.cliente = cliente;
		this.clienteEmail = clienteEmail;
		this.valor = valor;
		this.status = StatusPedido.PENDENTE_FATURAMENTO;
	}

	public void registrarFatura(Long faturaId) {
		if (faturaId == null) {
			throw new IllegalArgumentException("O hub não retornou o ID da fatura.");
		}
		this.faturaId = faturaId;
		this.status = StatusPedido.FATURADO;
	}

	public Long getId() {
		return id;
	}

	public String getChaveIntegracao() {
		return chaveIntegracao;
	}

	public String getCliente() {
		return cliente;
	}

	public String getClienteEmail() {
		return clienteEmail;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public Long getFaturaId() {
		return faturaId;
	}

	public StatusPedido getStatus() {
		return status;
	}
}
