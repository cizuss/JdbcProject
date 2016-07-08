package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        Field[] fields = entity.getDeclaredFields();
        List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
        for (Field field : fields) {
            Column annotation = (Column)field.getAnnotation(Column.class);
            ColumnInfo cinfo = new ColumnInfo();
            if (annotation != null) {
                cinfo.setId(false);
                cinfo.setDbName(annotation.name());
            }
            else {
                cinfo.setId(true);
                cinfo.setDbName("Id");
            }
            cinfo.setColumnName(field.getName());
            cinfo.setColumnType(field.getType());
            columnInfoList.add(cinfo);
        }
        return columnInfoList;
    }

    public static Object castFromSqlType(Object value, Class wantedType) {
        if (value instanceof BigDecimal) {
            if (wantedType.getSimpleName().equals("Integer"))
                return ((BigDecimal)value).intValue();
            if (wantedType.getSimpleName().equals("Long"))
                return ((BigDecimal)value).longValue();
            if (wantedType.getSimpleName().equals("Float"))
                return ((BigDecimal)value).floatValue();
            return ((BigDecimal)value).doubleValue();
        }
        return value;
    }

    public static List<Field> getFieldsByAnnotations(Class clazz, Class annotation) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = new ArrayList<Field>();
        for (Field field : fields) {
            Annotation fieldAnnotation = field.getAnnotation(annotation);
            if (fieldAnnotation != null)
                fieldList.add(field);
        }
        return fieldList;
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
