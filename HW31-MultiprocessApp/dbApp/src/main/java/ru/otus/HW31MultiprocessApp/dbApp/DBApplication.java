package ru.otus.HW31MultiprocessApp.dbApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class DBApplication {

  private static final Logger logger = LoggerFactory.getLogger(DBApplication.class);
//  private static final int PORT = 8082; // TODO: move to property

	public static void main(String[] args) {
		SpringApplication.run(DBApplication.class, args);
	}

//  @EventListener(ApplicationReadyEvent.class)
//  public void doSomethingAfterStartup(
//      DBService dbService
//  ) {
//    //DatagramSocket - UDP
//    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//      while (!Thread.currentThread().isInterrupted()) {
//        logger.info("waiting for client connection");
//        try (Socket clientSocket = serverSocket.accept()) {
//          dbService.clientHandler(clientSocket);
//        }
//      }
//    } catch (Exception ex) {
//      logger.error("error", ex);
//    }
//  }
}
