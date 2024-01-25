package TestCake;

import domain.Cake;
import repository.GenericRepository;
import repository_impl.MemoryRepository;
import service_impl.GenericService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CakeServiceTest {

    @Test
    public void testCakeService() {
        GenericRepository<Cake> cakeRepository = new MemoryRepository<>();
        GenericService<Cake> cakeService = new GenericService<>(cakeRepository);

        Cake cake1 = new Cake(0, "Chocolate", "Large", 20.99);
        Cake cake2 = new Cake(0, "Vanilla", "Medium", 15.99);

        cakeService.createEntity(cake1);
        cakeService.createEntity(cake2);

        assertEquals(2, cakeService.getAllEntities("cake").size());
        assertEquals(cake1, cakeService.getEntityById(6));
        assertEquals(cake2, cakeService.getEntityById(7));

        cakeService.deleteEntity(6,"cake");
        assertEquals(1,cakeService.getAllEntities("cake").size());
        //System.out.println(cakeService.getAllEntities("cake"));

    }
}
