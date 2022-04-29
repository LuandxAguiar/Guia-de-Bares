package br.luan.sp.bares.guidebar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.luan.sp.bares.guidebar.model.TipoBar;
import br.luan.sp.bares.guidebar.repository.AdminRepository;
import br.luan.sp.bares.guidebar.repository.TipoBarRepository;
import br.luan.sp.bares.guidebar.util.FireBaseUtil;

@Controller
public class TipoBarController {
	
	@Autowired
	private TipoBarRepository repository;
	
	
	
	@RequestMapping(value = "cadBar", method = RequestMethod.GET)
	private String form() {
		
		return "CadastroBar";
	}
	
	@RequestMapping(value = "salvarBar", method = RequestMethod.POST)
	public String salvarBar(@Valid TipoBar bar, BindingResult result, RedirectAttributes attr) {
		
		//verificar se houve erro na validação 
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos novamente");
			//redirecionando para form 
			return "redirect:cadBar "; 
		}
		
		try {
			repository.save(bar);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com Sucesso ID:"+bar.getId());
		}catch(Exception e ) {
			attr.addFlashAttribute("mensagemErro", "Verificar os campos novamente... "+e.getMessage());
		}
		//redirecionar para form 
		return "redirect:cadBar";
	}
	
	//fazendo a listagem 
	@RequestMapping("listaBar/{page}")
	public String listaBar(Model model, @PathVariable("page")int page) {
		//criar uma pageble para informar os paramatros da pagina
		PageRequest pageble = PageRequest.of(page-1 , 6 , Sort.by(Sort.Direction.ASC,"nome"));
		//criar uma pagina de bar com os parametro do repository 
		//essa linha ira gerar no banco 
		Page<TipoBar> pagina = repository.findAll(pageble);
		//adiciona a model a lista com os bares 
		model.addAttribute("bares" ,pagina.getContent());
		
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
			return"listaBar";
		}
	
	@RequestMapping("altere")
	public String alterare(Long id,Model model) {
		TipoBar bar = repository.findById(id).get();
		model.addAttribute("bart",bar);
		return "forward:cadBar";
	}
	@RequestMapping("exclua")
	public String exclua (Long id) {
		repository.deleteById(id);
		return "redirect:listaBar/1";
	}
	@RequestMapping("buscar")
	public String buscar (String buscar, Model model) {
		model.addAttribute("bares", repository.buscar(buscar));
		return "listaBar";
	}
		
	}
	
	

