/**
 * 
 */
package br.com.facilpay.ecommerce.entrypoint.rest;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilpay.ecommerce.domain.port.ManutencaoEstabelecimentoComercialUseCase;
import br.com.facilpay.infra.SwaggerConfig;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import br.com.facilpay.shared.models.FacilPayResponse;
import br.com.facilpay.shared.models.ResponseMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Renan F Rodrigues
 *
 */

@RestController
@RequestMapping(path = { "estabelecimentos" })
@Api(tags = { SwaggerConfig.TAG_ESTABELECIMENTO })
public class EstabelecimentoComercialController {
	
	private static final Logger LOG = LoggerFactory.getLogger(EstabelecimentoComercialController.class);
	
	@Autowired
	private ManutencaoEstabelecimentoComercialUseCase ecUseCase;

	@ApiOperation(value = "RETORNA UM ESTABELECIMENTO ESPECÍFICO ATRAVÉS DO ID", notes = "O ID DO ESTABELECIMENTO GERADO APÓS O CADASTRO PODERÁ SER UTILIZADO PARA OBTER OS DEMAIS DADOS DO EC")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OS DADOS DO ESTABELECIMENTO CASO O MESMO SEJA ENCONTRADO"),
		@ApiResponse(code = 400, message = "ESTABELECIMENTO COM O ID INFORMADO NÃO EXISTE")
	})	
	@GetMapping("/{id}")
	public ResponseEntity<EstabelecimentoComercial> getEstabelecimentoPorId(
			@PathVariable("id") Long id) {
		LOG.info("BUSCANDO O ESTABELECIMENTO PARA O ID {}", id);
		return ResponseEntity.ok(ecUseCase.buscarPorId(id));
	}
	
	@ApiOperation(value = "PERSISTE OS DADOS DE UM ESTABELECIMENTO", notes = "PERMITE CADASTRAR UM NOVO EC NA FÁCIL PAY")
	@PostMapping
	public ResponseEntity<EstabelecimentoComercial> postEstabelecimento(
			@RequestBody @Valid EstabelecimentoComercial estabelecimento) {
		LOG.info("INCLUINDO O NOVO ESTABELECIMENTO {}, DADOS: {}", estabelecimento.getRazaoSocial(), estabelecimento.toString());
		return ResponseEntity.ok(ecUseCase.salvarOuAtualizar(estabelecimento));
	}

	@ApiOperation(value = "ATUALIZAÇÃO CADASTRAL DE UM ESTABELECIMENTO", notes = "ALTERAÇÃO CADASTRAL DE UM EC EXISTENTE")
	@PutMapping
	public ResponseEntity<EstabelecimentoComercial> putEstabelecimento(
			@RequestBody EstabelecimentoComercial estabelecimento) {
		LOG.info("ALTERANDO OS ESTABELECIMENTOS PARA O ESTABELECIMENTO {}, DADOS: {}", estabelecimento.getRazaoSocial(), estabelecimento.toString());
		return ResponseEntity.ok(ecUseCase.salvarOuAtualizar(estabelecimento));
	}

	@ApiOperation(value = "MUDANÇA DE STATUS DE UM ESTABELECIMENTO POR ID", notes = "INATIVAÇÃO (DELEÇÃO LÓGICA) OU ATIVAÇÃO DE UM EC NA BASE DA FÁCIL PAY")
	@DeleteMapping("{id}")
	public ResponseEntity<EstabelecimentoComercial> deleteEstabelecimento(
			@ApiParam(value = "CÓDIGO IDENTIFICADOR DO ESTABELECIMENTO, GERADO PELO BACK OFFICE DA FÁCIL PAY", example = "1") 
			@PathVariable("id") Long id) {
		LOG.info("REALIZANDO A TROCA DE STATUS DO ESTABELECIMENTO COM O ID {}", id);
		return ResponseEntity.ok(ecUseCase.trocaStatus(id));
	}
	
	@ApiOperation(value = "PESQUISA DE ESTABELECIMENTOS", notes = "PERMITE CONSULTAR ESTABELECIMENTOS COM BASE NOS ATRIBUTOS DEFINIDOS EM UM FILTRO")
	@GetMapping
	public ResponseEntity<FacilPayResponse<EstabelecimentoComercial>> getEstabelecimentos(
			@ApiParam(value = "CNPJ", example = "61.244.034/0001-16")
			@RequestParam(required = false) String cnpj, 
			@ApiParam(value = "CPF", example = "664.412.177-24")
			@RequestParam(required = false) String cpf, 
			@ApiParam(value = "INSCRIÇÃO ESTADUAL", example = "128.571.981.111")
			@RequestParam(required = false) String inscricaoEstadual, 
			@ApiParam(value = "RAZÃO SOCIAL", example = "Enzo e Breno Comercio de Bebidas ME")
			@RequestParam(required = false) String razaoSocial, 
			@ApiParam(value = "NOME FANTASIA", example = "Bebidas Enzo e Breno")
			@RequestParam(required = false) String nomeFantasia, 
			@ApiParam(value = "NÚMERO DA PÁGINA DESEJADA", example = "1")
			@RequestParam int page,
			@ApiParam(value = "NÚMERO DE REGISTROS POR PÁGINA", example = "10")
			@RequestParam int size) {
		EstabelecimentoComercialFilter filter = EstabelecimentoComercialFilter
				.builder()
					.cnpj(cnpj)
					.cpf(cpf)
					.inscricaoEstadual(inscricaoEstadual)
					.razaoSocial(razaoSocial)
					.nomeFantasia(nomeFantasia)
				.build();
		LOG.info("REALIZANDO A BUSCA POR ESTABELECIMENTOS USANDO OS SEGUINTES CRITÉRIOS: {}", filter);
		Page<EstabelecimentoComercial> resultadoPesquisa = ecUseCase.pesquisar(filter, PageRequest.of(page, size));
		FacilPayResponse<EstabelecimentoComercial> response = new ResponseMapper<EstabelecimentoComercial>()
				.map(resultadoPesquisa.getContent(), resultadoPesquisa.isFirst(), resultadoPesquisa.isLast(), 
						resultadoPesquisa.getNumberOfElements(), resultadoPesquisa.getTotalElements(), 
						resultadoPesquisa.getNumber(), resultadoPesquisa.getSize(), resultadoPesquisa.getTotalPages());
		return ResponseEntity.ok().body(response);
	}
	
}
