package com.ahfdkun.repository.factory;

import com.ahfdkun.repository.ExpandJpaRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class ExpandJpaRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable> extends JpaRepositoryFactoryBean<R, T, ID> {

    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new ExpandJpaRepositoryFactory<T, ID>(entityManager);
    }


    private class ExpandJpaRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
        private final EntityManager entityManager;

        public ExpandJpaRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        protected SimpleJpaRepository<T, ID> getTargetRepository(RepositoryInformation information) {
            JpaEntityInformation<T, ID> entityInformation = (JpaEntityInformation<T, ID>) getEntityInformation(information.getDomainType());
            return new ExpandJpaRepositoryImpl<T, ID>(entityInformation, entityManager);
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return ExpandJpaRepositoryImpl.class;
        }
    }

}
