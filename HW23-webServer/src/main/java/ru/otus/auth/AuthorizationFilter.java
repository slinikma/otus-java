package ru.otus.auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

  private ServletContext context;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.context = filterConfig.getServletContext();
  }

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String uri = request.getRequestURI();
    this.context.log("Requested Resource: " + uri);

    // false - только получаем сессию, но не создаём
    HttpSession session = request.getSession(false);

    if (session == null) {
      // Сессия не была создана (клиент не передал Cookie JSESSIONID)
      response.setHeader("Location", "http://localhost:8080/login");
      response.setStatus(303); // TODO: правильно ли использовать "See Other" ?
    } else {
      // Если передал Cookie JSESSIONID и она валидна, тогда передаём по цепочке далльше в следующий сервлет
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void destroy() {

  }
}
