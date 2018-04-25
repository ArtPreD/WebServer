package database.dao;

import database.datasets.UsersDataSet;
import org.hibernate.Criteria;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) throws HibernateException {
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public UsersDataSet getUserByName(String name) throws HibernateException {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UsersDataSet> criteriaQuery = builder.createQuery(UsersDataSet.class);
        Root<UsersDataSet> root = criteriaQuery.from(UsersDataSet.class);
        criteriaQuery.select(root).where(builder.equal(root.get("name"), name));
        Query query = session.createQuery(criteriaQuery);

        return ((UsersDataSet) query.getSingleResult());
    }

    public long insertUser(String name, String password) throws HibernateException {
        return (Long) session.save(new UsersDataSet(name, password));
    }
}
