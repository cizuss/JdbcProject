package ro.teamnet.zth.api.em;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 7/8/2016.
 */
public interface EntityManager {
    public<T> T update (T entity);
    public void delete (Object entity);
    public<T> List<T> findByParams(Class<T> enityClass, Map<String, Object> params);
    public <T> T findById(Class<T> entityClass, Long id);
    public Long getNextIdVal(String tableName, String columnIdName);
    public <T> Object insert (T entity);
    public <T> List<T> findAll(Class<T> entityClass);
}
