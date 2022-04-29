package br.luan.sp.bares.guidebar.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.luan.sp.bares.guidebar.model.Bar;
import br.luan.sp.bares.guidebar.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>  {
	
	
}
