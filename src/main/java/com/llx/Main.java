package com.llx;

import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Lilx
 * @since 2016
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Thread.currentThread().sleep(20000);
        System.out.println("end");
    }
}
