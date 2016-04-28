
public class TestObjectDriver implements Savable {

    int var1 = 0;
    int var2 = 0;
    private int saveVar1 = 0;
    private int saveVar2 = 0;

    public TestObjectDriver(int var1, int var2) {
	this.var1 = var1;
	this.var2 = var2;
    }
    
    public TestObjectDriver(){
	
    }
    
    @Override
    public void save() {
	saveVar1 = var1;
	saveVar2 = var2;

    }

    @Override
    public void restore() {
	var1 = saveVar1;
	var2 = saveVar2;
    }

    public void increase() {
	var1++;
	var2++;
    }

    public void test1A(MonitorWithAborts monitor) {
	monitor.synchronize();
	increase();
	monitor.abortNotify();
	monitor.release();
    }

    public synchronized void test1B() {
	increase();
	notify();
    }  
    
    public void test2A(MonitorWithAborts monitor) {
	monitor.synchronize();
	try{
	    increase();
	    throw new Exception();
	}
	catch(Exception e){
	    monitor.abort();
	}
    }
    
    
}

