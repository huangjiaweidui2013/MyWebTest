package com.huang.demo;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @author localuser
 * create at 2022/7/27 11:24
 * @description
 */
@Slf4j
public class CompletionServiceDemo {
    public static void main(String[] args) {
        try {

            executorCompletionServicePoll();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 1.使用Future
     * 创建CompletionServiceDemo类，创建好的线程对象，使用Executors工厂类来创建ExecutorService的实例(即线程池)，
     * 通过ThreadPoolExecutor的.submit()方法提交到线程池去执行，线程执行后，返回值Future可被拿到
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void executorService() throws ExecutionException, InterruptedException {
        // 1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 2.创建Callable子线程对象任务
        Callable callable1 = () -> {
            Thread.sleep(5000);
            return ("我是5000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        Callable callable2 = () -> {
            Thread.sleep(3000);
            return ("我是3000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        Callable callable3 = () -> {
            Thread.sleep(1000);
            return ("我是1000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        // 3.使用Future提交三个任务到线程池
        Future future1 = executorService.submit(callable1);
        Future future2 = executorService.submit(callable2);
        Future future3 = executorService.submit(callable3);

        // 4.获取返回值
        log.info("开始获取结果 " + getStringDate());
        log.info(future1.get() + "" + getStringDate());
        log.info(future2.get() + "" + getStringDate());
        log.info(future3.get() + "" + getStringDate());
        log.info("结束 " + getStringDate());

        // 5.关闭线程池
        executorService.shutdown();
    }

    // 获取时间函数
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        return simpleDateFormat.format(currentTime);
    }

    public static void executorCompletionService() throws InterruptedException, ExecutionException {
        // 1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 2.创建一个ExecutorCompletionService放入线程池实现CompletionService接口
        ExecutorCompletionService completionService = new ExecutorCompletionService(executorService);

        // 3.创建Callable子线程对象任务
        Callable callable1 = () -> {
            Thread.sleep(5000);
            return ("我是5000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        Callable callable2 = () -> {
            Thread.sleep(3000);
            return ("我是3000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        Callable callable3 = () -> {
            Thread.sleep(1000);
            return ("我是1000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        // 4.使用CompletionService提交三个任务到线程池
        completionService.submit(callable1);
        completionService.submit(callable2);
        completionService.submit(callable3);

        // 5.获取返回值
        log.info("开始获取结果 " + getStringDate());
        /*
         * take()方法从队列中获取完成任务的Future对象，会阻塞，一直等待线程池中返回一个结果，谁最先执行完成谁最先返回，获取到的对象再调用.get()方法获取结果
         *
         * 如果调用take()方法的次数大于任务数，会因为等不到有任务返回结果而阻塞，只有三个任务，第四次take等不到结果而阻塞
         */
        log.info(completionService.take().get() + "" + getStringDate());
        log.info(completionService.take().get() + "" + getStringDate());
        log.info(completionService.take().get() + "" + getStringDate());
        log.info("结束 " + getStringDate());

        // 6.关闭线程池
        executorService.shutdown();

    }

    public static void executorCompletionServicePoll() throws InterruptedException, ExecutionException {
        // 1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 2.创建一个ExecutorCompletionService放入线程池实现CompletionService接口
        ExecutorCompletionService completionService = new ExecutorCompletionService(executorService);

        // 3.创建Callable子线程对象任务
        Callable callable1 = () -> {
            Thread.sleep(5000);
            return ("我是5000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        Callable callable2 = () -> {
            Thread.sleep(3000);
            return ("我是3000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        Callable callable3 = () -> {
            Thread.sleep(1000);
            return ("我是1000 Callable子线程 " + Thread.currentThread().getName() + " 产生的结果 ");
        };

        // 4.使用CompletionService提交三个任务到线程池
        completionService.submit(callable1);
        completionService.submit(callable2);
        completionService.submit(callable3);

        // 5.获取返回值
        log.info("开始获取结果 " + getStringDate());
        /*
         * poll()方法不会去等结果造成阻塞，没有结果则返回null，接着程序继续往下运行
         */
//        log.info(completionService.poll().get() + "" + getStringDate());
//        log.info(completionService.poll().get() + "" + getStringDate());
//        log.info(completionService.poll().get() + "" + getStringDate());
//        log.info(completionService.poll().get() + "" + getStringDate());

        //创建一个循环，连续调用poll()方法，每次隔1秒调用，没有结果则返回null
        for (int i = 0; i < 8; i++) {
            Future future = completionService.poll();
            if (future != null) {
                log.info(future.get() + "" + getStringDate());
            } else {
                log.info(null + " " + getStringDate());
            }
            Thread.sleep(1000);
        }
        log.info("结束 " + getStringDate());

        // 6.关闭线程池
        executorService.shutdown();

    }

}
