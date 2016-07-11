package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 7/11/2016.
 */
public class DepartmentDao {
    EntityManager entityManager = new EntityManagerImpl();
    public<T> T callFindById(Class<T> entityClass, Long id) {
        return entityManager.findById(entityClass, id);
    }
    public<T> T callInsert(T entity) {
        return (T) entityManager.insert(entity);
    }
    public<T> List<T> callFindAll(Class<T> entity) {
        return entityManager.findAll(entity);
    }
    public Long callGetNextIdVal(String tableName, String columnIdName) {
        return entityManager.getNextIdVal(tableName, columnIdName);
    }
    public<T> List<T> CallFindByParams(Class<T> entityClass, Map<String, Object> params) {
        return entityManager.findByParams(entityClass, params);
    }
}
