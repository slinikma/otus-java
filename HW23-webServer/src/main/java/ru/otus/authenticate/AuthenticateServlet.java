package ru.otus.authenticate;

import ru.otus.user.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class AuthenticateServlet extends HttpServlet {

  private UserService userService;

  public AuthenticateServlet(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Optional<String> login = Optional.ofNullable(request.getParameter("login"));
    Optional<String> password = Optional.ofNullable(request.getParameter("password"));

    if (login.isPresent() && password.isPresent()) {
      if (userService.authenticate(login.get(), password.get())) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(90);
        response.setHeader("Location", "http://localhost:8080/admin");
        response.setStatus(303); // TODO: правильно ли использовать "See Other" ?
      } else {
        response.setHeader("Location", "http://localhost:8080/login");
        response.setStatus(303); // TODO: правильно ли использовать "See Other" ?
      }
    }
  }
}
