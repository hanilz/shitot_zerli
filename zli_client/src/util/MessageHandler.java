package util;

import java.io.Serializable;

public class MessageHandler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Messages messageType;
	private Object parametersQuery;
	private Object responseFromDb;
	private static MessageHandler handlerInstance = null;

	private MessageHandler() {
	}
	
	public synchronized static MessageHandler getHandlerInstance() {
		if(handlerInstance == null)
			handlerInstance = new MessageHandler();
		return handlerInstance;
	}

	public Object getResponseFromDb() {
		return responseFromDb;
	}

	public void setResponseFromDb(Object responseFromDb) {
		this.responseFromDb = responseFromDb;
	}

	public Messages getMessageType() {
		return messageType;
	}

	public void setMessageType(Messages messageType) {
		this.messageType = messageType;
	}

	public Object getParametersQuery() {
		return parametersQuery;
	}

	public void setParametersQuery(Object parametersQuery) {
		this.parametersQuery = parametersQuery;
	}

}
