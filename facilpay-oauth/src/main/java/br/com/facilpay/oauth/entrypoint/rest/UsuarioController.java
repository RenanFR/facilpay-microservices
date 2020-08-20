package br.com.facilpay.oauth.entrypoint.rest;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilpay.infra.SwaggerConfig;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(path = { "usuarios" })
@Api(tags = { SwaggerConfig.TAG_USUARIOS })
public class UsuarioController {
	
    @GetMapping
    public Principal getUsuario(Principal usuario) {
    	return usuario;
    }	
	
}
