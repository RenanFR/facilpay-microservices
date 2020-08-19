package br.com.facilpay.oauth.entrypoint.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilpay.infra.SwaggerConfig;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(path = { "usuarios" })
@Api(tags = { SwaggerConfig.TAG_USUARIOS })
public class UsuarioController {
	
}
