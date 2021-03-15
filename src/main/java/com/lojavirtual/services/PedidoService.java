package com.lojavirtual.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lojavirtual.entity.Cliente;
import com.lojavirtual.entity.ItemPedido;
import com.lojavirtual.entity.PagamentoComBoleto;
import com.lojavirtual.entity.Pedido;
import com.lojavirtual.enums.EstadoPagamento;
import com.lojavirtual.repository.ItemPedidoRepository;
import com.lojavirtual.repository.PagamentoRepository;
import com.lojavirtual.repository.PedidoRepository;
import com.lojavirtual.security.UserSS;
import com.lojavirtual.services.exceptions.AuthorizationException;
import com.lojavirtual.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	private PedidoRepository repository;
	private BoletoService boletoService;
	private PagamentoRepository pagamentoRepository;
	private ItemPedidoRepository itemPedidoRepository;
	private ProdutoService produtoService;
	private ClienteService clienteService;
	private EmailService emailService;
	
	public PedidoService(PedidoRepository repository, BoletoService boletoService, 
			PagamentoRepository pagamentoRepository, ItemPedidoRepository itemPedidoRepository, 
			ProdutoService produtoService, ClienteService clienteService, EmailService emailService) {
		this.repository = repository;
		this.boletoService = boletoService;
		this.pagamentoRepository = pagamentoRepository;
		this.itemPedidoRepository = itemPedidoRepository;
		this.produtoService = produtoService;
		this.clienteService = clienteService;
		this.emailService = emailService;
	}
	
	public Pedido findById(long id) {
		Optional<Pedido> pedido = repository.findById(id);
		return pedido.orElseThrow(
				() -> new ObjectNotFoundException("Nenhum pedido foi encontrado com o Id: " + id)
			);
	}
	
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.findById(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto boleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(boleto, pedido.getInstante());
		}
		
		pedido = repository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.findById(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		//System.out.println(pedido);
		emailService.sendOrderConfirmationEmail(pedido);
		return pedido;
	}
	
	public Page<Pedido> findPedidoByCliente(int page, int linesPerPage, 
			String direction, String orderBy) {
		UserSS user = UserService.authenticaded();
		
		if (user == null)
			throw new AuthorizationException("Acesso negado!");
		
		PageRequest pedidosDoCliente = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.findById(user.getId());
		
		return repository.findByCliente(cliente, pedidosDoCliente);
	}
	
}
