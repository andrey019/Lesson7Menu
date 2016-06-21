import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            for (Object object : objects) {
                session.delete(object);
            }
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
        }
        return false;
    }

    @Override
    public List getAllEntities(Class objectClass) {
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(objectClass);
            List result = criteria.list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Object getEntityById(Class objectClass, long id) {
        Session session = sessionFactory.openSession();
        try {
            Object entity = session.get(objectClass, id);
            session.close();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
        return null;
    }

    @Override
    public List getEntityByName(Class objectClass, String name) {
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(objectClass);
            criteria.add(Restrictions.eq("name", name));
            List result = criteria.list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
        return null;
    }

    @Override
    public List getEntitiesByPrice(Class objectClass, double from, double to) {
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(objectClass);
            criteria.add(Restrictions.between("price", from, to));
            List result = criteria.list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
        return null;
    }

    @Override
    public List getEntitiesWithDiscount(Class objectClass) {
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(objectClass);
            criteria.add(Restrictions.gt("discount", 0));
            List result = criteria.list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
        return null;
    }

    @Override
    public List randomEntitiesByWeight(Class objectClass, int maxWeight) {
        try {
            int totalWeight = 0;
            List entities = getWithWeightLowerThan(objectClass, maxWeight);
            if ( (entities == null) || (entities.isEmpty()) ) {
                return null;
            }
            List result = new ArrayList<>();
            Menu menu = (Menu) entities.get(ThreadLocalRandom.current().nextInt(0, entities.size()));
            do {
                if (!result.contains(menu)) {
                    result.add(menu);
                    totalWeight += menu.getWeight();
                }
                menu = (Menu) entities.get(ThreadLocalRandom.current().nextInt(0, entities.size()));
            } while ( ((totalWeight + menu.getWeight()) < maxWeight) && (entities.size() != result.size()) );
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List getWithWeightLowerThan(Class objectClass, int maxWeight) {
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(objectClass);
            criteria.add(Restrictions.lt("weight", maxWeight));
            List result = criteria.list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
        return null;
    }

    @Override
    public int getRowCount(Class objectClass) {
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(objectClass);
            criteria.setProjection(Projections.rowCount());
            int result = (int) criteria.uniqueResult();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
        return 0;
    }
}
