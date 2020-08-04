/**
 * 
 */
package br.com.facilpay.payment.infra.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.facilpay.payment.domain.CartaoParcela;
import br.com.facilpay.payment.domain.Transacao;
import br.com.facilpay.payment.infra.entities.TransacaoEntity;

/**
 * @author rnfr
 *
 */

@Component
public class TransacaoMapper {
	
    @Autowired
    private ModelMapper modelMapper;
    
    public Transacao convertToDto(TransacaoEntity entity) {
    	Transacao transacao = modelMapper.map(entity, Transacao.class);
		transacao.setParcelas(modelMapper.map(entity.getParcelas(), new TypeToken<List<CartaoParcela>>() {}.getType()));
	    return transacao;
	} 
    
    public TransacaoEntity convertToEntity(Transacao dto) {
    	TransacaoEntity transacao = modelMapper.map(dto, TransacaoEntity.class);
    	transacao.setParcelas(modelMapper.map(dto.getParcelas(), new TypeToken<List<CartaoParcela>>() {}.getType()));    	
	    return transacao;
	}     
    
    public List<Transacao> convertAllToDto(List<TransacaoEntity> entities) {
	    return entities.stream().map(ec -> this.convertToDto(ec)).collect(Collectors.toList());
	}   
    
    public List<TransacaoEntity> convertAllToEntity(List<Transacao> dtos) {
	    return dtos.stream().map(ec -> this.convertToEntity(ec)).collect(Collectors.toList());
	}	

}
