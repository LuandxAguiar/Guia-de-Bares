package br.luan.sp.bares.guidebar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.luan.sp.bares.guidebar.model.Bar;
import br.luan.sp.bares.guidebar.model.TipoBar;
import br.luan.sp.bares.guidebar.repository.BarRepository;
import br.luan.sp.bares.guidebar.repository.TipoBarRepository;
import br.luan.sp.bares.guidebar.util.FireBaseUtil;

@Controller
public class BarController {
	@Autowired
	private BarRepository repository;
	@Autowired
	private TipoBarRepository barTipo;
	@Autowired
	private FireBaseUtil fireUtil;

	@RequestMapping("formularioBar")
	public String form(Model model) {
		model.addAttribute("tipo", barTipo.findAll());
		return "bar/formBar";
	}

	@RequestMapping("saveBar")
	public String salvar(Bar bar, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		System.out.println("PASSOU AQUI");
		String fotos = bar.getFotos();
		// String para amarazenar aas URls
		
		for (MultipartFile arquivo : fileFotos) {
			// verifica se o arquivo e existente

			if (arquivo.getOriginalFilename().isEmpty()) {
				// vai para o proximo arquivo
				continue;

			}
			try {
				fotos += fireUtil.upload(arquivo) + ";";
			} catch (IOException e) {

				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}
		// guardar na variavel bar as fotos
		bar.setFotos(fotos);
		repository.save(bar);
		return "redirect:formularioBar";

	}
	//fazendo listagem 
	@RequestMapping("listBar/{page}")
	public String listaBar(Model model, @PathVariable("page")int page) {
		//criar uma pageble para informar os paramatros da pagina
		PageRequest pageble = PageRequest.of(page-1 , 6 , Sort.by(Sort.Direction.ASC,"nome"));
		//criar uma pagina de bar com os parametro do repository 
		//essa linha ira gerar no banco 
		Page<Bar> pagina = repository.findAll(pageble);
		//adiciona a model a lista com os bares 
		model.addAttribute("bar" ,pagina.getContent());
		
		//gerar total de paginas 
		int totalPaginas = pagina.getTotalPages();
		//vetor para a lista 
		List<Integer> numPagina = new ArrayList<Integer>();
		//prenchendo os litar da paginas 
		for(int i = 1 ; i <= totalPaginas;i++) {
			numPagina.add(i);
		}
			//fazendo a model para os valores serem adicionados
			
			model.addAttribute("numPagina1",numPagina);
			model.addAttribute("totalPaginas1",totalPaginas);
			model.addAttribute("pagAtual1",page);
			
			//retornando para o html da lista 
			return"listeBar";
		}
	
	@RequestMapping("alterandoBar")
	public String alterare(Long id,Model model) {
		Bar bar = repository.findById(id).get();
		model.addAttribute("bares",bar);
		return "forward:formularioBar";
	}
	// metodo excluir
	@RequestMapping("exclue")
	public String exclua (Long id) {
		Bar bare = repository.findById(id).get();
		if(bare.getFotos().length() > 0) {
			for(String foto : bare.verFotos()) {
				fireUtil.deletar(foto);
			}
		}
		repository.delete(bare);
		return "redirect:listBar/1";
	}	
	@RequestMapping("excluirFotos")
	public String excluirFotos(Long idRest, int numFoto, Model model) {
		// buscar o bar no banco 
		Bar bart = repository.findById(idRest).get();
		//buscar URL 
		String urlFoto = bart.verFotos()[numFoto];
		//deleta a foto 
		fireUtil.deletar(urlFoto);
		//remover do atributo foto 
		bart.setFotos(bart.getFotos().replace(urlFoto+";", ""));
		//salva no banco 
		repository.save(bart);
		//coloca o bar na model
		model.addAttribute("bares",bart);
		return "forward:formularioBar";
	}
}
