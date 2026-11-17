package br.com.mvc.service;

import br.com.mvc.model.Produto;
import br.com.mvc.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

	private final ProdutoRepository repository;

	public ProdutoService(ProdutoRepository repository) {
		this.repository = repository;
	}

	public List<Produto> listar() {
		return repository.findAll();
	}

	public Produto criar(String nome) {
		return repository.save(new Produto(nome));
	}

	@Transactional
	public Produto registrarEntrada(Long id, int quantidade) {
		Produto produto = buscar(id);
		produto.registrarEntrada(quantidade);
		return produto;
	}

	@Transactional
	public Produto registrarSaida(Long id, int quantidade) {
		Produto produto = buscar(id);
		produto.registrarSaida(quantidade);
		return produto;
	}

	@Transactional
	public Produto descontinuar(Long id) {
		Produto produto = buscar(id);
		produto.descontinuar();
		return produto;
	}

	private Produto buscar(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
	}
}
