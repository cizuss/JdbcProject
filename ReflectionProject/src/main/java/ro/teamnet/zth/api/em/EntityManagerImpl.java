package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImpl implements EntityManager {
    public <T> T findById(Class<T> entityClass, Long id) {
        String tableName = EntityUtils.getTableName(entityClass);
        List<ColumnInfo> columnList = EntityUtils.getColumns(entityClass);

        List<Field> idFieldList = EntityUtils.getFieldsByAnnotations(entityClass, Id.class);

        String fieldName = idFieldList.get(0).getAnnotation(Id.class).name();

        Condition condition = new Condition(fieldName, id);
        QueryBuilder qb = new QueryBuilder();
        qb.setQueryType(QueryType.SELECT);
        qb.setTableName(tableName);
        qb.addQueryColumns(columnList);
        qb.addCondition(condition);
        String queryString = qb.createQuery();
        System.out.println(queryString);
        T instance = null;
        try (Connection conn = DBManager.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(queryString);
            ResultSet result = stmt.getResultSet();
            instance = entityClass.newInstance();
            if (result.next()) {
                for (ColumnInfo cinfo : columnList) {
                    Field field = instance.getClass().getDeclaredField(cinfo.getColumnName());
                    field.setAccessible(true);
                    String columnName = cinfo.getDbName();
<<<<<<< HEAD
                    System.out.println("Wanted Type: " + cinfo.getColumnType());
=======
>>>>>>> 16963f8ed847bcb413fd460ead03cfcc289435f3
                    field.set(instance, EntityUtils.castFromSqlType(result.getObject(columnName), cinfo.getColumnType()));
                }
            }
            return instance;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return instance;
    }

<<<<<<< HEAD
    public <T> Object insert(T entity) {
=======
    public <T> Object insert(T entity){
>>>>>>> 16963f8ed847bcb413fd460ead03cfcc289435f3
        String tableName = EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> cList = EntityUtils.getColumns(entity.getClass());
        QueryBuilder qb = new QueryBuilder();
        qb.setTableName(tableName);
        qb.addQueryColumns(cList);
        qb.setQueryType(QueryType.INSERT);
        long nextId = 1;
        for (ColumnInfo columnInfo : cList) {
            if (columnInfo.isId()) {
<<<<<<< HEAD
                nextId = getNextIdVal(tableName, columnInfo.getDbName());
                columnInfo.setValue(nextId);
            } else {
=======
                nextId =  getNextIdVal(tableName, columnInfo.getDbName());
                columnInfo.setValue(nextId);
            }
            else {
>>>>>>> 16963f8ed847bcb413fd460ead03cfcc289435f3
                try {
                    Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    columnInfo.setValue(field.get(entity));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        String queryString = qb.createQuery();
        System.out.println(queryString);
<<<<<<< HEAD
        try (Connection connection = DBManager.getConnection(); Statement stmt = connection.createStatement()) {
=======
        try (Connection connection=DBManager.getConnection(); Statement stmt = connection.createStatement()) {
>>>>>>> 16963f8ed847bcb413fd460ead03cfcc289435f3
            stmt.execute(queryString);
            return findById(entity.getClass(), nextId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> findAll(Class<T> entityTest) {
<<<<<<< HEAD
        Connection connection = DBManager.getConnection();
        String tableName = EntityUtils.getTableName(entityTest);
        List<ColumnInfo> columns = EntityUtils.getColumns(entityTest);

        QueryBuilder query = new QueryBuilder().setTableName(tableName);
        query.addQueryColumns(columns);
        query.setQueryType(QueryType.SELECT);

        String sqlQuery = query.createQuery();

        try {
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(sqlQuery);
            ArrayList<T> list = new ArrayList<T>();

            while (resultSet.next()) {
                T instance = entityTest.newInstance();
                for (ColumnInfo cInfo : columns) {
                    Field field = instance.getClass().getDeclaredField(cInfo.getColumnName());
                    field.setAccessible(true);
                    field.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(cInfo.getDbName()), cInfo.getColumnType()));
=======
        Connection connection= DBManager.getConnection();
        String tableName=EntityUtils.getTableName(entityTest);
        List<ColumnInfo> columns=EntityUtils.getColumns(entityTest);

        QueryBuilder query=new QueryBuilder().setTableName(tableName);
        query.addQueryColumns(columns);
        query.setQueryType(QueryType.SELECT);

        String sqlQuery=query.createQuery();

        try {
            Statement stm=connection.createStatement();
            ResultSet resultSet=stm.executeQuery(sqlQuery);
            ArrayList<T> list=new ArrayList<T>();

            while(resultSet.next()){
                T instance=entityTest.newInstance();
                for(ColumnInfo cInfo:columns){
                    Field field=instance.getClass().getDeclaredField(cInfo.getColumnName());
                    field.setAccessible(true);
                    field.set(instance,EntityUtils.castFromSqlType(resultSet.getObject(cInfo.getDbName()),cInfo.getColumnType()));
>>>>>>> 16963f8ed847bcb413fd460ead03cfcc289435f3
                }
                list.add(instance);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long getNextIdVal(String tableName, String columnIdName) {
        try (Connection conn = DBManager.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("select max(" + columnIdName + ") from " + tableName);
            if (resultSet.next()) {
                return resultSet.getLong(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
        return (long) 1;
    }

    public <T> List<T> findByParams(Class<T> entityClass, Map<String, Object> params) {
        String tableName = EntityUtils.getTableName(entityClass);
        List<ColumnInfo> columnList = EntityUtils.getColumns(entityClass);
        QueryBuilder qb = new QueryBuilder();
        qb.setQueryType(QueryType.SELECT);
        qb.setTableName(tableName);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            qb.addCondition(new Condition(entry.getKey(), entry.getValue()));
        }
        qb.addQueryColumns(columnList);
        String queryString = qb.createQuery();
        try (Connection conn = DBManager.getConnection(); Statement stmt = conn.createStatement()) {
            List<T> objectsFound = new ArrayList<T>();
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                T instance = entityClass.newInstance();
                for (ColumnInfo cinfo : columnList) {
                    Field field = instance.getClass().getDeclaredField(cinfo.getColumnName());
                    field.setAccessible(true);
                    field.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(cinfo.getDbName()), cinfo.getColumnType()));
                }
                objectsFound.add(instance);
            }
            return objectsFound;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Object entity) {
        Connection connection = DBManager.getConnection();
        String tableName = EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> columns = EntityUtils.getColumns(entity.getClass());
        QueryBuilder qb = new QueryBuilder();
        qb.setQueryType(QueryType.DELETE);
        qb.setTableName(tableName);
        qb.addQueryColumns(columns);
        String queryString;
        String idFieldName = null;
        Object idFieldValue = null;
        for (ColumnInfo columnInfo : columns) {
            try {
                Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                field.setAccessible(true);
                columnInfo.setValue(field.get(entity));
                if (columnInfo.isId()) {
                    idFieldName = columnInfo.getDbName();
                    idFieldValue = columnInfo.getValue();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        Condition condition = null;
        try {
            condition = new Condition(idFieldName, idFieldValue);
            qb.addCondition(condition);
            queryString = qb.createQuery();
            Statement statement = connection.createStatement();
            statement.execute(queryString);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public <T> T update(T entity){
        //Connection connection=DBManager.getConnection();
        String tableName=EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> tableColumns=EntityUtils.getColumns(entity.getClass());

        for(ColumnInfo cInfo:tableColumns){
            try {
                Field field=entity.getClass().getDeclaredField(cInfo.getColumnName());
                field.setAccessible(true);
                cInfo.setValue(field.get(entity));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        QueryBuilder queryBuilder=new QueryBuilder();

        queryBuilder.setTableName(tableName);
        queryBuilder.setQueryType(QueryType.UPDATE);
        queryBuilder.addQueryColumns(tableColumns);

        Long returnId=null;
        for(ColumnInfo cInfo:tableColumns){
            if(cInfo.isId()){
                returnId=(Long)cInfo.getValue();
                queryBuilder.addCondition(new Condition(cInfo.getDbName(),cInfo.getValue()));
                break;
            }
        }

        String queryStatement=queryBuilder.createQuery();
        System.out.println(queryStatement);
        try (Connection connection=DBManager.getConnection(); Statement stm = connection.createStatement()){
            stm.executeQuery(queryStatement);
            //return (T) resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findById((Class<T>) entity.getClass(),returnId);

=======
        return (long)1;
>>>>>>> 16963f8ed847bcb413fd460ead03cfcc289435f3
    }
}