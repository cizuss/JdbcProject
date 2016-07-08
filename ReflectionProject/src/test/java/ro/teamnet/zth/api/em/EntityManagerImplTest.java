package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Job;

import static org.junit.Assert.assertEquals;

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
    public void testFindAllMethod(){
        assertEquals("Huston, we have a problem!",27,new EntityManagerImpl().findAll(Department.class).size());
        assertEquals("Huston, we have another problem!",19,new EntityManagerImpl().findAll(Job.class).size());
        System.out.println(new EntityManagerImpl().findAll(Department.class));
    }
}
