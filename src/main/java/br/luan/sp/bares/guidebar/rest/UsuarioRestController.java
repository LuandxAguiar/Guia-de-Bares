package br.luan.sp.bares.guidebar.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.luan.sp.bares.guidebar.annotation.Privado;
import br.luan.sp.bares.guidebar.annotation.Publico;
import br.luan.sp.bares.guidebar.model.Bar;
import br.luan.sp.bares.guidebar.model.Erro;
import br.luan.sp.bares.guidebar.model.Usuario;
import br.luan.sp.bares.guidebar.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	
	@Autowired
	private  UsuarioRepository repository;
	//criando resposta em post para o usuario 
	//post devolvera Json ocm objeto que foi gerado 
	//tera que ser personalizado, pois e a resposta n resposta 200 ou 201
	//sempre que quiser alterar a resposta usar Response Entity
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario){
		//insere usuario no banco 
		//
		//quando cair no try catch no caso de duplicado 
		try {
		repository.save(usuario);
		//retorna um cod HTTP 201 informa como acessar o recroso inserido 
		//e acrescenta no corpo da resposta o objeto inserido 
		return ResponseEntity.created(URI.create("/api/usuario/"+usuario.getId())).body(usuario);
		}catch(DataIntegrityViolationException e ) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR,
					"Registro Duplcado", e.getClass().getName());
			return new ResponseEntity<Object>(erro,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(Exception e ) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage(), e.getClass().getName());
			return new ResponseEntity<Object>(erro,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	}
	@Privado
	@RequestMapping("{id}")
	public ResponseEntity<Usuario> getBar(@PathVariable("id") Long idUser){
	 // tentando buscar o bar no repository
		Optional<Usuario> optional = repository.findById(idUser);
		//
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
					}else {
						return ResponseEntity.notFound().build();
					}
	}
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT , consumes = MediaType.APPLICATION_JSON_VALUE)
	// no response pode colocar a frase concluida se preferir 
	public ResponseEntity<Void> atualizarUsuario
	(@RequestBody Usuario usuario, @PathVariable("id")Long id){
		//validação do id 
		if(id != usuario.getId()) {
			//forçando a exception 
			throw new RuntimeException("ID Invalido ");
		}
		repository.save(usuario); 
		return ResponseEntity.ok().build();
		
		
	}
	//metodo para deletar elo posttman 
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario){
		repository.deleteById(idUsuario);
		//nocontent codigo 204
		return ResponseEntity.noContent().build();
	}
	
}


