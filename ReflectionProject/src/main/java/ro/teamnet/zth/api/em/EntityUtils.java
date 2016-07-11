package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 7/7/2016.
 */
public class EntityUtils {
    private EntityUtils() throws UnsupportedOperationException {

    }
    public static String getTableName(Class entity) {
        Table anotation = (Table)entity.getAnnotation(Table.class);
        if (anotation != null)
            return anotation.name();
        return entity.getSimpleName();

    }

    public static List<ColumnInfo> getColumns(Class entity) {
        List<ColumnInfo> columns = new ArrayList<>();
        Field[] fields = entity.getDeclaredFields();
        for(Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(field.getName());
            columnInfo.setColumnType(field.getType());
            if(column != null) {
                columnInfo.setDbName(column.name());
            } else {
                Id id = field.getAnnotation(Id.class);
                columnInfo.setDbName(id.name());
                columnInfo.setId(true);
            }
            columns.add(columnInfo);
        }
        return columns;
    }

    public static Object castFromSqlType(Object value, Class wantedType) {
        if(value != null) {
            if (value instanceof java.sql.Timestamp)
                return new java.sql.Date(((java.sql.Timestamp)value).getTime());
            if(value instanceof BigDecimal) {
                BigDecimal bdValue = (BigDecimal) value;
                return wantedType.equals(Integer.class) ? bdValue.intValue() :
                        wantedType.equals(Long.class) ? bdValue.longValue() :
                                wantedType.equals(Float.class) ? bdValue.floatValue() :
                                        wantedType.equals(Double.class) ? bdValue.doubleValue() : value;
            } else {
                return value;
            }
        }
        return null;
    }

    public static List<Field> getFieldsByAnnotations(Class clazz, Class annotation) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field declaredField : declaredFields) {
            if(declaredField.getAnnotation(annotation) != null) {
                fields.add(declaredField);
            }
        }
        return fields;
    }

    public static Object getSqlValue(Object object) {
        Table anotation = object.getClass().getAnnotation(Table.class);
        if (anotation != null) {
            List<Field> fields = getFieldsByAnnotations(object.getClass(), Id.class);
            fields.get(0).setAccessible(true);
            try {
                return fields.get(0).get(object);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
