package util;

public class MessageHandler {
	private Messages messageType;
	private Object parametersQuery;
	private static MessageHandler handlerInstance = null;

	private MessageHandler() {
	}
	
	public synchronized static MessageHandler getHandlerInstance() {
		if(handlerInstance == null)
			handlerInstance = new MessageHandler();
		return handlerInstance;
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
