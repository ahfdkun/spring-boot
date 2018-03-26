package com.ahfdkun.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;

public class ExpandJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExpandJpaRepository<T, ID> {

    private final EntityManager entityManager;

    public ExpandJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public T findOne(String name) {
        T result = null;
        try {
            Query query = entityManager.createQuery("from User t where t.name= :name");
            query.setParameter("name", name);
            result = (T) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return result;

    }

}
