package com.grandcycles;

public class WorkerThread extends Thread {
	public boolean isTimeUp = false;
	
	public void setTimeUp() {
		isTimeUp = true;
	}
	
	public void resetTimerUp() {
		isTimeUp = false;
	}
	
	public boolean getTimeUp() {
		return isTimeUp;
	}

	public void run() {
		while (OutputFile.open()) {
			OutputFile.writeHeader();
			resetTimerUp();

			while (!getTimeUp()) {
				String latlongstr = GPS.getNext();
				if (latlongstr != null)
					OutputFile.writeLine(latlongstr);
			}

			OutputFile.writeTail();
		}
	}

}
