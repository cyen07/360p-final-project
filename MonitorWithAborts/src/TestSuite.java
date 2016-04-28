

import static org.junit.Assert.*;
import org.junit.Test;

public class TestSuite {
    @Test
    public void Test1A(){
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	long start = System.nanoTime();
	obj1.test1A(monitor1);
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Test1A: " + difference + "ns");
	assertTrue((obj1.var1 == 1) && (obj1.var2 == 11));
    }
    
    @Test
    public void Test1B(){
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	long start = System.nanoTime();
	obj1.test1B();
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Test1B: " + difference + "ns");
	assertTrue((obj1.var1 == 1) && (obj1.var2 == 11));
    }
    
    @Test
    public void Test2A(){
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	long start = System.nanoTime();
	obj1.test2A(monitor1);
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Test2A: " + difference + "ns");
	assertTrue((obj1.var1 == 0) && (obj1.var2 == 10));
    }
    
    @Test
    public void Test3A(){
	TestObjectDriver obj1 = new TestObjectDriver(0,10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	Runnable thread1 = new Runnable(){

	    @Override
	    public void run() {
		monitor1.synchronize();
		obj1.increase();
		try{
		    Thread.sleep(1000);
		}
		catch(InterruptedException e){
		    e.printStackTrace();
		}
		monitor1.abortNotify();
		monitor1.release();
	    }
	};
	Runnable thread2 = new Runnable(){
	    @Override
	    public void run(){
		monitor1.synchronize();
		try{
        		obj1.increase();
        		obj1.increase();
        		try{
        		    Thread.sleep(100);
        		}
        		catch(InterruptedException e){
        		    e.printStackTrace();
        		}
        		throw new Exception();
		}
		catch(Exception e){
		    monitor1.abort();
		}
	    }
	};
	long start = System.currentTimeMillis();
	thread1.run();
	thread2.run();
	long end = System.currentTimeMillis();
	long difference = end - start;
	System.out.println("Execution Time of Test3A: " + difference + "ms");
	assertTrue((obj1.var1 == 1) && (obj1.var2 == 11));
    }
}
