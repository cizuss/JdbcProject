package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImpl {
    public static <T> T findById(Class<T> entityClass, Long id) {
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
                    System.out.println("column name is " + cinfo.getColumnName());
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

    public <T> Object insert(T entity){
        String tableName = EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> cList = EntityUtils.getColumns(entity.getClass());
        QueryBuilder qb = new QueryBuilder();
        qb.setTableName(tableName);
        qb.addQueryColumns(cList);
        qb.setQueryType(QueryType.INSERT);
        T instance = null;
        for (ColumnInfo columnInfo : cList) {
            if (columnInfo.isId())
                columnInfo.setValue(getNextIdVal(tableName,columnInfo.getDbName()));
            else {
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
        try (Connection connection=DBManager.getConnection(); Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(queryString);
            instance = (T) entity.getClass().newInstance();
            for (ColumnInfo cinfo : cList) {
                Field field = instance.getClass().getDeclaredField(cinfo.getColumnName());
                field.setAccessible(true);
                field.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(cinfo.getDbName()), cinfo.getColumnType()));
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
    }
}
