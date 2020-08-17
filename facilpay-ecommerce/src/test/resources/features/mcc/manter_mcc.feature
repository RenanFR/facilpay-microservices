# language: pt
Funcionalidade: manutencao de mcc dos estabelecimentos comerciais
  como usuario do back office da facil pay
	desejo gerir o cadastro de mcc dos estabelecimentos contratantes

  Cenário: inclusao de segmento
    Dado o seguinte segmento
      | nome | descricao |
      | Restaurantes | Restaurante Self Service por Quilo |
    Quando realizo a chamada para inclusao
    Então o segmento estara disponivel para consulta na base
    
  Cenário: consulta de segmentos
    Dado que tenho na base os seguintes segmentos de atuacao
      | mccCode | nome | descricao |
      | 5499 | Bares | Baladas e Eventos |
      | 5511 | Padarias | Lojas de Conveniência, Padarias |
      | 5521 | Pizzarias | Pizzarias |
      | 5422 | Restaurantes | Restaurantes Delivery |
      | 5331 | Academias desportivas | Academias desportivas |
      | 5571 | Cabeleireiro | Cabeleireiro |
      | 5921 | Pet shop | Pet shop |
      | 5972 | Tabacaria | Tabacaria |
      | 7011 | Drogaria | Drogaria |
    Quando realizo uma busca
    Então serei capaz de visualizar os mesmos    