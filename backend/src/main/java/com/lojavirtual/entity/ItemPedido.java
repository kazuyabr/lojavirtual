package com.lojavirtual.entity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK item = new ItemPedidoPK();
	
	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {}
	
	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		this.item.setPedido(pedido);
		this.item.setProduto(produto);
		
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public double getSubTotal() {
		return (preco - desconto) * quantidade;
	}
	
	@JsonIgnore
	public Pedido getPedido() {
		return item.getPedido();
	}
	
	public void setPedido(Pedido pedido) {
		this.item.setPedido(pedido);
	}
	
	public Produto getProduto() {
		return item.getProduto();
	}
	
	public void setProduto(Produto produto) {
		this.item.setProduto(produto);
	}

	public ItemPedidoPK getItem() {
		return item;
	}

	public void setItem(ItemPedidoPK item) {
		this.item = item;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		
		builder.append(getProduto().getNome());
		builder.append(", Quatidade: ");
		builder.append(getQuantidade());
		builder.append(", Preço Unitário: ");
		builder.append(format.format(getPreco()));
		builder.append(", Subtotal: ");
		builder.append(format.format(getSubTotal()));
		builder.append("\n");
		
		return builder.toString();
	}

}
