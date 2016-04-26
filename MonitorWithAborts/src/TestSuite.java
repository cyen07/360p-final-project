

import static org.junit.Assert.*;
import org.junit.Test;

public class TestSuite {
    @Test
    public void Test1A(){
	TestObjectDriver obj1 = new TestObjectDriver(5, 6);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	long start = System.nanoTime();
	obj1.test1A(monitor1);
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Test1A: " + difference + "ns");
	assertTrue(obj1.var1 == 6);
    }
    
    @Test
    public void Test2B(){
	TestObjectDriver obj1 = new TestObjectDriver(5, 6);
	long start = System.nanoTime();
	obj1.test1B();
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Test1B: " + difference + "ns");
	assertTrue(obj1.var1 == 6);
    }
}
