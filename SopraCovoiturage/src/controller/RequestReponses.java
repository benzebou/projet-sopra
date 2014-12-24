package controller;

import java.util.HashMap;
import java.util.Map;

public class RequestReponses {

		private int code;
		private boolean success;
		private Map<String,Object> data;
		
		public RequestReponses(int code, boolean success, Map<String, Object> map){
			this.code = code;
			this.success = success;
			if (map == null)
				this.data = new HashMap<String,Object>();
			else 
				this.data = map ;
		}

		public int getCode() {
			return code;
		}

		public boolean isSuccess() {
			return success;
		}

		public Map<String, Object> getData() {
			return data;
		}
		
		public String toString(){
			return "Server code : "+code+"\nRequest successful ? : "+success+"\nData received : "+data;
		}
	}
