package com.grandcycles;

import java.util.Timer;
import java.util.TimerTask;

public class MainApp {
	final static int INTERVALINHOURS = 1;

	public MainApp() {
	}

	public static void main(String[] args) {
		if (GPS.open()) {
			final WorkerThread worker = new WorkerThread();
			worker.start();

			Timer timer = new Timer();
			TimerTask hourlyTask = new TimerTask() {
				public void run() {
					worker.setTimeUp();
				}
				
			};
			timer.schedule(hourlyTask, 1000*60*60, 1000*60*60);
		}
	}
}
