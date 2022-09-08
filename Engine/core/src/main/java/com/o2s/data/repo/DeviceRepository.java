package com.o2s.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.o2s.data.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>{
    
    List<Device> findByHostContaining(String name);

    @Query(value = "SELECT * FROM DEVICE WHERE ENV_ID = ?1", nativeQuery = true)
    List<Device> findAllByEnvId(Integer envId);

}
