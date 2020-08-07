/**
 * 
 */
package br.com.facilpay.ecommerce.output.db.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.facilpay.ecommerce.entrypoint.rest.EstabelecimentoComercialFilter;
import br.com.facilpay.ecommerce.infra.entities.EstabelecimentoComercialEntity;
import br.com.facilpay.ecommerce.infra.mapper.EstabelecimentoComercialMapper;
import br.com.facilpay.ecommerce.output.db.port.EstabelecimentoComercialRepository;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import br.com.facilpay.shared.exception.EntidadeNaoEncontradaException;
import br.com.facilpay.shared.exception.EntidadeNaoEncontradaException.TipoEntidade;

/**
 * @author rnfr
 *
 */

@Repository
public class EstabelecimentoComercialRepositoryAdapter implements EstabelecimentoComercialRepository {
	
	private static final Logger LOG = LoggerFactory.getLogger(EstabelecimentoComercialRepositoryAdapter.class);
	
	@Autowired
	private SpringJPAEstabelecimentoComercialRepository repository;
	
	@Autowired
	private EstabelecimentoComercialMapper mapper;	
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	@Override
	public Page<EstabelecimentoComercial> buscarTodos(Pageable pageRequest) {
		List<EstabelecimentoComercialEntity> results = repository.findAll(pageRequest).toList();
		return new PageImpl<EstabelecimentoComercial>(mapper.convertAllToDto(results), pageRequest, results.size());
	}

	@Override
	public EstabelecimentoComercial buscarPorId(Long id) {
		return mapper.convertToDto(repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(TipoEntidade.ESTABELECIMENTO, id.toString())));
	}

	@Override
	public EstabelecimentoComercial salvarOuAtualizar(EstabelecimentoComercial estabelecimento) {
		LOG.info("SALVANDO O ESTABELECIMENTO {}", estabelecimento.getRazaoSocial());
		EstabelecimentoComercialEntity ecEntity = mapper.convertToEntity(estabelecimento);
		return mapper.convertToDto(repository.save(ecEntity));
	}

	@Override
	public EstabelecimentoComercial removePorId(Long id) {
		EstabelecimentoComercial ec = this.buscarPorId(id);
		ec.setAtivo(false);
		return this.salvarOuAtualizar(ec);
	}

	@Override
	public Page<EstabelecimentoComercial> pesquisar(EstabelecimentoComercialFilter filter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<EstabelecimentoComercialEntity> queryBuilder = criteriaBuilder.createQuery(EstabelecimentoComercialEntity.class);
		Root<EstabelecimentoComercialEntity> rootEntity = queryBuilder.from(EstabelecimentoComercialEntity.class);
		queryBuilder.select(rootEntity).where(mapeiaCondicoes(filter, criteriaBuilder, rootEntity));
		TypedQuery<EstabelecimentoComercialEntity> query = entityManager.createQuery(queryBuilder);
		adicionaRestricoesPaginacao(query, pageable);
		List<EstabelecimentoComercialEntity> results = query.getResultList();
		return new PageImpl<EstabelecimentoComercial>(mapper.convertAllToDto(results), pageable, contagemTotal(filter));
	}
	
	private Predicate[] mapeiaCondicoes(EstabelecimentoComercialFilter filter, CriteriaBuilder criteriaBuilder, Root<EstabelecimentoComercialEntity> rootEntity) {
		List<Predicate> restricoes = new ArrayList<>();
		if (!StringUtils.isEmpty(filter.getCnpj())) {
			restricoes.add(criteriaBuilder.like(criteriaBuilder.lower(rootEntity.get("cnpj")), "%" + filter.getCnpj().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(filter.getCpf())) {
			restricoes.add(criteriaBuilder.like(criteriaBuilder.lower(rootEntity.get("cpf")), "%" + filter.getCpf().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(filter.getInscricaoEstadual())) {
			restricoes.add(criteriaBuilder.like(criteriaBuilder.lower(rootEntity.get("inscricaoEstadual")), "%" + filter.getInscricaoEstadual().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(filter.getRazaoSocial())) {
			restricoes.add(criteriaBuilder.like(criteriaBuilder.lower(rootEntity.get("razaoSocial")), "%" + filter.getRazaoSocial().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(filter.getNomeFantasia())) {
			restricoes.add(criteriaBuilder.like(criteriaBuilder.lower(rootEntity.get("nomeFantasia")), "%" + filter.getNomeFantasia().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(filter.getNumeroContrato())) {
			restricoes.add(criteriaBuilder.like(criteriaBuilder.lower(rootEntity.get("numeroContrato")), "%" + filter.getNumeroContrato().toLowerCase() + "%"));
		}
		return restricoes.toArray(new Predicate[restricoes.size()]);
	}
	
	private void adicionaRestricoesPaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalRegistrosPorPagina;
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long contagemTotal(EstabelecimentoComercialFilter filter) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> queryBuilder = criteriaBuilder.createQuery(Long.class);
		Root<EstabelecimentoComercialEntity> rootEntity = queryBuilder.from(EstabelecimentoComercialEntity.class);
		queryBuilder.where(mapeiaCondicoes(filter, criteriaBuilder, rootEntity));
		queryBuilder.select(criteriaBuilder.count(rootEntity));
		return entityManager
				.createQuery(queryBuilder)
				.getSingleResult();
	}

}
