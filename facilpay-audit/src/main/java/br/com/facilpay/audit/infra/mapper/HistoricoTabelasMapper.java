package br.com.facilpay.audit.infra.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.facilpay.audit.infra.entities.HistoricoTabelasEntity;
import br.com.facilpay.audit.infra.entities.HistoricoTabelasId;
import br.com.facilpay.shared.domain.HistoricoTabelas;

@Component
public class HistoricoTabelasMapper {
	
    @Autowired
    private ModelMapper modelMapper;
    
    public HistoricoTabelas convertToDto(HistoricoTabelasEntity entity) {
	    HistoricoTabelas dto = modelMapper.map(entity, HistoricoTabelas.class);
		return dto;
	} 
    
    public HistoricoTabelasEntity convertToEntity(HistoricoTabelas dto) {
    	HistoricoTabelasId id = new HistoricoTabelasId(dto.getNomeTabela(), dto.getNomeColuna(), dto.getIdRegistroAlterado(), dto.getDataHoraManutencao());
    	HistoricoTabelasEntity entity = modelMapper.map(dto, HistoricoTabelasEntity.class);
    	entity.setId(id);
		return entity;
	}    
    
    public List<HistoricoTabelasEntity> convertAllToEntity(List<HistoricoTabelas> dtos) {
	    return dtos.stream().map(historico -> this.convertToEntity(historico)).collect(Collectors.toList());
	}      
    
    public List<HistoricoTabelas> convertAllToDto(List<HistoricoTabelasEntity> entities) {
	    return entities.stream().map(ec -> this.convertToDto(ec)).collect(Collectors.toList());
	}      

}
