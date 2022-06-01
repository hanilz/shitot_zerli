package entities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class SurveyAnalysisFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private int size = 0;
	public byte[] bytesToArray;

	public SurveyAnalysisFile(String fileName) {
		this.fileName = fileName;
	}

	public SurveyAnalysisFile(String fileName, int arraySize, int size) {
		this.fileName = fileName;
		initArray(arraySize);
		setSize(size);
	}

	public void initArray(int size) {
		bytesToArray = new byte[size];
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getBytesToArray() {
		return bytesToArray;
	}

	public byte getBytesToArray(int i) {
		return bytesToArray[i];
	}

	public void setBytesToArray(byte[] bytesToArray) {
		for (int i = 0; i < bytesToArray.length; i++)
			this.bytesToArray[i] = bytesToArray[i];
	}

	public static SurveyAnalysisFile createPDFinResource(File savedFile) {
		byte[] byteArray = new byte[(int) savedFile.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(savedFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		String[] pathSpliter = savedFile.getPath().split("\\\\");
		String filePath = pathSpliter[pathSpliter.length-1];
		
		SurveyAnalysisFile surveyAnaylsis = new SurveyAnalysisFile(filePath, byteArray.length, byteArray.length);
		try {
			bis.read(surveyAnaylsis.getBytesToArray(),0,byteArray.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return surveyAnaylsis;
	}
}
