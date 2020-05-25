package org.step.lection.second.spring;

import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.hibernate.*;

import javax.sql.DataSource;

@PropertySource(value = "classpath:/db.properties")
public class Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.step");

        DataSource dataSource = context.getBean("devDataSource", DataSource.class);

        System.out.println(dataSource);
        System.out.println(context.getEnvironment().getProperty("db.url"));

        Configuration cfg = new Configuration();

        SessionFactory sessionFactory = cfg.configure().buildSessionFactory();

        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.beginTransaction();

        Query query = currentSession.createQuery("");

        currentSession.getTransaction().commit();

        currentSession.getTransaction().rollback();
    }
}
