package br.com.facilpay.oauth.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name = "tbl_usuario")
public class UsuarioEntity implements UserDetails, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7448726924997864884L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;
	
	@Column(name = "nm_login")
	private String login;
	
	@Column(name = "ds_senha")
	private String senha;
	
	@ManyToMany
	@JoinTable(
			  name = "tbl_perfil_usuario", 
			  joinColumns = @JoinColumn(name = "id_usuario"), 
			  inverseJoinColumns = @JoinColumn(name = "id_perfil")
			)
	private List<PerfilEntity> perfis;

	@Column(name = "fl_ativo")
	private boolean ativo;

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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getPerfis();
	}

	@Override
	public String getPassword() {
		return this.getSenha();
	}

	@Override
	public String getUsername() {
		return this.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isAtivo();
	}	

}
