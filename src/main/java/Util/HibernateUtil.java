package Util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by User on 26.07.2016.
 */
public class HibernateUtil implements Runnable{
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static Thread th;

    public void run(){
        th = Thread.currentThread();
        Configuration conf = new Configuration().configure();
        serviceRegistry = new ServiceRegistryBuilder().applySettings(conf.getProperties()).buildServiceRegistry();
        sessionFactory = conf.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        try{
            th.join();
        }catch (InterruptedException e){
            return null;
        }
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        try{
            th.join();
        }catch (InterruptedException e){
            return;
        }
        if(sessionFactory != null)
            sessionFactory.close();
    }
}
