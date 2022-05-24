package util;

public class MessageHandler {
	private Messages messageType;
	private Object parametersQuery;
	private Object responseQuery;
	private static MessageHandler handlerInstance = null;

	private MessageHandler() {
	}
	
	public synchronized static MessageHandler getHandlerInstance() {
		if(handlerInstance == null)
			handlerInstance = new MessageHandler();
		return handlerInstance;
	}

	public Object getResponseQuery() {
		return responseQuery;
	}

	public void setResponseQuery(Object responseQuery) {
		this.responseQuery = responseQuery;
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
