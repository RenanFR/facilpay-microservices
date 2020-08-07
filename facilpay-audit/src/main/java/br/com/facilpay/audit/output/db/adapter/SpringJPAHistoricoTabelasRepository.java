package br.com.facilpay.audit.output.db.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.facilpay.audit.infra.entities.HistoricoTabelasEntity;
import br.com.facilpay.audit.infra.entities.HistoricoTabelasId;

public interface SpringJPAHistoricoTabelasRepository extends JpaRepository<HistoricoTabelasEntity, HistoricoTabelasId> {

}
