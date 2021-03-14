import { IdentificadorModel } from './referencia.model';
import { PagamentoModel } from './pagamento.model';
import { ItemPedidoModel } from './item-pedido.model';

export interface PedidoModel {
  cliente: IdentificadorModel;
  enderecoDeEntrega: IdentificadorModel;
  pagamento: PagamentoModel;
  itens: ItemPedidoModel[];
}