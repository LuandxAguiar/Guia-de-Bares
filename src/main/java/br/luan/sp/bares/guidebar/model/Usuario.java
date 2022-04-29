package br.luan.sp.bares.guidebar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.luan.sp.bares.guidebar.util.HashUtil;
import lombok.Data;

@Data
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	//gravar um usuario em json com nome email e senha 
	//ele vira um objeto em json, logo não aparecera a senha do usuario 
	@JsonProperty(access = Access.WRITE_ONLY)
	private String senha;
	
	public void setSenha(String senha) {
		this.senha = (HashUtil.hash(senha));
	}
}
