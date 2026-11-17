package br.com.vendas.controller;

import br.com.vendas.service.PedidoVendaService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@Validated
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoVendaService service;

	public PedidoController(PedidoVendaService service) {
		this.service = service;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("pedidos", service.listar());
		return "pedidos";
	}

	@PostMapping
	public String fechar(
			@RequestParam @NotBlank String cliente,
			@RequestParam @NotBlank @Email String email,
			@RequestParam @NotNull @Positive BigDecimal valor,
			RedirectAttributes redirect) {
		var resultado = service.fechar(cliente, email, valor);
		redirect.addFlashAttribute("mensagem", resultado.mensagem());
		return "redirect:/pedidos";
	}

	@PostMapping("/{id}/faturar")
	public String faturar(@PathVariable Long id, RedirectAttributes redirect) {
		var resultado = service.faturar(id);
		redirect.addFlashAttribute("mensagem", resultado.mensagem());
		return "redirect:/pedidos";
	}
}
