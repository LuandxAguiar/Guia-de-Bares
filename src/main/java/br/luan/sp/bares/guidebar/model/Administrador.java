package br.luan.sp.bares.guidebar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.luan.sp.bares.guidebar.util.HashUtil;
import lombok.Data;

// anotação criar get get e setters 
@Data
// mapear a entidade para o JPA
@Entity
public class Administrador {
	//chave primaria e auto-incremento 
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//nome não seja vazio 
	@NotEmpty
	private String nome;
	//defini o email como unico
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);
	}
	
	//Métod que "seta" o hash na senha 
	public void setSenhaComHash(String hash) {
			this.senha = hash;
	}
}
