package br.luan.sp.bares.guidebar.rest;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.luan.sp.bares.guidebar.annotation.Publico;
import br.luan.sp.bares.guidebar.model.Bar;
import br.luan.sp.bares.guidebar.model.TipoBar;
import br.luan.sp.bares.guidebar.repository.BarRepository;
import br.luan.sp.bares.guidebar.repository.TipoBarRepository;

@RestController
@RequestMapping("/api/bar")
public class BarRestController {
	
	@Autowired
	private BarRepository repository;
	private TipoBarRepository tRepository;
	//tipo de lista menor 
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Bar> getBar(){
		
		return repository.findAll();
		
	}
	

	
	//esse metodo devolve bar pelo id 
	@Publico
	@RequestMapping("/{id}")
	public ResponseEntity<Bar> getBar(@PathVariable("id") Long idBar){
	 // tentando buscar o bar no repository
		Optional<Bar> optional = repository.findById(idBar);
		//
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
					}else {
						return ResponseEntity.notFound().build();
					}
	}
	// metodod que busca id do tipo de restaurante 
	@Publico
	@RequestMapping(value = "/tipo/{id}", method = RequestMethod.GET)
	public Iterable<Bar> getListagemBar(@PathVariable ("id") Long id){
		return repository.findByTipoId(id);
		
	}
	
	
	}

