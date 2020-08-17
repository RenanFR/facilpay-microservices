/**
 * 
 */
package br.com.facilpay.shared.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.caelum.stella.bean.validation.CNPJ;
import br.com.caelum.stella.bean.validation.CPF;
import br.com.facilpay.shared.models.Contato;
import br.com.facilpay.shared.models.Endereco;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author rnfr
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(
	    value = "Estabelecimento comercial (EC)",
	    description = "DADOS DO ESTABELECIMENTO CUJO REGISTRO NA BASE SERÁ ATUALIZADO/INSERIDO"
)
@ToString(exclude = { "mcc", "servicosContratados" })
@Builder
public class EstabelecimentoComercial {
	
	@ApiModelProperty(
			value = "CÓDIGO IDENTIFICADOR DO ESTABELECIMENTO, GERADO PELO BACK OFFICE DA FÁCIL PAY",
			example = "1")		
	private Long id;
	
	@ApiModelProperty(
			value = "O CNPJ DO ESTABELECIMENTO",
			example = "61.244.034/0001-16")
	@CNPJ
	private String cnpj;
	
	@ApiModelProperty(
			value = "CPF DO RESPONSÁVEL PELO ESTABELECIMENTO NO CASO DE PESSOA FÍSICA",
			example = "664.412.177-24")
	@CPF
	private String cpf;
	
	@ApiModelProperty(
			value = "REGISTRO DO CONTRIBUINTE NO CADASTRO DO ICMS MANTIDO PELA RECEITA ESTADUAL",
			example = "128.571.981.111")		
	private String inscricaoEstadual;
	
	@ApiModelProperty(
			value = "ESTADO PARA O QUAL O EC RECOLHE O ICMS, DEVE CORRESPONDER A INSCRIÇÃO ESTADUAL",
			example = "SP")		
	private String estadoIE;
	
	@ApiModelProperty(
			value = "NÚMERO DE IDENTIFICAÇÃO DO CONTRIBUINTE NO CADASTRO TRIBUTÁRIO MUNICIPAL",
			example = "000.000.006-00")		
	private String inscricaoMunicipal;
	
	@ApiModelProperty(
			value = "NOME DEVIDAMENTE REGISTRADO SOB O QUAL UMA PESSOA JURÍDICA SE INDIVIDUALIZA E EXERCE SUAS ATIVIDADES",
			example = "Enzo e Breno Comercio de Bebidas ME")		
	private String razaoSocial;
	
	@ApiModelProperty(
			value = "DESIGNAÇÃO POPULAR DE TÍTULO DE ESTABELECIMENTO UTILIZADA POR UMA INSTITUIÇÃO",
			example = "Bebidas Enzo e Breno")	
	@NotNull
	@NotEmpty
	private String nomeFantasia;
	
	@ApiModelProperty(
			value = "DATA DE INÍCIO DA VIGÊNCIA DO CADASTRO DO EC JUNTO A FÁCIL PAY",
			example = "28/07/2020")	
	private LocalDateTime dataInicio;
	
	@ApiModelProperty(
			value = "DATA DE FIM DA VIGÊNCIA DO CADASTRO DO EC JUNTO A FÁCIL PAY",
			example = "31/12/2020")		
	private LocalDateTime dataFim;
	
	private Contato contato;
	
	private Endereco endereco;
	
	private MCC mcc;
	
	@ApiModelProperty(
			value = "ATIVIDADES QUE A EMPRESA ESTÁ AUTORIZADA A EXECUTAR – CNAEs",
			example = "4723-7/00 - Comércio varejista de bebidas")		
	private String atividadeEconomica;
	
	@ApiModelProperty(
			value = "MODELO DE NEGÓCIO DEFINIDO NA FORMALIZAÇÃO (MEI, EI, EIRELI, SA E LTDA)",
			example = "EIRELI")		
	private String naturezaJuridica;

	@ApiModelProperty(
			value = "TIPO DE EMPRESA DE ACORDO COM O PORTE (ME, EPP, MÉDIO PORTE E GRANDE PORTE)",
			example = "ME")	
	private String porte;
	
	@ApiModelProperty(
			value = "NÚMERO DO CONTRATO DE PRESTAÇÃO DE SERVIÇO DO EC JUNTO A FÁCIL PAY",
			example = "1")	
	private String numeroContrato;
	
	@ApiModelProperty(
			value = "ENDEREÇO WEB DO SITE POR ONDE O ESTABELECIMENTO REALIZA SUAS VENDAS NA MODALIDADE E-COMMERCE",
			example = "www.enzoebrenocomerciodebebidasme.com.br")		
	private String site;
	
	@ApiModelProperty(
			value = "INDICA SE O CADASTRO DO EC JUNTO A FÁCIL PAY ESTÁ ATIVO OU INATIVO",
			example = "true")		
	private Boolean ativo;
	
	private List<ServicoFacilPay> servicosContratados;	
	
	public static List<EstabelecimentoComercial> getEstabelecimentosTeste() {
		EstabelecimentoComercial ianNoahEsportes = EstabelecimentoComercial
				.builder()
				.cnpj("15.304.663/0001-77")
				.inscricaoEstadual("790.392.726.288")
				.razaoSocial("Ian e Noah Esportes ME")
				.nomeFantasia("IN Esportes")
				.build();
		EstabelecimentoComercial pizzariaAlianca = EstabelecimentoComercial
				.builder()
				.cpf("426.922.240-94")
				.inscricaoEstadual("12910684-4")
				.razaoSocial("Daiane e Carlos Eduardo Pizzaria Delivery Ltda")
				.nomeFantasia("Pizzaria Aliança")
				.build();
		EstabelecimentoComercial trincheirasSalgados = EstabelecimentoComercial
				.builder()
				.cnpj("01.003.891/0001-00")
				.inscricaoEstadual("09026767-2")
				.razaoSocial("Elisa e Daiane Doces & Salgados ME")
				.nomeFantasia("Trincheiras Salgados")
				.build();
		EstabelecimentoComercial bebidasTresIrmas = EstabelecimentoComercial
				.builder()
				.cpf("820.247.350-02")
				.inscricaoEstadual("70526524-2")
				.razaoSocial("Regina e Felipe Comercio de Bebidas ME")
				.nomeFantasia("Bebidas Três Irmãs")
				.build();
		EstabelecimentoComercial joalheriaPeruchi = EstabelecimentoComercial
				.builder()
				.cnpj("71.703.604/0001-42")
				.inscricaoEstadual("11656440-7")
				.razaoSocial("Gabriel e Victor Joalheria ME")
				.nomeFantasia("Joalheria Peruchi")
				.build();
		return List.of(ianNoahEsportes, pizzariaAlianca, trincheirasSalgados, bebidasTresIrmas, joalheriaPeruchi);
				
	}
	
}
