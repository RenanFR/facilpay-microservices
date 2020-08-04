# language: pt
Funcionalidade: manutencao de estabelecimentos comerciais
  como usuario do back office da facil pay
	desejo realizar as operações crud referente aos ecs na base da facil pay

  Contexto:
  	Dado um novo ec
      | estadoIE | inscricaoEstadual | razaoSocial | nomeFantasia | telefone | celular | email | pessoa | cep | logradouro | numero | bairro | cidade | estado | atividadeEconomica | naturezaJuridica | porte | site | ativo
      | SP | 128.571.981.111 | Enzo e Breno Comercio de Bebidas ME | Bebidas Enzo e Breno | (11) 2967-5644 | (11) 99236-9007 | manutencao@enzoebrenocomerciodebebidasme.com.br | Breno | 02652-090 | Rua Caravela Princesa | 985 | Jardim Peri | São Paulo | SP | 4723-7/00 - Comércio varejista de bebidas | EIRELI | ME | www.enzoebrenocomerciodebebidasme.com.br | true  	
      
	Esquema do Cenário: deve considerar as regras de validacao de estabelecimento
		Quando adiciono um ec com o cnpj "<cnpj>"
		Então meu retorno sera <retorno>
		
	Exemplos:
	   | cnpj | retorno | 
	   | 61.244.034/0001-16 | 200 |
	   | CNPJ inválido teste | 400 |

  Cenário: remocao de ec
    Dado o seguinte ec existente na base
      | cnpj | estadoIE | inscricaoEstadual | razaoSocial | nomeFantasia | telefone | celular | email | pessoa | cep | logradouro | numero | bairro | cidade | estado | atividadeEconomica | naturezaJuridica | porte | site | ativo
      | 91.551.093/0001-06 | PA | 15-744524-0 | Isis e Manuel Pães e Doces ME | Pratinha Pães | (91) 2794-9453 | (91) 98557-0670 | producao@isisemanuelpaesedocesme.com.br | Isis | 66816-640 | Alameda Fé em Deus | 105 | Pratinha (Icoaraci) | Belém | PA | 4723-7/00 - Comércio varejista de bebidas | EIRELI | ME | www.isisemanuelpaesedocesme.com.br | true
    Quando realizo a chamada para delecao
    Então o status do ec passa a ser inativo
    
  Esquema do Cenário: consulta de ec	   
    Dado que desejo encontrar um ou mais ecs por meio dos atributos "<cnpj>", "<cpf>", "<inscricaoEstadual>", "<razaoSocial>" e ou "<nomeFantasia>"
    Dado que tenho alguns registro de teste
    Quando realizo a chamada para pesquisa
    Então o numero de resultados devera ser <numResultados>
    
	Exemplos:
	   | cnpj | cpf | inscricaoEstadual | razaoSocial | nomeFantasia | numResultados |
	   | 15.304.663/0001-77 ||||| 2 |
