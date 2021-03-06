/**
 * 
 */
package br.com.facilpay.ecommerce.output.db.adapter;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
import br.com.facilpay.infra.aop.DeveAuditar;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import br.com.facilpay.shared.domain.HistoricoTabelas;
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
	
	public List<HistoricoTabelas> historicosTabela = new ArrayList<>();
	
	@SuppressWarnings("rawtypes")
	private static final Set<Class> BASE_TYPES = new HashSet<>(Arrays.asList(
            String.class, Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));
	
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
	@DeveAuditar
	public EstabelecimentoComercial salvarOuAtualizar(EstabelecimentoComercial estabelecimento) {
		historicosTabela.clear();
		LOG.info("SALVANDO O ESTABELECIMENTO {}", estabelecimento.getRazaoSocial());
		if (estabelecimento.getId() != null) {
			historicosTabela.addAll(this.compararCampos(estabelecimento));
		}
		EstabelecimentoComercialEntity ecEntity = mapper.convertToEntity(estabelecimento);
		return mapper.convertToDto(repository.save(ecEntity));
	}

	@Override
	@DeveAuditar
	public EstabelecimentoComercial trocaStatus(Long id) {
		historicosTabela.clear();
		EstabelecimentoComercial ec = this.buscarPorId(id);
		if (ec.getAtivo()) {
			LOG.info("{} ATUALMENTE ESTÁ ATIVO", ec.getRazaoSocial());
			historicosTabela.add(new HistoricoTabelas("tbl_estabelecimento", "dt_fim_contrato", ec.getId(), null, LocalDateTime.now(), ec.getDataFim().toString(), LocalDateTime.now().toString()));
			ec.setDataFim(LocalDateTime.now());
		} else {
			LOG.info("{} ATUALMENTE ESTÁ INATIVO", ec.getRazaoSocial());
			historicosTabela.add(new HistoricoTabelas("tbl_estabelecimento", "dt_inicio_contrato", ec.getId(), null, LocalDateTime.now(), ec.getDataInicio().toString(),  LocalDateTime.now().toString()));
			ec.setDataInicio(LocalDateTime.now());
		}
		Boolean novoStatus = !ec.getAtivo();
		LOG.info("TROCANDO O STATUS DO ESTABELECIMENTO {} DE {} PARA {}", ec.getRazaoSocial(), ec.getAtivo(), novoStatus);
		historicosTabela.add(new HistoricoTabelas("tbl_estabelecimento", "fl_ativo", ec.getId(), null, LocalDateTime.now(), ec.getAtivo().toString(), novoStatus.toString()));
		ec.setAtivo(novoStatus);
		EstabelecimentoComercialEntity ecEntity = mapper.convertToEntity(ec);
		return mapper.convertToDto(repository.save(ecEntity));
	}

	@Override
	public Page<EstabelecimentoComercial> pesquisar(EstabelecimentoComercialFilter filter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<EstabelecimentoComercialEntity> queryBuilder = criteriaBuilder.createQuery(EstabelecimentoComercialEntity.class);
		Root<EstabelecimentoComercialEntity> rootEntity = queryBuilder.from(EstabelecimentoComercialEntity.class);
		queryBuilder.select(rootEntity).where(criteriaBuilder.or(mapeiaCondicoes(filter, criteriaBuilder, rootEntity)));
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
	
	private List<HistoricoTabelas> compararCampos(EstabelecimentoComercial estabelecimentoComercial) {
		List<HistoricoTabelas> camposAlterados = new ArrayList<>();
		EstabelecimentoComercial antigo = this.buscarPorId(estabelecimentoComercial.getId());
        for (Field field : antigo.getClass().getDeclaredFields()) {
        	LOG.info("VERIFICANDO MUDANÇAS NO CAMPO {}", field.getName());
        	field.setAccessible(true);
        	tryReflection: try {
				Object valueAntigo = field.get(antigo);
				Object valueAtual = field.get(estabelecimentoComercial);
				if (valueAntigo == null && valueAtual == null) {
					break tryReflection;
				}
				LOG.info("O VALOR ANTIGO PARA O CAMPO {} ERA {}", field.getName(), valueAntigo.toString());
				LOG.info("O VALOR ATUAL PARA O CAMPO {} É {}", field.getName(), valueAtual.toString());
				if (isBaseType(valueAntigo.getClass())) {
					if (!Objects.equals(valueAntigo, valueAtual)) {
						LOG.info("O CAMPO {} DO ESTABELECIMENTO {} SOFREU ALTERAÇÃO", field.getName(), estabelecimentoComercial.getId());
						camposAlterados.add(HistoricoTabelas
								.builder()
								.idRegistroAlterado(antigo.getId())
								.idUsuarioAlteracao(null)
								.nomeTabela("tbl_estabelecimento")
								.nomeColuna(field.getName())
								.dataHoraManutencao(LocalDateTime.now())
								.conteudoAnteriorColuna(valueAntigo.toString())
								.conteudoAtualColuna(valueAtual.toString())
								.build());
					}
				}
			} catch (Exception e) {
				LOG.error("ERRO DURANTE A VERIFICAÇÃO DE MUDANÇAS NO REGISTRO VIA REFLECTION, ERRO: {}", e.getClass().getSimpleName());
			}        	
        }		
		return camposAlterados;
	}
	
	private static boolean isBaseType(@SuppressWarnings("rawtypes") Class clazz) {
        return BASE_TYPES.contains(clazz);
    }	

}
