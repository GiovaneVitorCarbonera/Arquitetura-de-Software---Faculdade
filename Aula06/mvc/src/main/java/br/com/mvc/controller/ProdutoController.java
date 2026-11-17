package br.com.mvc.controller;

import br.com.mvc.model.Produto;
import br.com.mvc.service.ProdutoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService service;

	public ProdutoController(ProdutoService service) {
		this.service = service;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("produtos", service.listar());
		return "produtos";
	}

	@PostMapping
	public String criar(@RequestParam String nome, RedirectAttributes redirect) {
		return executar(() -> service.criar(nome), redirect);
	}

	@PostMapping("/{id}/entrada")
	public String entrada(@PathVariable Long id, @RequestParam int quantidade, RedirectAttributes redirect) {
		return executar(() -> service.registrarEntrada(id, quantidade), redirect);
	}

	@PostMapping("/{id}/saida")
	public String saida(@PathVariable Long id, @RequestParam int quantidade, RedirectAttributes redirect) {
		return executar(() -> service.registrarSaida(id, quantidade), redirect);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{id}/descontinuar")
	public String descontinuar(@PathVariable Long id, RedirectAttributes redirect) {
		return executar(() -> service.descontinuar(id), redirect);
	}

	@FunctionalInterface
	private interface OperacaoProduto {
		Produto executar();
	}

	private String executar(OperacaoProduto operacao, RedirectAttributes redirect) {
		try {
			operacao.executar();
			redirect.addFlashAttribute("mensagem", "Operação realizada com sucesso.");
		} catch (IllegalArgumentException | IllegalStateException exception) {
			redirect.addFlashAttribute("erro", exception.getMessage());
		}
		return "redirect:/produtos";
	}
}
