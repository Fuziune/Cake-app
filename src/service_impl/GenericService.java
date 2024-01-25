package service_impl;

import Service.Service;
import Settings.Settings;
import Validators.CakeValidator;
import  Validators.OrderValidator;
import  domain.Cake;
import domain.Identifiable;
import domain.Order;
import repository.GenericRepository;

import java.util.Collection;

public class GenericService<T extends Identifiable> implements Service<T> {
    private final GenericRepository<T> repository;
    //private final FileRepository<T> repository1;
    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public void createEntity(T entity) {
//       if (entity.getId() != 0) {
//            throw new IllegalArgumentException("New entities must have an ID of 0.");
//        }
       if(entity.getname()=="Cake"){
           CakeValidator.validate((Cake) entity);
       }
        if(entity.getname()=="Order"){
            OrderValidator.validate((Order) entity);
        }
        repository.save(entity);
    }

    public void updateEntity(T entity) {
        Settings settings=new Settings();
        if (entity.getId() == 0) {
            throw new IllegalArgumentException("Entity ID must not be 0 for updates.");
        }
        T existingEntity = repository.findById(entity.getId());
        if (existingEntity == null) {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " does not exist.");
        }

        repository.save(entity);
    }

    public void deleteEntity(long entityId, String entityName) {
        T entity = repository.findById(entityId);
        System.out.println(entity);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + entityId + " does not exist.");
        }
        repository.delete(entityId);
    }
    public Collection<T> getAllEntities(String entityName) {
        return repository. getAll();
    }

    public T getEntityById(long entityId) {
        T entity=repository.findById(entityId);
        //System.out.println(entity);
        if(entity==null){throw new IllegalArgumentException("Entity with ID " + entityId + " does not exist.");}
        return entity;
    }

}
