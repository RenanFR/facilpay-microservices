package br.com.facilpay.shared.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoTabelas {
	
	private String nomeTabela;
	
	private String nomeColuna;
	
	private Long idRegistroAlterado;
	
	private Long idUsuarioAlteracao;
	
	private LocalDateTime dataHoraManutencao;
	
	private String conteudoAnteriorColuna;
	
	private String conteudoAtualColuna;	

}
