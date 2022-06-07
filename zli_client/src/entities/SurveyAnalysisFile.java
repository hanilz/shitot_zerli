package entities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
/////!!!line 86!!!!
/**
 * SurveyAnalysisFile saves file attributes
 * Provides Setters and Getters
 */
public class SurveyAnalysisFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fileName;
	private int size = 0;
	private String filePath;
	private byte[] bytesToArray;

	public SurveyAnalysisFile(String fileName) {
		this.fileName = fileName;
	}

	public SurveyAnalysisFile(String fileName, int arraySize, int size, String filePath) {
		this.fileName = fileName;
		initArray(arraySize);
		setSize(size);
		this.setFilePath(filePath);
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

	/**
	 * Upload file survey analysis
	 * @param savedFile
	 * @return saved file
	 */
	public static SurveyAnalysisFile createSurveyAnalysisFile(File savedFile) {
		byte[] byteArray = new byte[(int) savedFile.length()];
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(savedFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

		String[] pathSpliter = savedFile.getPath().split("\\\\");
		String fileName = pathSpliter[pathSpliter.length - 1];
		String filePath = savedFile.getPath();

		SurveyAnalysisFile surveyAnaylsis = new SurveyAnalysisFile(fileName, byteArray.length, byteArray.length,
				filePath);
		try {
			bufferedInputStream.read(surveyAnaylsis.getBytesToArray(), 0, byteArray.length);////// need to close 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return surveyAnaylsis;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
