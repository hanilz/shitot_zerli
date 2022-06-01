package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import entities.SurveyAnalysisFile;

public class FilesHandler {
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
