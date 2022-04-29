package br.luan.sp.bares.guidebar.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.luan.sp.bares.guidebar.model.Bar;

public interface BarRepository extends PagingAndSortingRepository<Bar, Long> {
	
	public List<Bar> findByTipoId(Long id); 
	
	// se quiser listar qualquer coisa como estacionamnto 
	//public List<Bar> findByTipoEstacionamento(boolean estacionamneto); 
}

