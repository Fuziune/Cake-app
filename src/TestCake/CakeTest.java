package TestCake;

import domain.Cake;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class CakeTest {
    @Test
    public void testCakeConstructor(){
        Cake cake=new Cake(1,"Vanilla","big",120.0);
        assertEquals(1,cake.getId());
        assertEquals("Vanilla",cake.getFlavor());
        assertEquals("big",cake.getSize());
        assertEquals(120.0,cake.getPrice(),0.001);
    }
}
