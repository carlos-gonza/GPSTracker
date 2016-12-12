package com.grandcycles;

import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class OutputFile {
	static BufferedWriter bw;

	public OutputFile() {
	}

	public static boolean writeHeader() {
		try {
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			bw.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
			bw.write("<Document><Placemark><LineString><coordinates>\n");
			bw.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean writeTail() {
		try {
			bw.write("</coordinates></LineString>\n");
			bw.write("<Style><lineStyle><color>#ff0000ff</color>\n");
			bw.write("<width>5</width>\n");
			bw.write("</LineStyle>\n");
			bw.write("</Style></Placemark></Document></kml>\n");
			bw.flush();
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean writeLine(String line) {
		try {
			bw.write(line + "\n");
			bw.flush();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean open() {
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
			File file = new File(dateFormat.format(date) + ".kml");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			if (bw != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

}
