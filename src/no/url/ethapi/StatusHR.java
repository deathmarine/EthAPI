package no.url.ethapi;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class StatusHR extends Status {
	public static String COMMAND = "{\"id\":0,\"jsonrpc\":\"2.0\",\"method\": \"miner_getstathr\"}\r\n";
	private JSONObject object;
	private double TOTAL_POWER = 0;
	
	public StatusHR(String data) {
		super(data);
		try {
			JSONObject json_obj = (JSONObject) parser.parse(data);
			if (json_obj != null) {
				Object obj = json_obj.get("result");
				if (obj instanceof JSONObject)
					this.object = (JSONObject) obj;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (object.containsKey("version")) {
			this.setVersion((String) object.get("version"));
		}
		if (object.containsKey("runtime")) {
			this.setRuntime(Integer.valueOf((String) object.get("runtime")));
		}
		if (object.containsKey("ethhashrate")) {
			this.setHashrate((long) object.get("ethhashrate") / 1000000f);
		}
		if (object.containsKey("ethshares")) {
			this.setShares((this.safeLongToInt((long) object.get("ethshares"))));
		}
		if (object.containsKey("ethinvalid")) {
			this.setInvalid((this.safeLongToInt((long) object.get("ethinvalid"))));
		}
		if (object.containsKey("fanpercentages")) {
			JSONArray array = (JSONArray) object.get("fanpercentages");
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					int v = this.safeLongToInt((long) array.get(i));
					AVG_FAN += v;
				}
				AVG_FAN = AVG_FAN / (double) array.size();
			}
		}
		if (object.containsKey("powerusages")) {
			JSONArray array = (JSONArray) object.get("powerusages");
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					double v = (double) array.get(i);
					TOTAL_POWER += v;
				}
			}
		}
		if (object.containsKey("temperatures")) {
			JSONArray array = (JSONArray) object.get("temperatures");
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					int v = Integer.valueOf(String.valueOf((long) array.get(i)));
					AVG_TEMP += v;
				}
				AVG_TEMP = AVG_TEMP / (double) array.size();
			}
		}

		if (object.containsKey("pooladdrs")) {
			this.setPool((String) object.get("pooladdrs"));
		}

	}
	/*
	 * response["version"] = version.str(); // miner version. 
	 * response["runtime"] = runtime.str(); // running time, in minutes. // total ETH hashrate in MH/s, number of ETH shares, number of ETH rejected shares. 
	 * response["ethhashrate"] = (p.rate()); 
	 * response["ethhashrates"] = detailedMhEth; 
	 * response["ethshares"] = s.getAccepts(); 
	 * response["ethrejected"] = s.getRejects();
	 * response["ethinvalid"] = s.getFailures(); 
	 * response["ethpoolsw"] = 0; // Hardware Info 
	 * response["temperatures"] = temps; // Temperatures(C) for all GPUs 
	 * response["fanpercentages"] = fans; // Fans speed(%) for all GPUs
	 * response["powerusages"] = powers; // Power Usages(W) for all GPUs
	 * response["pooladdrs"] = poolAddresses.str(); // current mining pool. For dual mode, there will be two pools here.
	 */

	public JSONObject getJSONObject() {
		return object;
	}

	public int getSpecificTemp(int gpu) {
		JSONArray array = (JSONArray) object.get("temperatures");
		if (array != null)
			return Integer.valueOf(String.valueOf((long) array.get(gpu)));
		return 0;
	}

	public int getSpecificFan(int gpu) {
		JSONArray array = (JSONArray) object.get("fanpercentages");
		if (array != null)
			return Integer.valueOf(String.valueOf((long) array.get(gpu)));
		return 0;
	}

	public double getSpecificPower(int gpu) {
		JSONArray array = (JSONArray) object.get("powerusages");
		if (array != null)
			return (double) array.get(gpu);
		return 0D;
	}

	public double getTotalPower() {
		return TOTAL_POWER;
	}

	public double getSharesPerMin() {
		return ((double) this.getShares()) / (double) this.getRuntime();
	}

	public int getAmtGPUs() {
		JSONArray array = (JSONArray) object.get("fanpercentages");
		if (array != null)
			return array.size();
		return 0;
	}

	public double getGPURate(int gpu) {
		JSONArray array = (JSONArray) object.get("ethhashrates");
		if (array != null)
			return ((double) array.get(gpu)) / 1000000d;
		return 0D;
	}

	public String toString() {
		return object.toString();
	}

	public StatusHR getStatusHR(String IP_address, int port) throws IOException {
		return getStatusHR(new Server(IP_address, port));
	}

	public StatusHR getStatusHR(Server s) throws IOException {
		String data = StatusHR.connect(s.getIPAddress(), s.getPort(), StatusHR.COMMAND);
		if (data != null)
			return new StatusHR(data);
		return null;
	}
}