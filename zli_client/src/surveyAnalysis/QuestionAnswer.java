package surveyAnalysis;

import java.io.Serializable;

public class QuestionAnswer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6254089446485571757L;
	private int idQuestion;
	private String Question;
	private int[] answers;
	
	public QuestionAnswer(int idQuestion, String question, int[] answers) {
		this.idQuestion = idQuestion;
		Question = question;
		this.answers = answers;
	}

	/**
	 * @return the idQuestion
	 */
	public int getIdQuestion() {
		return idQuestion;
	}

	/**
	 * @param idQuestion the idQuestion to set
	 */
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return Question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		Question = question;
	}

	/**
	 * @return the answers
	 */
	public int[] getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(int[] answers) {
		this.answers = answers;
	}
	
	
}
