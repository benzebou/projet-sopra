package controller;

import java.util.HashMap;

public class RequestsParams {
	private RequestType typeOfRequest;
	private HashMap<String,Object> parameters;
	
	public RequestsParams(RequestType typeOfRequest, HashMap<String,Object> parameters) {
		this.typeOfRequest = typeOfRequest;
		this.parameters = parameters;
	}

	public RequestType getTypeOfRequest() {
		return typeOfRequest;
	}

	public HashMap<String,Object> getParameters() {
		return parameters;
	}

}
