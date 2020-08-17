# language: pt
Funcionalidade: manutencao dos servicos prestados pela facil pay aos ecs contratantes
  como usuario do back office da facil pay
	desejo gerir o cadastro de servicos prestados pela facil pay

  Cenário: inclusao de servico
    Dado o seguinte servico
      | nome | descricao |
      | Análise de risco | Análise de risco |
    Quando realizo a chamada para inclusao
    Então o servico estara disponivel para consulta na base
    
  Cenário: consulta de servicos
    Dado que tenho na base os seguintes servicos
      | nome | descricao |
      | 3DS 2.0 | 3DS 2.0 |
      | Débito Online | Débito Online |
      | Pagamento Por Link | Pagamento Por Link |
    Quando realizo uma busca
    Então serei capaz de visualizar os mesmos    