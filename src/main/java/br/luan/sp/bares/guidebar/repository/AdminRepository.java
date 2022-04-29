package br.luan.sp.bares.guidebar.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.luan.sp.bares.guidebar.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long> {
	
	
	
	//buscar adm no banco de dados para fazer o login no html de login 
	public Administrador findByEmailAndSenha(String email, String senha);
}
