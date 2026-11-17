package br.com.hub.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "faturas")
public class FaturaJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String pedidoId;

	@Column(nullable = false)
	private String clienteEmail;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal valor;

	private String codigoBarras;
	private String chaveAcesso;
	private String hashEmail;

	protected FaturaJpaEntity() {
	}

	public FaturaJpaEntity(Long id, String pedidoId, String clienteEmail, BigDecimal valor,
			String codigoBarras, String chaveAcesso, String hashEmail) {
		this.id = id;
		this.pedidoId = pedidoId;
		this.clienteEmail = clienteEmail;
		this.valor = valor;
		this.codigoBarras = codigoBarras;
		this.chaveAcesso = chaveAcesso;
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
