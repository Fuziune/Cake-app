package service_impl;

import Service.Service;
import domain.Identifiable;
import repository_impl.FileRepository;

import java.util.Collection;

public class GenericServiceFile <T extends Identifiable> implements Service<T> {

    private final FileRepository<T> repository1;

    public GenericServiceFile(FileRepository<T> repository1) {
        this.repository1 = repository1;
    }


    public void createEntity(T entity) {
        /*if (entity.getId() != 0) {
            throw new IllegalArgumentException("New entities must have an ID of 0.");
        }*/
        repository1.addElem(entity);
    }

    public void updateEntity(T entity) {
        if (entity.getId() == 0) {
            throw new IllegalArgumentException("Entity ID must not be 0 for updates.");
        }
        T existingEntity = repository1.findById(entity.getId());
        if (existingEntity == null) {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " does not exist.");
        }
        repository1.addElem(entity);
    }

    public void deleteEntity(long entityId, String entityName) {
        T entity = repository1.findById(entityId);
        System.out.println(entity);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + entityId + " does not exist.");
        }
        repository1.deleteElem(entityId);
    }
    public Collection<T> getAllEntities(String entityName) {
        return repository1.getAll();
    }

    public T getEntityById(long entityId) {
        T entity= repository1.findById(entityId);
        System.out.println(entity);
        if(entity==null){throw new IllegalArgumentException("Entity with ID " + entityId + " does not exist.");}
        return entity;
    }

}
