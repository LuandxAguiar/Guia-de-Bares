package br.luan.sp.bares.guidebar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.luan.sp.bares.guidebar.model.Bar;
import br.luan.sp.bares.guidebar.model.TipoBar;

public interface TipoBarRepository extends PagingAndSortingRepository<TipoBar, Long>{
	
	@Query("SELECT b FROM TipoBar b WHERE b.nome LIKE %:b% OR b.palavrasChave LIKE %:b% OR b.descricao LIKE %:b% ORDER BY b.palavrasChave")
	public List<TipoBar> buscar(@Param("b")String geral);

public interface BarRepository extends PagingAndSortingRepository<Bar, Long> {
	public List<TipoBar> findAllByrderByNomeAsc();
}
}