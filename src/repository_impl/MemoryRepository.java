package repository_impl;

import domain.Identifiable;
import repository.GenericRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
public class   MemoryRepository<T extends Identifiable> implements GenericRepository<T>, Serializable {
    protected Map<Long, T> repository = new HashMap<>();
    private long nextIdo = 2;
    private long nextIdc=6;

    @Override
    public T findById(long id) {
        if (!repository.containsKey(id)) {
            throw new NoSuchElementException("Object with ID " + id + " not found");
        }
        return repository.get(id);
    }

    @Override
    public void save(T entity) {
        if (entity.getId() == 0 && !entity.getname().equals("Cake")) {
            entity.setId(nextIdo++);
        }
        else if(entity.getId() == 0 && entity.getname().equals("Cake")){entity.setId(nextIdc++);}
        repository.put(entity.getId(), entity);

    }

    @Override
        public void delete(long id) {
            if (!repository.containsKey(id)) {
                throw new NoSuchElementException("Object with ID " + id + " not found");
            }

            repository.remove(id);
        }


    @Override
    public Collection<T> getAll() {
        return (Collection<T>) repository.values();
    }

    @Override
    public String toString() {
        return "MemoryRepository{" +
                "repository=" + repository +
                ", nextId=" + nextIdo +
                '}';
    }
}
