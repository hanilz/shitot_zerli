package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import entities.SurveyAnalysisFile;

public class FilesHandler {
	public static File saveFile(SurveyAnalysisFile surveyFile) {
		String currentPath = new File("").getAbsolutePath();
		String saveToPath = currentPath.substring(0, currentPath.length()-10);
		String path = saveToPath+"/zli_client/src/resources/survey_analysis/" + surveyFile.getFileName();
		File newFile = new File(path);
		try {
			SurveyAnalysisFile receivedFile = (SurveyAnalysisFile) surveyFile;
			FileOutputStream fos = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(receivedFile.getBytesToArray(), 0, receivedFile.getSize());
			bos.flush();
			fos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile;
	}
}
