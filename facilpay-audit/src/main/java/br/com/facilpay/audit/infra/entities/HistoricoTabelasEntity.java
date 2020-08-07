package br.com.facilpay.audit.infra.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_historico_tabelas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoTabelasEntity {
	
	@EmbeddedId
	private HistoricoTabelasId id;
	
	@Column(name = "id_usuario")
	private Long idUsuarioAlteracao;
	
	@Column(name = "ds_conteudo_anterior")
	private String conteudoAnteriorColuna;
	
	@Column(name = "ds_conteudo_atual")
	private String conteudoAtualColuna;

}
