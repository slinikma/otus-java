package ru.otus;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.admin.MainAdminServlet;
import ru.otus.auth.AuthorizationFilter;
import ru.otus.authenticate.AuthenticateServlet;
import ru.otus.login.LoginServlet;
import ru.otus.mongodb.MongoUserDao;
import ru.otus.user.UserService;

public class Main {
  private static final int PORT = 8080;
  private final static String STATIC = "/static";

  public static void main(String[] args) throws Exception {
    new Main().start();
  }

  private void start() throws Exception {
    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

    ResourceHandler resourceHandler = new ResourceHandler();
    Resource resource = Resource.newClassPathResource(STATIC);
    resourceHandler.setBaseResource(resource);

    contextHandler.addServlet(new ServletHolder(new LoginServlet()), "/login");
    contextHandler.addServlet(new ServletHolder(new AuthenticateServlet(userService())), "/authenticate");
    contextHandler.addServlet(new ServletHolder(new MainAdminServlet(userService(), gson())), "/admin");

    contextHandler.addFilter(new FilterHolder(new AuthorizationFilter()), "/admin", null);

    // Создаём сервер Jetty и передаём списко хэндлеров, которые заданы в контексте
    Server server = new Server(PORT);
    server.setHandler(new HandlerList(resourceHandler, contextHandler));

    server.start();
    // Ждём поток, который стартует сервер
    server.join();
  }

  private UserService userService() {
    return new UserService(userDao());
  }

  private MongoUserDao userDao() {
    return new MongoUserDao();
  }

  private Gson gson() {
    return new Gson();
  }
}
