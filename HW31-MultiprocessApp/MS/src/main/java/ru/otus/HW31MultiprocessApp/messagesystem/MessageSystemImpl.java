package ru.otus.HW31MultiprocessApp.messagesystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.HW31MultiprocessApp.msclient.Message;
import ru.otus.HW31MultiprocessApp.msclient.MsClient;
import ru.otus.HW31MultiprocessApp.msclient.common.Serializers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public final class MessageSystemImpl implements MessageSystem {
  private static final Logger logger = LoggerFactory.getLogger(MessageSystemImpl.class);
  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new Jdk8Module());
  private static final int MESSAGE_QUEUE_SIZE = 1_000;
  private static final int MSG_HANDLER_THREAD_LIMIT = 2;
  private static final int PORT = 8081;

  private final AtomicBoolean runFlag = new AtomicBoolean(true);

  private final Map<String, Socket> clientMap = new ConcurrentHashMap<>();
  private final BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_SIZE);

  private final ExecutorService msgProcessor = Executors.newSingleThreadExecutor(runnable -> {
    Thread thread = new Thread(runnable);
    thread.setName("msg-processor-thread");
    return thread;
  });

  private final ExecutorService connectionProcessor = Executors.newSingleThreadExecutor(runnable -> {
    Thread thread = new Thread(runnable);
    thread.setName("connection-processor-thread");
    return thread;
  });

  private final ExecutorService msgHandler = Executors.newFixedThreadPool(MSG_HANDLER_THREAD_LIMIT, new ThreadFactory() {
    private final AtomicInteger threadNameSeq = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable runnable) {
      Thread thread = new Thread(runnable);
      thread.setName("msg-handler-thread-" + threadNameSeq.incrementAndGet());
      return thread;
    }
  });

  public MessageSystemImpl() {
    msgProcessor.submit(this::msgProcessor);
    connectionProcessor.submit(this::connectionProcessor);
  }

  private void connectionProcessor() {
    //DatagramSocket - UDP
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (runFlag.get()) {
        logger.info("waiting for client connection");
        try (Socket clientSocket = serverSocket.accept()) {
          clientRequestHandler(clientSocket);
        }
      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

  // pooler - достаём сообщение из очереди, достаём клиента
  // после чего берём трэд из трэд пула и выполняем в нём msClient.handle(msg)
  private void msgProcessor() {
    logger.info("msgProcessor started");
    while (runFlag.get()) {
      try {
        Message msg = messageQueue.take();
        if (msg == Message.VOID_MESSAGE) {
          logger.info("received the stop message");
        } else {
          Socket clientTo = clientMap.get(msg.getTo());
          if (clientTo == null) {
            logger.warn("client not found");
          } else {
            msgHandler.submit(() -> handleMessage(clientTo, msg));
          }
        }
      } catch (InterruptedException ex) {
        logger.error(ex.getMessage(), ex);
        Thread.currentThread().interrupt();
      } catch (Exception ex) {
        logger.error(ex.getMessage(), ex);
      }
    }
    msgHandler.submit(this::messageHandlerShutdown);
    logger.info("msgProcessor finished");
  }

  private void messageHandlerShutdown() {
    msgHandler.shutdown();
    logger.info("msgHandler has been shut down");
  }

  private void handleMessage(Socket msClient, Message msg) {
    try {
      PrintWriter out = new PrintWriter(msClient.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(msClient.getInputStream()));

      logger.info("sending to MS server");
      out.println(objectMapper.writeValueAsString(msg));
      Message respMsg = objectMapper.readValue(in.readLine(), Message.class);
      this.newMessage(respMsg);
      logger.info("MS server response: {}", respMsg.toString());
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      logger.error("message:{}", msg);
    }
  }

  private void insertStopMessage() throws InterruptedException {
    boolean result = messageQueue.offer(Message.VOID_MESSAGE);
    while (!result) {
      Thread.sleep(100);
      result = messageQueue.offer(Message.VOID_MESSAGE);
    }
  }

  @Override
  public void addClient(String clientId, Socket msClient) {
    var msClientAddr = getSocketInetAddr(msClient); // TODO: убрать?
    logger.info("new client address: {}", msClientAddr);
    if (clientMap.containsKey(msClientAddr)) {
      throw new IllegalArgumentException("Error. client: " + msClientAddr + " already exists");
    }
    clientMap.put(msClientAddr, msClient);
  }

  @Override
  @SneakyThrows
  public void removeClient(String clientId) {
    Socket removedClient = clientMap.remove(clientId);
    if (removedClient == null) {
      logger.warn("client not found: {}", clientId);
    } else {
      removedClient.close();
      logger.info("removed client:{}", removedClient);
    }
  }

  @Override
  // TODO: тут серверный сокет системы сообщений (точнее это хэндлер сокета)
  public boolean newMessage(Message msg) {
    if (runFlag.get()) {
      return messageQueue.offer(msg);
    } else {
      logger.warn("MS is being shutting down... rejected:{}", msg);
      return false;
    }
  }

  @Override
  public void dispose() throws InterruptedException {
    runFlag.set(false);
    insertStopMessage();
    msgProcessor.shutdown();
    msgHandler.awaitTermination(60, TimeUnit.SECONDS);
  }

  @Override
  public void clientRequestHandler(Socket clientSocket) {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
      Message msg = objectMapper.readValue(in.readLine(), Message.class);
      this.addClient(msg.getFrom(), clientSocket);

      logger.info("from client: {} ", msg.toString());
      newMessage(msg);

    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

  private String getSocketInetAddr(Socket sock) {
    return sock.getInetAddress() + ":" + sock.getPort();
  }
}
