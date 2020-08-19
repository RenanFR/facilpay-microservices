package br.com.facilpay.oauth.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_perfil")
public class PerfilEntity implements GrantedAuthority, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7353919756868821395L;

	@Id
	@Column(name = "nm_perfil")	
	private String nome;
	
	@Column(name = "dt_criacao")
	private LocalDate dataCriacao;
	
	@Column(name = "dt_manutencao")
	private LocalDate dataManutencao;
	
	@PostPersist
	public void persistDataCriacao() {
		this.dataCriacao = LocalDate.now();
	}
	
	@PostUpdate
	public void updateDataCriacao() {
		this.dataManutencao = LocalDate.now();
	}
	
	@Override
	public String getAuthority() {
		return this.getNome();
	}	

}
