package br.com.mvc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private int estoque;

	@Enumerated(EnumType.STRING)
	private StatusProduto status = StatusProduto.INDISPONIVEL;

	protected Produto() {
	}

	public Produto(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new IllegalArgumentException("O nome do produto é obrigatório.");
		}
		this.nome = nome.trim();
	}

	public void registrarEntrada(int quantidade) {
		validarOperacao(quantidade);
		estoque += quantidade;
		atualizarStatusPeloEstoque();
	}

	public void registrarSaida(int quantidade) {
		validarOperacao(quantidade);
		if (quantidade > estoque) {
			throw new IllegalArgumentException("Estoque insuficiente para essa saída.");
		}
		estoque -= quantidade;
		atualizarStatusPeloEstoque();
	}

	public void descontinuar() {
		if (status == StatusProduto.DESCONTINUADO) {
			throw new IllegalStateException("O produto já está descontinuado.");
		}
		status = StatusProduto.DESCONTINUADO;
	}

	private void validarOperacao(int quantidade) {
		if (status == StatusProduto.DESCONTINUADO) {
			throw new IllegalStateException("Produto descontinuado não pode ser movimentado.");
		}
		if (quantidade <= 0) {
			throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
		}
	}

	private void atualizarStatusPeloEstoque() {
		status = estoque == 0 ? StatusProduto.INDISPONIVEL : StatusProduto.DISPONIVEL;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getEstoque() {
		return estoque;
	}

	public StatusProduto getStatus() {
		return status;
	}
}
