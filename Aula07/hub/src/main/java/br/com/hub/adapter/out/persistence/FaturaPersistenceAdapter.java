package br.com.hub.adapter.out.persistence;

import br.com.hub.application.port.out.FaturaRepositoryPort;
import br.com.hub.domain.Fatura;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FaturaPersistenceAdapter implements FaturaRepositoryPort {

	private final SpringDataFaturaRepository repository;

	public FaturaPersistenceAdapter(SpringDataFaturaRepository repository) {
		this.repository = repository;
	}

	@Override
	public Fatura salvar(Fatura fatura) {
		FaturaJpaEntity salvo = repository.save(toEntity(fatura));
		return toDomain(salvo);
	}

	@Override
	public Optional<Fatura> buscarPorPedidoId(String pedidoId) {
		return repository.findByPedidoId(pedidoId).map(this::toDomain);
	}

	private FaturaJpaEntity toEntity(Fatura fatura) {
		return new FaturaJpaEntity(fatura.getId(), fatura.getPedidoId(), fatura.getClienteEmail(),
				fatura.getValor(), fatura.getCodigoBarras(), fatura.getChaveAcesso(), fatura.getHashEmail());
	}

	private Fatura toDomain(FaturaJpaEntity entity) {
		return new Fatura(entity.getId(), entity.getPedidoId(), entity.getClienteEmail(), entity.getValor(),
				entity.getCodigoBarras(), entity.getChaveAcesso(), entity.getHashEmail());
	}
}
