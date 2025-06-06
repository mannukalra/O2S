package org.o2s.orm;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.o2s.orm.entity.Device;
import org.o2s.orm.entity.Environment;
import org.o2s.orm.repos.DeviceRepository;

@ApplicationScoped
public class DataResource {

    @Inject
    EntityManager entityManager;

    @Inject
    DeviceRepository deviceRepository;

    public List<Fruit> get() {
        return entityManager.createNamedQuery("Fruits.findAll", Fruit.class)
                .getResultList();
    }

    public Fruit getSingle(Integer id) {
        Fruit entity = entityManager.find(Fruit.class, id);
        if (entity == null) {
            throw new RuntimeException("Fruit with id of " + id + " does not exist.");
        }
        return entity;
    }

    @Transactional
    public Fruit create(Fruit fruit) {
        if (fruit.getId() != null) {
            throw new RuntimeException("Id was invalidly set on request.");
        }

        entityManager.persist(fruit);
        return fruit;
    }

    @Transactional
    public Fruit update(Integer id, Fruit fruit) {
        if (fruit.getName() == null) {
            throw new RuntimeException("Fruit Name was not set on request.");
        }

        Fruit entity = entityManager.find(Fruit.class, id);

        if (entity == null) {
            throw new RuntimeException("Fruit with id of " + id + " does not exist.");
        }

        entity.setName(fruit.getName());

        return entity;
    }

    @Transactional
    public void delete(Integer id) {
        Fruit entity = entityManager.getReference(Fruit.class, id);
        if (entity == null) {
            throw new RuntimeException("Fruit with id of " + id + " does not exist.");
        }
        entityManager.remove(entity);
    }

    public Device getDevice(Integer deviceId) {
        return Optional.ofNullable(deviceRepository.findById(deviceId)).orElseThrow().get();
    }

    public List<Device> getDevices(Integer envId) {
        return deviceRepository.findAllByEnvironment(Environment.builder().id(envId).build());
    }
}
