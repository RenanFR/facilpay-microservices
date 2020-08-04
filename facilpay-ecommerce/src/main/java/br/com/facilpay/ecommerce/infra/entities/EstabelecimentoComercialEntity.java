/**
 * 
 */
package br.com.facilpay.ecommerce.infra.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.facilpay.shared.entities.ContatoEntity;
import br.com.facilpay.shared.entities.EnderecoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Entity
@Table(name = "tbl_estabelecimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoComercialEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_loja")
	private Long id;
	
	@Column(name = "cd_cnpj")
	private String cnpj;
	
	@Column(name = "cd_cpf")
	private String cpf;
	
	@Column(name = "cd_ie")
	private String inscricaoEstadual;
	
	@Column(name = "cd_im")
	private String inscricaoMunicipal;
	
	@Column(name = "nm_razao_social")
	private String razaoSocial;
	
	@Column(name = "nm_fantasia")
	private String nomeFantasia;
	
	@Column(name = "dt_inicio_contrato")
	private LocalDateTime dataInicio;
	
	@Column(name = "dt_fim_contrato")
	private LocalDateTime dataFim;
	
	@Embedded
	private ContatoEntity contato;
	
	@Embedded
	@AttributeOverrides({
		  @AttributeOverride( name = "cd_cep", column = @Column(name = "cd_cep_loja")),
		  @AttributeOverride( name = "ds_logradouro", column = @Column(name = "ds_logradouro_loja")),
		  @AttributeOverride( name = "nu_endereco", column = @Column(name = "nu_endereco_loja")),
		  @AttributeOverride( name = "nm_bairro", column = @Column(name = "nm_bairro_loja")),
		  @AttributeOverride( name = "nm_cidade", column = @Column(name = "nm_cidade_loja")),
		  @AttributeOverride( name = "nm_uf", column = @Column(name = "nm_uf_loja"))
	})
	private EnderecoEntity endereco;
	
	@ManyToOne
	@JoinColumn(name  = "id_segmento", foreignKey = @ForeignKey(name = "fk_estabelecimento_segmento"))
	private SegmentoEstabelecimentoEntity segmento;
	
	@Column(name = "ds_atv_economica")
	private String atividadeEconomica;
	
	@Column(name = "ds_natureza_juridica")
	private String naturezaJuridica;
	
	@Column(name = "nm_porte")
	private String porte;
	
	@Column(name = "nu_contrato")
	private String numeroContrato;
	
	@Column(name = "fl_ativo")
	private Boolean ativo;
	
	@ManyToMany
	@JoinTable(
			  name = "tbl_servico_estabelecimento", 
			  joinColumns = @JoinColumn(name = "id_loja"), 
			  inverseJoinColumns = @JoinColumn(name = "id_servico")
			)		
	private List<ServicoFacilPayEntity> servicosContratados = new ArrayList<>();
	
}
