package br.luan.sp.bares.guidebar.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.luan.sp.bares.guidebar.annotation.Publico;

//clase para as paginas so serem acessadas apos o login
@Component
public class AppInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// variavel para obeter a URi da request
		String uri = request.getRequestURI();
		// variavel para sessão
		HttpSession session = request.getSession();
		if (uri.startsWith("/error")) {
			return true;
		}

		// verifica se handler é um handlerMethod
		// oq idnica que ele esta chamando o metodo
		// em algum controller
		if (handler instanceof HandlerMethod) {
			// logica para criar method publico, e privado
			// casting de objet para o HandlerMethod
			HandlerMethod metodo = (HandlerMethod) handler;

			if (uri.startsWith("/api")) {

				return true;

			} else {

				// verifica se tem anotação ou metodo e publico
				if (metodo.getMethodAnnotation(Publico.class) != null) {
					return true;

				}
				// verifica se esta logado
				if (session.getAttribute("usuarioLogado") != null) {
					return true;
				}
				// redireciona para a pagInicial
				response.sendRedirect("/");
				return false;

			}
		}
		return true;
	}
}
