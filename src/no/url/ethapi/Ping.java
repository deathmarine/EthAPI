package no.url.ethapi;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Ping extends APICall{
	public static String COMMAND = "{\"id\":0,\"jsonrpc\":\"2.0\",\"method\": \"miner_ping\"}\r\n";
	/*
	 	{
  			"id": 1,
  			"jsonrpc": "2.0",
  			"result": "pong"
		}
	 */
	boolean pong = false;
	
	public Ping(String data) {
		super(data);
		try {
			JSONObject json_obj = (JSONObject) parser.parse(raw_data);
			if (json_obj != null) {
				Object obj = json_obj.get("result");
				if (obj instanceof String)
					pong = ((String) obj).equalsIgnoreCase("true");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasResponed() {
		return pong;
	}
	

}
