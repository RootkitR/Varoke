package ch.rootkit.varoke.habbohotel.threading;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import ch.rootkit.varoke.utils.Logger;

public class ThreadRejected implements RejectedExecutionHandler {
	
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		Logger.printErrorLine(r.toString() + " was rejected!");
	}
	
}
