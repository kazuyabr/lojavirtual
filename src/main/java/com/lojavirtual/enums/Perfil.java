package com.lojavirtual.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private Integer codigo;
	private String descricao;
	
	Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer codigo) {
		if (codigo == null)
			return null;
		
		for (Perfil perfil : Perfil.values()) {
			if (perfil.getCodigo().equals(codigo))
				return perfil;
		}
		
		throw new IllegalArgumentException("O perfil não foi encontrado! Código: " + codigo);
	}
	
}
