import { CidadeModel } from './cidade.model';

export interface EnderecoModel {
  id: number;
  logradouro: string;
  numero: string;
  complemento: string;
  bairro: string;
  cep: string;
  cidade: CidadeModel;
}