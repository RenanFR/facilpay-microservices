/**
 * 
 */
package br.com.facilpay.ecommerce.infra.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.facilpay.ecommerce.domain.EstabelecimentoComercial;
import br.com.facilpay.ecommerce.domain.SegmentoEstabelecimento;
import br.com.facilpay.ecommerce.domain.ServicoFacilPay;
import br.com.facilpay.ecommerce.infra.entities.EstabelecimentoComercialEntity;
import br.com.facilpay.ecommerce.infra.entities.SegmentoEstabelecimentoEntity;
import br.com.facilpay.ecommerce.infra.entities.ServicoFacilPayEntity;
import br.com.facilpay.shared.entities.ContatoEntity;
import br.com.facilpay.shared.entities.EnderecoEntity;
import br.com.facilpay.shared.models.Contato;
import br.com.facilpay.shared.models.Endereco;

/**
 * @author rnfr
 *
 */

@Component
public class EstabelecimentoComercialMapper {
	
    @Autowired
    private ModelMapper modelMapper;
    
    public EstabelecimentoComercial convertToDto(EstabelecimentoComercialEntity entity) {
		EstabelecimentoComercial estabelecimentoComercial = modelMapper.map(entity, EstabelecimentoComercial.class);
		Optional.ofNullable(entity.getContato()).ifPresent((contato) -> { 
			estabelecimentoComercial.setContato(modelMapper.map(contato, Contato.class));
		}); 
		Optional.ofNullable(entity.getEndereco()).ifPresent((endereco) -> { 
			estabelecimentoComercial.setEndereco(modelMapper.map(endereco, Endereco.class));
		}); 
		Optional.ofNullable(entity.getSegmento()).ifPresent((segmento) -> { 
			estabelecimentoComercial.setSegmento(modelMapper.map(segmento, SegmentoEstabelecimento.class));
		}); 
		Optional.ofNullable(entity.getServicosContratados()).ifPresent((servicos) -> { 
			estabelecimentoComercial.setServicosContratados(modelMapper.map(servicos, new TypeToken<List<ServicoFacilPay>>() {}.getType()));
		}); 
	    return estabelecimentoComercial;
	} 
    
    public EstabelecimentoComercialEntity convertToEntity(EstabelecimentoComercial dto) {
    	EstabelecimentoComercialEntity estabelecimentoComercial = modelMapper.map(dto, EstabelecimentoComercialEntity.class);
		Optional.ofNullable(dto.getEndereco()).ifPresent((endereco) -> { 
			estabelecimentoComercial.setEndereco(modelMapper.map(endereco, EnderecoEntity.class));
		}); 		
		Optional.ofNullable(dto.getContato()).ifPresent((contato) -> { 
			estabelecimentoComercial.setContato(modelMapper.map(contato, ContatoEntity.class));
		}); 		
		Optional.ofNullable(dto.getSegmento()).ifPresent((segmento) -> { 
			estabelecimentoComercial.setSegmento(convertSegmentoToEntity(segmento));
		}); 		
		Optional.ofNullable(dto.getServicosContratados()).ifPresent((servicos) -> { 
			estabelecimentoComercial.setServicosContratados(modelMapper.map(servicos, new TypeToken<List<ServicoFacilPayEntity>>() {}.getType()));    	
		}); 		
	    return estabelecimentoComercial;
	}
    
    public SegmentoEstabelecimentoEntity convertSegmentoToEntity(SegmentoEstabelecimento segmentoDTO) {
    	return modelMapper.map(segmentoDTO, SegmentoEstabelecimentoEntity.class);
    }     
    
    public List<EstabelecimentoComercial> convertAllToDto(List<EstabelecimentoComercialEntity> entities) {
	    return entities.stream().map(ec -> this.convertToDto(ec)).collect(Collectors.toList());
	}   
    
    public List<EstabelecimentoComercialEntity> convertAllToEntity(List<EstabelecimentoComercial> dtos) {
	    return dtos.stream().map(ec -> this.convertToEntity(ec)).collect(Collectors.toList());
	}     
    
}
