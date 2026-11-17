package br.com.mvc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoTest {

	@Test
	void estoqueDeterminaDisponibilidadeEProdutoDescontinuadoNaoPodeSerMovimentado() {
		Produto produto = new Produto("Caderno");
		assertEquals(StatusProduto.INDISPONIVEL, produto.getStatus());

		produto.registrarEntrada(4);
		assertEquals(StatusProduto.DISPONIVEL, produto.getStatus());
		produto.registrarSaida(4);
		assertEquals(StatusProduto.INDISPONIVEL, produto.getStatus());

		produto.descontinuar();
		assertEquals(StatusProduto.DESCONTINUADO, produto.getStatus());
		assertThrows(IllegalStateException.class, () -> produto.registrarEntrada(1));
		assertThrows(IllegalStateException.class, () -> produto.registrarSaida(1));
	}

	@Test
	void naoPermiteSaidaMaiorQueEstoque() {
		Produto produto = new Produto("Caneta");
		produto.registrarEntrada(2);

		assertThrows(IllegalArgumentException.class, () -> produto.registrarSaida(3));
		assertEquals(2, produto.getEstoque());
	}
}
