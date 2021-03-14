export interface ClienteCadastroModel {
  nome: string;
  email: string;
  tipo: number;
  cpfOuCnpj: string;
  senha: string;

  logradouro: string;
  numero: string;
  complemento: string;
  bairro: string;
  cep: string;

  telefone1: string;
  telefone2?: string;
  telefone3?: string;
  
  cidadeId: number;
}