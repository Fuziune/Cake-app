package repository;

import domain.Identifiable;

import java.util.Collection;
public interface GenericRepository<T extends Identifiable>{
    T findById(long id);
    void save(T entity);
    void delete(long id);
    Collection<T> getAll();
    //void update(T entity);

    //String getname();

}
