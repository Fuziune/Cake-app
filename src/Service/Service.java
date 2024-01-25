package Service;

import domain.Identifiable;

import java.util.Collection;

public interface Service<T extends Identifiable> {
    void createEntity(T entity);
    void updateEntity(T entity);
    void deleteEntity(long entityId, String entityName);
    Collection<T> getAllEntities(String entityName);

    T getEntityById(long entityId);
}
