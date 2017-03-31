package com.vifu.crms.service;/**
 * Created by mirag on 2017/2/16.
 */

import com.vifu.crms.model.AcctUser;

import java.util.List;

/**
 * @author TangBo
 * @create 2017-02-16 14:52
 **/
public interface UserService {

    AcctUser load(String id);

    AcctUser get(String id);

    List<AcctUser> findAll();

    void persist(AcctUser entity);

    String save(AcctUser entity);

    void saveOrUpdate(AcctUser entity);

    void delete(String id);

    void flush();
}
