package org.o2s.orm.repos;


import org.o2s.orm.entity.Environment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Integer>{
    
    List<Environment> findByNameContaining(String name);
}
