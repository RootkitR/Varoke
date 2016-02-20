package ch.rootkit.varoke.habbohotel.threading;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ch.rootkit.varoke.utils.Logger;

public class ThreadPool {
	
	private ThreadPoolExecutor threadExecutor;
	private ScheduledExecutorService scheduledExecutor;
	
	public ThreadPool(){
		ThreadRejected rejected = new ThreadRejected();
		ThreadFactory factory = Executors.defaultThreadFactory();
		threadExecutor = new ThreadPoolExecutor(3, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), factory, rejected);
		scheduledExecutor = Executors.newScheduledThreadPool(2);
	}
	
	public void execute(Runnable runnable){
		try{
			threadExecutor.execute(runnable);
		}catch(Exception ex){
			Logger.printErrorLine("Error while executing Thread: " + ex.getMessage());
		}
	}
	
	public void executeScheduled(Runnable runnable, long delay){
		try{
			scheduledExecutor.schedule(runnable, delay, TimeUnit.MILLISECONDS);
		}catch(Exception ex){
			Logger.printErrorLine("Error while executing Scheduled Thread: " + ex.getMessage());
		}
	}
	
	public void dispose(){
		threadExecutor.shutdown();
		while(!threadExecutor.isTerminated()){
		}
		scheduledExecutor.shutdown();
		while(!threadExecutor.isTerminated()){
		}
	}
}
