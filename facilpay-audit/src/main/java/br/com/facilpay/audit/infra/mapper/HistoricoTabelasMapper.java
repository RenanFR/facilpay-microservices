package br.com.facilpay.audit.infra.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.facilpay.audit.infra.entities.HistoricoTabelasEntity;
import br.com.facilpay.shared.domain.HistoricoTabelas;

@Component
public class HistoricoTabelasMapper {
	
    @Autowired
    private ModelMapper modelMapper;
    
    public HistoricoTabelas convertToDto(HistoricoTabelasEntity entity) {
	    return modelMapper.map(entity, HistoricoTabelas.class);
	} 
    
    public HistoricoTabelasEntity convertToEntity(HistoricoTabelas dto) {
    	return modelMapper.map(dto, HistoricoTabelasEntity.class);
	}    

}
