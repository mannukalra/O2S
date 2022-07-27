package com.o2s.db.repo;


import com.o2s.db.model.Environment;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Integer>{
    
    List<Environment> findByNameContaining(String name);
}


// @Repository
// public class EnvironmentRepository {
 
//     @PersistenceContext
//     private EntityManager entityManager;
 
//     @Transactional
//     public void save(Environment environment) {
//         entityManager.persist(environment);
//     }
// }