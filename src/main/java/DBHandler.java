import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBHandler implements DBInterface {
    private SessionFactory sessionFactory;

    public DBHandler() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public boolean addEntityToDB(Object... objects) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            for (Object object : objects) {
                session.save(object);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    @Override
    public boolean deleteEntityFromBD(Object... objects) {
        return false;
    }

    @Override
    public Object getAllEntities(Object object) {
        return null;
    }

    @Override
    public Object getEntityById(Object object) {
        return null;
    }

    @Override
    public List getEntitiesByPrice(Object object, double from, double to) {
        return null;
    }

    @Override
    public List getEntitiesWithDiscount(Object object) {
        return null;
    }

    @Override
    public List randomEntitiesByWeight(Object object, int maxWeight) {
        return null;
    }
}
