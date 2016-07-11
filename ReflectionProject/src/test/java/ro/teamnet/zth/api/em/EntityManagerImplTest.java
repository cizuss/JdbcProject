package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.List;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImplTest {
    @Test
    public void testFindById() {
        Employee e = new EntityManagerImpl().findById(Employee.class, (long)193);
        System.out.println(e);
    }
    @Test
    public void testNextId() {
        //assert (new EntityManagerImpl().getNextIdVal("departments", "department_id") == 272);
        System.out.print(new EntityManagerImpl().getNextIdVal("departments", "department_id"));
    }
    @Test
    public void testInsert() {
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("dummydepname");
        newDepartment.setLocation(555);
        Department entity = (Department) new EntityManagerImpl().insert(newDepartment);
        assert (entity.getDepartmentName().equals("dummydepname") && entity.getLocation() == 555);
    }
    @Test
    public void testFindAll() {
        List<Department> departmentList =  new EntityManagerImpl().findAll(Department.class);
        assert departmentList.size() == 30;
    }
    @Test
    public void testFindByParams() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("location_id",1700);
        List<Department> objectsFound = new EntityManagerImpl().findByParams(Department.class, paramMap);
        assert objectsFound.size() == 21;
    }
    @Test
    public void testDelete() {
        Employee emp = new EntityManagerImpl().findById(Employee.class, (long) 206);
        new EntityManagerImpl().delete(emp);
    }
    @Test
    public void testUpdate() {
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("dummydepname");
        newDepartment.setLocation(555);
        Department entity = (Department) new EntityManagerImpl().insert(newDepartment);
        entity.setDepartmentName("otherDepName");
        entity = new EntityManagerImpl().update(entity);
        System.out.println(entity);
    }
    @Test
    public void testNextId() {
        assert (new EntityManagerImpl().getNextIdVal("departments", "department_id") == 271);
    }
    @Test
    public void testInsert() {
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("dummydepname");
        newDepartment.setLocation(555);
        Department entity = (Department) new EntityManagerImpl().insert(newDepartment);
        assert (entity.getDepartmentName().equals("dummydepname") && entity.getLocation() == 555);
    }
    @Test
    public void testFindAll() {
        List<Department> departmentList =  new EntityManagerImpl().findAll(Department.class);
        assert departmentList.size() == 28;
    }
}
