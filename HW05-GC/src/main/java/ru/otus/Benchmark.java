package ru.otus;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tully and modified by Nikita
 */
class Benchmark implements BenchmarkMBean {
    private static Logger logger = LoggerFactory.getLogger(Benchmark.class);
    private volatile int size = 0;
    private ArrayList<Byte> globalLst = new ArrayList<Byte>();


    private class LoggingTask extends TimerTask {

        @Override
        public void run() {
            logger.info("GC metrics: {}", GC.getGCMetrics());
            logger.debug("Approximately global list allocates: {}mb", String.format("%.2f", ((double)globalLst.size()*8+8)/(1024*1024)));
            logger.debug("Free heap size: {}mb left",  String.format("%.2f", ((double)Runtime.getRuntime().freeMemory())/(1024*1024)));
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    void run() throws InterruptedException {
        Timer timer = new Timer();
        LoggingTask myLoggingTask = new LoggingTask();

        timer.scheduleAtFixedRate(myLoggingTask, 0,60_000);
        System.out.println("Starting the loop");
        while (true) {
            int local = size;
            Byte[] localArr = new Byte[local];

            for (int i=0; i < local; i++) {
                localArr[i] = (byte)i;
            }
            globalLst.addAll(Arrays.asList(localArr));
            globalLst.subList(globalLst.size() - localArr.length/2, globalLst.size()).clear();

            Thread.sleep(30);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

}
