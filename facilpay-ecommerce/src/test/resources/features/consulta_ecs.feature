# language: pt
Funcionalidade: consulta de estabelecimentos comerciais
  como usuario do back office da facil pay
	desejo realizar a busca de um ou mais estabelecimentos de acordo com criterios

  Esquema do Cenário: consulta de ec	   
    Dado que desejo encontrar um ou mais ecs por meio dos atributos "<cnpj>", "<cpf>", "<inscricaoEstadual>", "<razaoSocial>" e ou "<nomeFantasia>"
    Dado que tenho alguns registro de teste
    Quando realizo a chamada para pesquisa
    Então o numero de resultados devera ser <numResultados>
	Exemplos:
	   | cnpj | cpf | inscricaoEstadual | razaoSocial | nomeFantasia | numResultados |
	   | 15.304.663/0001-77 ||||| 1 | 