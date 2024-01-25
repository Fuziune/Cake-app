package TestCake;

import org.junit.jupiter.api.Test;
public class Tests {
    @Test
    public void testRun(){
        CakeTest caketest=new CakeTest();
        RepositoryTest repoTest=new RepositoryTest();
        CakeServiceTest cakeServTest=new CakeServiceTest();
        caketest.testCakeConstructor();
        repoTest.testCakeRepository();
        cakeServTest.testCakeService();

    }
}
