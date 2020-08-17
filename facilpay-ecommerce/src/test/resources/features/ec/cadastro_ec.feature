# language: pt
@sprint1
Funcionalidade: cadastro de estabelecimento comercial
  como usuario do back office da facil pay
	desejo incluir um novo ec contratante

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