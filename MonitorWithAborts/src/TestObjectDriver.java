
public class TestObjectDriver implements Savable {

    int var1 = 0;
    int var2 = 0;
    int saveVar1 = 0;
    int saveVar2 = 0;
    
    TestObjectDriver(int var1, int var2){
	this.var1 = var1;
	this.var2 = var2;
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
    public static void main(String[] args){
	TestObjectDriver obj1 = new TestObjectDriver(5,6);
	MonitorWithAborts monitor1 = new MonitorWithAborts(obj1);
	monitor1.synchronize();
	
    }
}
