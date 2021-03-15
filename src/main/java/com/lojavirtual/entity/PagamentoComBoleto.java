package com.lojavirtual.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.lojavirtual.enums.EstadoPagamento;

@Entity
@Table(name = "tb_pagamento_com_boleto")
@JsonTypeName(value = "pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {

	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataVencimeto;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataPagamento;
	
	public PagamentoComBoleto() {}

	public PagamentoComBoleto(Long id, EstadoPagamento estado, Pedido pedido, Date dataVencimeto, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataVencimeto = dataVencimeto;
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimeto() {
		return dataVencimeto;
	}

	public void setDataVencimeto(Date dataVencimeto) {
		this.dataVencimeto = dataVencimeto;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
}
