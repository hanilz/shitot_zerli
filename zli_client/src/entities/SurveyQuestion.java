package entities;

import java.io.Serializable;

/**
 * SurveyQuestion saves questionID,surveyID and question
 * Provides setters and getters
 *
 */
public class SurveyQuestion implements Serializable {
	private static final long serialVersionUID = 736007857216709227L;
	private int questionID;
	private int surveyID;
	private String question;
	
	public SurveyQuestion(int questionID, int surveyID, String question) {
		this.questionID = questionID;
		this.surveyID = surveyID;
		this.question = question;
	}

	/**
	 * @return the questionID
	 */
	public int getQuestionID() {
		return questionID;
	}

	/**
	 * @param questionID the questionID to set
	 */
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	/**
	 * @return the surveyID
	 */
	public int getSurveyID() {
		return surveyID;
	}

	/**
	 * @param surveyID the surveyID to set
	 */
	public void setSurveyID(int surveyID) {
		this.surveyID = surveyID;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	
	
	
}
