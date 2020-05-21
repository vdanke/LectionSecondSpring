package org.step.lection.second.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.step");

        DataSource dataSource = context.getBean("dataSource", DataSource.class);

        System.out.println(dataSource);
        System.out.println(context.getEnvironment().getProperty("db.supername"));
    }
}
