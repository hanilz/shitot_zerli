package survey;

import java.io.Serializable;
import java.util.ArrayList;

public class Survey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6878148869855072143L;
	private String surveyName;
	private ArrayList<SurveyQuestion> questions;
	private int idSurvey;

	public Survey(int idSurvey ,String surveyName, ArrayList<SurveyQuestion> questions) {
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
	public ArrayList<SurveyQuestion> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(ArrayList<SurveyQuestion> questions) {
		this.questions = questions;
	}

	public SurveyQuestion getQuestion(int idx) {
		if (idx >= 0 && idx < questions.size())
			return questions.get(idx);
		return null;
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
