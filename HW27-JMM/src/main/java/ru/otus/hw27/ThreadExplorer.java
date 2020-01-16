package ru.otus.hw27;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadExplorer {

  private static Logger logger = LoggerFactory.getLogger(ThreadExplorer.class);
  private static String thread1Name = "Thread 1";
  private static String thread2Name = "Thread 2";

  private static String lastThreadName = "Thread 2";

  private static void sleep() {
    try {
      Thread.sleep(1_000);
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  // Т.к. synchronized является барьером, то на lastThreadName volatile не нужен
  private synchronized void doNumberCycle(int from, int to, int step) {

    Integer currentVal = from;

    if (from < to) {
      step = Math.abs(step);
    } else {
      step = Math.abs(step) * -1;
    }

    while (true) {
      try {
        //spurious wakeup
        while (lastThreadName.equals(Thread.currentThread().getName())) {
          this.wait();
        }

        logger.info("{}: {}", Thread.currentThread().getName(), currentVal);

        currentVal += step;

        if (currentVal == to ||
            currentVal == from)
        {
          step *= -1;
        }

        // Решил сделать по имени потока, хотя можно и currentVal использовать, но это только запутывает
        lastThreadName = Thread.currentThread().getName();
        sleep();
        notifyAll();

      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public static void main(String[] args) {
    ThreadExplorer explorer = new ThreadExplorer();
    Thread t1 = new Thread(() -> explorer.doNumberCycle(1, 10, 1));
    Thread t2 = new Thread(() -> explorer.doNumberCycle(1, 10, 1));

    t1.setName(thread1Name);
    t2.setName(thread2Name);

    t1.start();
    t2.start();
  }
}
