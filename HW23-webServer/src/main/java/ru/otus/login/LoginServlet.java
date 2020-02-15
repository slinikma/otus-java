package ru.otus.login;

import ru.otus.utils.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

  private static final String ADMIN_LOGIN_PAGE_TEMPLATE = "login.html";

  private final TemplateProcessor templateProcessor;

  public LoginServlet() throws IOException {
    this.templateProcessor = new TemplateProcessor();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, Object> pageVariables = new HashMap<>();

    response.setContentType("text/html;charset=utf-8");
    response.getWriter().println(templateProcessor.getPage(ADMIN_LOGIN_PAGE_TEMPLATE, pageVariables));
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
