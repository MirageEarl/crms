package com.vifu.crms.dao.impl;/**
 * Created by mirag on 2017/2/16.
 */

import com.vifu.crms.dao.UserDao;
import com.vifu.crms.model.AcctUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author TangBo
 * @create 2017-02-16 14:41
 **/
@Repository("userDao")
public class UserDaoImpl implements UserDao{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public AcctUser load(String id) {
        return (AcctUser) this.getCurrentSession().load(AcctUser.class, id);
    }

    @Override
    public AcctUser get(String id) {
        return (AcctUser) this.getCurrentSession().get(AcctUser.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AcctUser> findAll() {
        List<AcctUser> acctUsers = this.getCurrentSession().createQuery("from AcctUser").setCacheable(true).list();
        return acctUsers;
    }

    @Override
    public void persist(AcctUser entity) {
        this.getCurrentSession().persist(entity);

    }

    @Override
    public String save(AcctUser entity) {
        return (String) this.getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(AcctUser entity) {
        this.getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(String id) {
        AcctUser entity = this.load(id);
        this.getCurrentSession().delete(entity);
    }

    @Override
    public void flush() {
        this.getCurrentSession().flush();

    }
}
