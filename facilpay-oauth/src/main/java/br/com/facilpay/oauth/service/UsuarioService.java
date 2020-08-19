package br.com.facilpay.oauth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.facilpay.oauth.entities.UsuarioEntity;
import br.com.facilpay.oauth.repository.UsuarioRepository;
import br.com.facilpay.shared.exception.EntidadeNaoEncontradaException;
import br.com.facilpay.shared.exception.EntidadeNaoEncontradaException.TipoEntidade;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsuarioEntity> usuario = repository.findByLogin(username);
		if(usuario.isPresent()) {
			return usuario.get();
		}
		throw new EntidadeNaoEncontradaException(TipoEntidade.USUARIOS);
	}

}
