package TestCake;

import domain.Cake;
import repository_impl.MemoryRepository;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class RepositoryTest {
    @Test
    public void testCakeRepository(){
        Cake cake1=new Cake(1,"sugar","big",130);
        Cake cake2=new Cake(2,"caramel","small",80);
        Cake cake3=new Cake(3,"vanilla","medium",100);
        MemoryRepository<Cake> repo=new MemoryRepository<>();
        repo.save(cake1);
        repo.save(cake2);
        repo.save(cake3);
        Collection<Cake> list1= repo.getAll();

        assertEquals(repo.findById(2),cake2);
        assertEquals(repo.findById(1),cake1);
        assertEquals(repo.findById(3),cake3);
        assertEquals(list1.size(),3);

        repo.delete(2);
        Collection<Cake> list2= repo.getAll();
        assertEquals(list1.size(),list2.size());
        try {
            repo.findById(2);
            assert false : "Expected exception not thrown";
        } catch (Exception e) {
            System.out.println("Exception caught: " + ("Object with ID " + 2 + " not found"));
        }
    }
}
