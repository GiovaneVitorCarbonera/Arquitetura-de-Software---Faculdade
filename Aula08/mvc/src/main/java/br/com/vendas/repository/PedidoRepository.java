package br.com.vendas.repository;

import br.com.vendas.model.PedidoVenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoVenda, Long> {
}
