package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.appl.domain.Department;

import java.util.List;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImplTest {
    @Test
    public void testFindById() {
        Department d = EntityManagerImpl.findById(Department.class, (long) 30);
        System.out.println(d);
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
