package br.luan.sp.bares.guidebar.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
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

import br.luan.sp.bares.guidebar.annotation.Publico;
import br.luan.sp.bares.guidebar.model.Administrador;
import br.luan.sp.bares.guidebar.repository.AdminRepository;
import br.luan.sp.bares.guidebar.util.HashUtil;

@Controller
public class AdministradorController {
	//variavel para persistencias
	@Autowired
	private AdminRepository repository;
	
	@RequestMapping(value = "cadAdm", method = RequestMethod.GET)
	private String form() {
		
		return "CadastroAdm";
	}
	//salvando adm, @valid valida os campo do admni.java, redirect pendurar
	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		
		//verificar se houve erro na validação
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro","Verificar os campos novamente..");
			//redireciona para o form
			return "redirect:cadAdm";
		}
		
		//variavel para descobrir se estou alterando ou inserindo 
		boolean alterando = admin.getId() != null ? true : false;
		
		//verificar se asenha esta vazia, pegando o hash, pois ela gera um numero igual sempre que salva uma senha vazia 
		if(admin.getSenha().equals(HashUtil.hash(""))) {
			if(!alterando) {
			//retirar a parte antes do @ do email //index of para dizer ate onde vai, pegando a senha antes do @, como primeira senha 
			String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
			//setar a parte da senha do admin 
			admin.setSenha(parte);
		}else {
			//buscar a senha atual no bd 				.get() aqui para gerar o adm
			String hash = repository.findById(admin.getId()).get().getSenha();
			//"setar o hash da nova senha que sera criada no adm
			admin.setSenhaComHash(hash);
		}
		}
		try {
			//salvando no BD a entidade
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso ID:"+admin.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro","Verificar os campos novamente.."+e.getMessage());
		}
		//redireciona para o form
		return "redirect:cadAdm";
	}
	//colocando uma variavel para paginação /{page}
	//listagem
	@RequestMapping("listaAd/{page}")
	public String listadmin(Model model , @PathVariable("page")int page) {
		
		//criar um pageble informando os parametros da pagina
		
		PageRequest pageble = PageRequest.of(page-1, 6 , Sort.by(Sort.Direction.ASC, "nome"));
		//criar uma pagina de adm com os parametros passado ao repository
		//essa linha ja gera no banco de dados 
		Page<Administrador> pagina = repository.findAll(pageble);
		
		//add a model a lista com os admns
		
		model.addAttribute("admins", pagina.getContent());
		
		//variavel para o total de paginas
		int totalPages = pagina.getTotalPages();
		//criar um list de inteiros, poderia ser vetor tambem, lista para armazernar os num da paginas
		
		List<Integer> numPagina = new ArrayList<Integer>();
		//preencher o list com as paginas
		for(int i = 1; i <= totalPages; i++) {
			numPagina.add(i);
		}
		
		//adicionando os valores a model
		
		model.addAttribute("numPaginas", numPagina);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);
		//retorna para o html da lista 
		return"listaAdm";
	}
		@RequestMapping("alterar")
		public String alterar(Long id, Model model) {
			Administrador admin = repository.findById(id).get();
			model.addAttribute("admin", admin);
			return "forward:cadAdm";
		}
		@RequestMapping("excluir")
		public String excluir(Long id) {
			repository.deleteById(id);
			return"redirect:listaAd/1";
		}
		//metodo login ja com o hash colocado, pois ele passa pelo adm --- httpSession para quando logar entrar em uma sessão
		@Publico
		@RequestMapping("login")

		public String login(Administrador admLogin, RedirectAttributes attr, HttpSession session){
			//buscar o adm no banco
			
			Administrador admin = repository.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
			//verificar se existe
			if(admin == null) {
				System.out.println("adm não existe");
				attr.addFlashAttribute("mensagemErro","Login ou Senha invalida(s)");
					return"redirect:/";
				
			}else {
				System.out.println("adm existe");
				//salva o adm na sessão 
				session.setAttribute("usuarioLogado", admin);
				//mandar para a pagina inicial ou lista de restaurante 
				return"redirect:listBar/1";
			}
			//logout 
			
		}
		@RequestMapping("logout")
		public String logout (HttpSession session) {
			//invalida a sessão 
			session.invalidate();
			//voltar a pagina inicial
			return "redirect:/";
			
		}
}
