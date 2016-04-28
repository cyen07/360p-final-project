
import static org.junit.Assert.*;

import org.junit.Test;

public class TestSuite {

    @Test
    public void Test1A() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	long start = System.nanoTime();
	obj1.test1A(monitor1);
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Monitor with Aborts (not aborted): " + difference + "ns");
	assertTrue((obj1.var1 == 1) && (obj1.var2 == 11));
	assertTrue(true);
    }

    @Test
    public void Test1B() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	long start = System.nanoTime();
	obj1.test1B();
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Synchronized Monitor: " + difference + "ns");
	assertTrue((obj1.var1 == 1) && (obj1.var2 == 11));
    }

    @Test
    public void Test2A() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	long start = System.nanoTime();
	obj1.test2A(monitor1);
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Monitor with Aborts (exception aborted): " + difference + "ns");
	assertTrue((obj1.var1 == 0) && (obj1.var2 == 10));
    }

    @Test
    public void Test2B() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	long start = System.nanoTime();
	obj1.test2B();
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Synchronized Monitor (exception caught): " + difference + "ns");
	assertTrue((obj1.var1 == 1) && (obj1.var2 == 11));
    }

    @Test
    public void Test3A() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	long start = System.nanoTime();
	for (int i = 0; i < 1000; i++) {
	    obj1.test1A(monitor1);
	}
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Monitor with Aborts x1000 (not aborted): " + difference + "ns");
	assertTrue((obj1.var1 == 1000) && (obj1.var2 == 1010));
    }

    @Test
    public void Test3B() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	long start = System.nanoTime();
	for (int i = 0; i < 1000; i++) {
	    obj1.test1B();
	}
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Synchronized Monitor x1000: " + difference + "ns");
	assertTrue((obj1.var1 == 1000) && (obj1.var2 == 1010));
    }

    @Test
    public void Test4A() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	long start = System.nanoTime();
	for (int i = 0; i < 1000; i++) {
	    obj1.test2A(monitor1);
	}
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Monitor with Aborts x1000 (exception aborted): " + difference + "ns");
	assertTrue((obj1.var1 == 0) && (obj1.var2 == 10));
    }
    
    @Test
    public void Test4B() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	long start = System.nanoTime();
	for (int i = 0; i < 1000; i++) {
	    obj1.test2B();
	}
	long done = System.nanoTime();
	long difference = done - start;
	System.out.println("Execution Time of Synchronized Monitor x1000 (exception caught): " + difference + "ns");
	assertTrue((obj1.var1 == 1000) && (obj1.var2 == 1010));
    }

    @Test
    public void Test5A() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	Thread thread1 = new Thread() {

	    @Override
	    public void run() {
		monitor1.synchronize();
		obj1.increase();
		for (int i = 0; i < 50; i++) {
		    System.out.println("Thread 1 count " + i + " ++");
		}
		try {
		    Thread.sleep(200);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		monitor1.abortNotify();
		monitor1.release();
	    }
	};
	Thread thread2 = new Thread() {

	    @Override
	    public void run() {
		monitor1.synchronize();
		obj1.increase();
		for (int i = 0; i < 50; i++) {
		    System.out.println("Thread 2 count " + i + " --");
		}
		try {
		    Thread.sleep(200);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		monitor1.abortNotify();
		monitor1.release();

	    }
	};
	Thread thread3 = new Thread() {

	    @Override
	    public void run() {
		for (int i = 0; i < 50; i++) {
		    System.out.println("Thread 3 count " + i);
		}
		try {
		    Thread.sleep(100);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	};
	long start = System.currentTimeMillis();
	thread1.start();
	thread2.start();
	thread3.start();
	try {
	    thread1.join();
	    thread2.join();
	    thread3.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	long end = System.currentTimeMillis();
	long difference = end - start;
	System.out.println("Expected Time of Test ~400ms: " + difference + "ms");
	assertTrue((obj1.var1 == 2) && (obj1.var2 == 12));
    }

    @Test
    public void Test6A() {
	TestObjectDriver obj1 = new TestObjectDriver(0, 10);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	Thread thread1 = new Thread() {

	    @Override
	    public void run() {
		monitor1.synchronize();
		try {
		    Thread.sleep(200);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		obj1.increase();
		for (int i = 0; i < 50; i++) {
		    System.out.println("Thread 1 count " + i + " ++");
		}

		monitor1.abortNotify();
		monitor1.release();
	    }
	};
	Thread thread2 = new Thread() {

	    @Override
	    public void run() {
		try {
		    Thread.sleep(100);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		monitor1.synchronize();
		try {
		    Thread.sleep(200);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		obj1.increase();
		for (int i = 0; i < 50; i++) {
		    System.out.println("Thread 2 count " + i + " --");
		}
		try {

		    throw new Exception();
		} catch (Exception e) {
		    monitor1.abort();
		}

	    }
	};
	long start = System.currentTimeMillis();
	thread1.start();
	thread2.start();
	try {
	    thread1.join();
	    thread2.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	long done = System.currentTimeMillis();
	long difference = done - start;
	System.out.println("Expected Time of Test ~400 " + difference + "ns");
	assertTrue((obj1.var1 == 1) && (obj1.var2 == 11));
    }

}
