package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import entities.SurveyAnalysisFile;

/**
 * This class helps us to save the file that given from the server from the blob table after uploading the pdf file.
 */
public class FilesHandler {
	/**
	 * This method save the file that given from the database after receiving the fils from the blob table.
	 * It's write bytes to the file to save it as pdf file in the client folders.
	 * @param surveyFile
	 * @return newFile
	 */
	public static File saveFile(SurveyAnalysisFile surveyFile) {
		surveyFile.setFilePath(System.getProperty("user.dir") + "\\src\\resources\\files\\" + surveyFile.getFileName());
		File newFile = new File(surveyFile.getFilePath());
		
		try {
			FileOutputStream fos = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(surveyFile.getBytesToArray(), 0, surveyFile.getSize());
			bos.flush();
			fos.flush();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newFile;
	}
}
