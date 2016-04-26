import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MonitorWithAborts {
	Lock mutex = new ReentrantLock();
	Condition avail = mutex.newCondition();
	Savable monitored_object;
	
	public MonitorWithAborts(Savable s) {
		monitored_object = s;
	}
	
	public void synchronize() {
		mutex.lock();
		monitored_object.save();
	}
	
	public void abort() {
		try {
			monitored_object.restore();
		} catch (Exception e) {
			
		} finally {
			avail.signalAll();
			mutex.unlock();
		}
	}
	
	public void waitForAbort() {
		try {
			avail.await();
		} catch (InterruptedException e) {}
	}
	
	public void abortNotify() {
		avail.signal();
	}
	
	public void abortNotifyAll() {
		avail.signalAll();
	}
}
