package br.com.facilpay.audit.infra.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HistoricoTabelasId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "nm_tabela")
	private String nomeTabela;
	
	@Column(name = "nm_coluna")
	private String nomeColuna;
	
	@Column(name = "id_registro")
	private Long idRegistroAlterado;
	
	@Column(name = "dt_manutencao")
	private LocalDateTime dataHoraManutencao;	

}
