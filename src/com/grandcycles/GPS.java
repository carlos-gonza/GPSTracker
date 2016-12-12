package com.grandcycles;

import com.pi4j.wiringpi.Serial;

public class GPS {
	static int fd = -1;
	static float lastlongitude = 0.0f;
	static float lastlatitude = 0.0f;

	public static String getLatLongStr(String line) {
		int idx = 0;
		if ((idx = line.indexOf("$GPGGA")) >= 0 && ((line.length() - idx) > 60)) {
			String time = line.substring(idx + 7, idx + 13);
			String lat = line.substring(idx + 14, idx + 16);
			String latdeg = line.substring(idx + 16, idx + 16 + 7);
			String lon = line.substring(idx + 26, idx + 26 + 3);
			String londeg = line.substring(idx + 29, idx + 29 + 7);
			int nlat = Integer.parseInt(lat); // 26
			float flat = Float.parseFloat(latdeg); // 16.8263
			float flat1 = flat / 60; // 0.280438
			float latitude = nlat + flat1;
			if (line.substring(idx + 24, idx + 25).equals("S"))
				latitude = -1 * latitude;
			int nlon = Integer.parseInt(lon); // 80
			float flon = Float.parseFloat(londeg); // 16.8767
			float flon1 = flon / 60; // 0.281278
			float longitude = nlon + flon1; // 80.281278
			if (line.substring(idx + 37, idx + 38).equals("W"))
				longitude = -1 * longitude;
			/*
			 * int hour = Integer.parseInt(time.substring(0, 2)); int min =
			 * Integer.parseInt(time.substring(2, 4)); int sec =
			 * Integer.parseInt(time.substring(4, 6)); long secs = sec + min*60
			 * + hour*60*60;
			 */
			if ((Math.abs(longitude - lastlongitude) >= 0.0001f) || (Math.abs(latitude - lastlatitude) >= 0.0001f)) {
				lastlongitude = longitude;
		        lastlatitude = latitude;
				return (longitude + "," + latitude);
			}
		}
		return null;
	}

	private static String bytesToStringUTFCustom(byte[] bytes) {
		char[] buffer = new char[bytes.length];

		for (int i = 0; i < buffer.length; i++) {
			char c = (char) bytes[i];
			buffer[i] = c;
		}
		return new String(buffer);
	}

	public static String getNext() {
		int dataavail = Serial.serialDataAvail(fd);
		while (dataavail > 0) {
			byte[] bytes = Serial.serialGetBytes(fd, dataavail);
			String line = bytesToStringUTFCustom(bytes);

			String latlongstr = getLatLongStr(line);
			if (latlongstr != null) {
				return latlongstr;
			}

			dataavail = Serial.serialDataAvail(fd);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				
			}
		}
		return null;
	}

	public static boolean open() {
		fd = Serial.serialOpen(Serial.DEFAULT_COM_PORT, 4800);
		if (fd == -1) {
			System.out.println(" ==>> SRIAL SETUP FAILED");
			return false;
		}
		return true;
	}

}
