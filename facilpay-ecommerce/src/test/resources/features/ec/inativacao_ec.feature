# language: pt
@sprint1
Funcionalidade: inativacao de estabelecimento comercial
  como usuario do back office da facil pay
	desejo realizar a delecao logica de um ec existente na base

  Cenário: remocao de ec
    Dado o seguinte ec existente na base
      | cnpj | estadoIE | inscricaoEstadual | razaoSocial | nomeFantasia | telefone | celular | email | pessoa | cep | logradouro | numero | bairro | cidade | estado | atividadeEconomica | naturezaJuridica | porte | site | ativo |
      | 91.551.093/0001-06 | PA | 15-744524-0 | Isis e Manuel Pães e Doces ME | Pratinha Pães | (91) 2794-9453 | (91) 98557-0670 | producao@isisemanuelpaesedocesme.com.br | Isis | 66816-640 | Alameda Fé em Deus | 105 | Pratinha (Icoaraci) | Belém | PA | 4723-7/00 - Comércio varejista de bebidas | EIRELI | ME | www.isisemanuelpaesedocesme.com.br | true |
    Quando realizo a chamada para delecao
    Então o status do ec passa a ser inativo
    Então sera criado o historico de auditoria correspondente