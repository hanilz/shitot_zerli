package survey;

import java.io.Serializable;

public class Survey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6878148869855072143L;
	private String surveyName;
	private String[] questions;
	private int idSurvey;

	public Survey(int idSurvey ,String surveyName, String[] questions) {
		this.surveyName = surveyName;
		this.questions = questions;
		this.idSurvey=idSurvey;
	}

	/**
	 * @return the title
	 */
	public String getSurveyName() {
		return surveyName;
	}

	/**
	 * @param title the title to set
	 */
	public void setSurveyName(String title) {
		this.surveyName = title;
	}

	/**
	 * @return the questions
	 */
	public String[] getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(String[] questions) {
		this.questions = questions;
	}

	public String getQuestion(int idx) {
		if (idx > 0 && idx <= 6)
			return questions[idx];
		return "Invalid Parameter";
	}

	/**
	 * @return the idSurvey
	 */
	public int getIdSurvey() {
		return idSurvey;
	}

	/**
	 * @param idSurvey the idSurvey to set
	 */
	public void setIdSurvey(int idSurvey) {
		this.idSurvey = idSurvey;
	}

	
	
}
