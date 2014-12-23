package controller;

import java.util.HashMap;
import java.util.Map;

public class RequestReponses {

		private int code;
		private boolean success;
		private Map<String,String> data;
		
		public RequestReponses(int code, boolean success, HashMap<String,String> data){
			this.code = code;
			this.success = success;
			this.data = data;
		}

		public int getCode() {
			return code;
		}

		public boolean isSuccess() {
			return success;
		}

		public Map<String, String> getData() {
			return data;
		}
		
		public String toString(){
			return "Server code : "+code+"\nRequest successful ? : "+success+"\nData received : "+data;
		}
	}
