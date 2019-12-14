package schwatzerschnabel.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import schwatzerschnabel.database.entities.WordPair;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Dao {
    public static List<WordPair> getWordPairsByAuthorId(String authorId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //Create an instance of CriteriaBuilder by calling the getCriteriaBuilder() method
        CriteriaBuilder cb = session.getCriteriaBuilder();
        //Create an instance of CriteriaQuery by calling the CriteriaBuilder createQuery() method
        CriteriaQuery<WordPair> cr = cb.createQuery(WordPair.class);
        Root<WordPair> root = cr.from(WordPair.class);
        cr.select(root).where(cb.equal(root.get("authorId"), authorId));
        //Create an instance of Query by calling the Session createQuery() method
        Query<WordPair> query = session.createQuery(cr);
        //Call the getResultList() method of the query object which gives us the results
        List<WordPair> result = query.getResultList();
        session.close();
        return result;
    }
}
