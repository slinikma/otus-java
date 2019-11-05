package ru.otus.hw27;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadExplorer {

  final CountDownLatch latch = new CountDownLatch(1);
//  Object monitor = new Object();
  private volatile Integer countA = 0;
  private volatile Integer countB = 0;
//  private AtomicInteger countA = new AtomicInteger(0);
//  private AtomicInteger countB = new AtomicInteger(0);

  private void incrementToTenA(Integer count, String varName, Object monitor) throws InterruptedException {
    for (var i = 0; i < 10; i++) {
      synchronized (monitor) {
        monitor.wait();
        System.out.println("Increment " + varName + ": " + count++);
//        System.out.println("Increment " + varName + ": " + countA++);
        monitor.notifyAll();
      }
    }
  }

  private void incrementToTenB(Integer count, String varName) {
    for (var i = 0; i < 10; i++) {
      synchronized (countA) {
//      System.out.println("Increment " + varName + ": " + count.incrementAndGet());
        System.out.println("Increment " + varName + ": " + countB++);
      }
    }
  }

  private void decrementToOne(Integer count, String varName) {
    for (var i = 0; i < 9; i++) {
      synchronized (countA) {
//      System.out.println("Decrement " + varName + ": " + count.decrementAndGet());
        System.out.println("Decrement " + varName + ": " + countB++);
      }
    }
  }

  private void letsTry() throws InterruptedException {
    Thread threadA = new Thread(() -> {
      try {
        latch.await();
//        countB.wait();
        incrementToTen(countA, "countA", countB);
//        decrementToOne(countA, "countA");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    Thread threadB = new Thread(() -> {
      try {
        latch.await();
        incrementToTen(countB, "countB", countA);
//        decrementToOne(countB, "countB");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    threadA.start();
    threadB.start();

    latch.countDown();

    threadA.join();
    threadB.join();
  }

  public static void main(String[] args) {
    ThreadExplorer explorer = new ThreadExplorer();
    try {
      explorer.letsTry();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
