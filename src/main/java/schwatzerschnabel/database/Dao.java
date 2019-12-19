package schwatzerschnabel.database;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import schwatzerschnabel.database.entities.WordPair;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

public class Dao {
    private static StandardServiceRegistry serviceRegistry;
    private volatile static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory==null){
            createSessionFactory();
        }
        return sessionFactory;
    }


    private synchronized static void createSessionFactory() {
        if(sessionFactory!=null){return;}

        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }


    public static boolean insertWordPair(WordPair wordPair)  {
        boolean result = false;

        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(wordPair);
            session.flush();
            session.getTransaction().commit();

            result = true;
        }
        catch (Exception e)  {
            result = false;
        }
        finally  {
            session.clear();
            session.close();
        }

        return result;
    }

    public static void updateWordPair(WordPair pair)  {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        session.merge(pair);
        session.flush();
        session.getTransaction().commit();
        session.clear();
        session.close();
    }

    public static WordPair getWordPairByForeignWord(String foreignWord)  {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<WordPair> cr = cb.createQuery(WordPair.class);
        Root<WordPair> root = cr.from(WordPair.class);
        cr.select(root).where(cb.equal(root.get("foreignWord"), foreignWord));

        Query<WordPair> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<WordPair> result = query.getResultList();
        session.flush();
        session.clear();
        session.close();

        return result.get(0);
    }

    public static int getMaxId()  {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        Criteria c = session.createCriteria(WordPair.class);
        c.addOrder(Order.desc("id"));
        c.setMaxResults(1);

        int result = ((WordPair) c.uniqueResult()).getId();
        session.flush();
        session.close();

        return result;
    }

    public static List<WordPair> getWordPairsByAuthorId(String authorId, int limit, String pos) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        //Create an instance of CriteriaBuilder by calling the getCriteriaBuilder() method
        CriteriaBuilder cb = session.getCriteriaBuilder();
        //Create an instance of CriteriaQuery by calling the CriteriaBuilder createQuery() method
        CriteriaQuery<WordPair> cr = cb.createQuery(WordPair.class);
        Root<WordPair> root = cr.from(WordPair.class);
        cr.select(root).orderBy(cb.desc(root.get("id")));
        cr.select(root).where(cb.equal(root.get("authorId"), authorId));

        if (!pos.equals(""))  {
            cr.select(root).where(cb.equal(root.get("pos"), pos));
        }

        //Create an instance of Query by calling the Session createQuery() method
        Query<WordPair> query = session.createQuery(cr);

        if (limit > 0)  {
           // query.setFirstResult((start));
            query.setMaxResults(limit);
        }

        //Call the getResultList() method of the query object which gives us the results
        List<WordPair> result = query.getResultList();
        session.flush();
        session.clear();
        session.close();

        Collections.reverse(result);

        return result;
    }

    public static List<WordPair> getAllWordPairsByAuthorId(String authorId)  {
        return getWordPairsByAuthorId(authorId, 0, "");
    }
}
