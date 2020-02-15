package ru.otus.admin;

import com.google.gson.Gson;
import ru.otus.user.UserService;
import ru.otus.utils.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainAdminServlet extends HttpServlet {

  private static final String ADMIN_MAIN_PAGE_TEMPLATE = "admin_main.html";
  private static final String ADMIN_CREATE_USER_PAGE_TEMPLATE = "admin_create_user.html";

  private final TemplateProcessor templateProcessor;
  private final UserService userService;
  private final Gson gson;

  public MainAdminServlet(UserService userService, Gson gson) throws IOException {
    this.templateProcessor = new TemplateProcessor();
    this.userService = userService;
    this.gson = gson;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Optional<String> actionParam = Optional.ofNullable(request.getParameter("action"));
    Optional<String> newUserLoginParam = Optional.ofNullable(request.getParameter("new_user_login"));
    Optional<String> newUserPasswordParam = Optional.ofNullable(request.getParameter("new_user_password"));
    Map<String, Object> pageVariables = new HashMap<>();

    // TODO: вот тут у меня нет опыта в создании REST API
    // TODO: возможно, стоит разнести на разные URI (контексты) создание нового пользователя и вывод пользователей
    // TODO: но похоже, что тогда нужно добавлять ещё фильтры на каждый из контекстов, что не очень здорово
    //
    // TODO: и как правильно разруливать значения параметров get запросов?
    if (!actionParam.isPresent() &&
        !newUserLoginParam.isPresent() &&
        !newUserPasswordParam.isPresent())
    {
      response.setContentType("text/html;charset=utf-8");
      response.getWriter().println(templateProcessor.getPage(ADMIN_MAIN_PAGE_TEMPLATE, pageVariables));
      response.setStatus(HttpServletResponse.SC_OK);
    }

    // Если задан параметр действия, то выполняем его
    actionParam.ifPresent(param -> {
      if (param.equals("createUser")) {
        try {
          response.getWriter().println(templateProcessor.getPage(ADMIN_CREATE_USER_PAGE_TEMPLATE, pageVariables));
        } catch (IOException e) {
          e.printStackTrace();
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
      }

      if (param.equals("showUsers")) {
        this.userService.getAllUsers().forEach(user -> {
          try {
            response.getWriter().println(this.gson.toJson(user));
            response.getWriter().println("<br>");
          } catch (IOException e) {
            e.printStackTrace();
          }

          response.setContentType("text/html;charset=utf-8");
          response.setStatus(HttpServletResponse.SC_OK);
        });
      }
    });

    // Если заданы параметры на создание нового пользователя, то добавляем
    if (newUserLoginParam.isPresent() && newUserPasswordParam.isPresent()) {
      this.userService.createUser(newUserLoginParam.get(), newUserPasswordParam.get());
      response.getWriter().println("User was successfully created");
      response.setContentType("text/html;charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }
}
