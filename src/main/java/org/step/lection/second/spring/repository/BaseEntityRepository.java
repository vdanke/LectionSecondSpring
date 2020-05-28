package org.step.lection.second.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.step.lection.second.spring.model.AbstractBaseEntity;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface BaseEntityRepository<T extends AbstractBaseEntity> extends Repository<T, UUID> {

    @Query("select n from #{#entityName} n where n.attribute = ?1")
    List<T> findAllByAttribute(String attribute);
}
