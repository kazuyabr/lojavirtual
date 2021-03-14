package com.lojavirtual.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.lojavirtual.entity.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto boleto, Date instanteDoPedido) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(instanteDoPedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		
		boleto.setDataVencimeto(calendar.getTime());
	}
	
}
