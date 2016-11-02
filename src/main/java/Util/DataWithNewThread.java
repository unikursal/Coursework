package Util;

import Controller.ControllerRoot;
import ObjectTable.ObjectData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28.09.2016.
 */
public class DataWithNewThread implements Runnable {
    private static Thread thread;
    private ArrayList<ObjectData> datas[];

    @Override
    public void run() {
        thread = Thread.currentThread();

        datas = new ArrayList[3];
        for(int i = 0; i < 3; i++)
            datas[i] = new ArrayList<>();

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == nul", null, null);
            return;
        }

        Session session = sessionFactory.openSession();

        String alias = "Judge".toLowerCase();
        String quer = "";
        if (ControllerRoot.defaultKodCourt != 0)
            quer = " where " + alias + ".kodCourt = " + ControllerRoot.defaultKodCourt;
        List<ObjectData> objectDataList = session.createQuery("select " + alias + " from Judge as " + alias + quer + " ORDER BY lName ASC").list();
        datas[0].addAll(objectDataList);

        alias = "Assessor".toLowerCase();
        quer = "";
        if(ControllerRoot.defaultKodCourt != 0)
            quer = " where " + alias + ".kodCourt = " + ControllerRoot.defaultKodCourt;
        objectDataList = session.createQuery("select " + alias +  " from Assessor as "  + alias + quer + " ORDER BY lName ASC").list();
        datas[1].addAll(objectDataList);

        alias = "CourtWork".toLowerCase();
        quer = "";
        if(ControllerRoot.defaultKodCourt != 0)
            quer = " where " + alias + ".kodCourt = " + ControllerRoot.defaultKodCourt;
        objectDataList = session.createQuery("select " + alias +  " from CourtWork as "  + alias + quer + " ORDER BY lName ASC").list();
        datas[2].addAll(objectDataList);


        session.close();
    }

    public ArrayList<ObjectData>[] getDatas() {
        try{
            thread.join();
        }catch(InterruptedException e){
            return null;
        }
        return datas;
    }
}
