package com.lojavirtual.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer codigo;
	private String descricao;

	TipoCliente(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer codigo) {
		if (codigo == null)
			return null;
		
		for (TipoCliente tipo : TipoCliente.values()) {
			if (tipo.getCodigo().equals(codigo))
				return tipo;
		}
			
		throw new IllegalArgumentException("Não existe um tipo de cliente para o código informado: " + codigo);
	}
	
}
