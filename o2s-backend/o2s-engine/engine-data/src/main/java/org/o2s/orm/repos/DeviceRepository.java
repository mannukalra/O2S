package org.o2s.orm.repos;

import java.util.List;

import org.o2s.orm.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.o2s.orm.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>{
    
    List<Device> findByHostContaining(String name);

    @Query(value = "SELECT device_alias FROM Device device_alias WHERE environment = ?1")
    List<Device> findAllByEnvironment(Environment environment);

}
