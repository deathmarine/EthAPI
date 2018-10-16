package no.url.ethapi;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class StatusDetail extends Status{

	public static String COMMAND = "{\"id\":0,\"jsonrpc\":\"2.0\",\"method\": \"miner_getstatdetail\"}\r\n";
	
	public StatusDetail(String data) {
		super(data);
		
	}
	/*
	 * Example Return *
	 {
  		"id": 1,
  		"jsonrpc": "2.0",
  		"result": {
    		"connection": {                     // Current active connection
      		"isconnected": true,
      		"switched": 0,
      		"uri": "stratum+tcp://<omitted-ethereum-address>.worker@eu1.ethermine.org:4444"
    		},
	    	"difficulty": 3999938964.0,
	    	"epoch": 218,
	    	"epoch_changes": 1,                 // Ethminer starts with epoch 0. First connection to pool increments this counter
	    	"hashrate": 46709128,               // Overall HashRate in H/s
	    	"hostname": "<omitted-hostname>",
	    	"runtime": 240,                     // Total running time in seconds
	    	"shares": {                         // Summarized info about shares
	      		"accepted": 5,
	      		"acceptedstale": 1,
	      		"invalid": 1,
	      		"lastupdate": 58,                 // Latest update of any share info of is X seconds ago
	      		"rejected": 0
	    	},
	    	"tstart": 63,
	    	"tstop": 69,
	    	"version": "ethminer-0.16.0.dev3-73+commit.f35c22ab",
	    	"gpus": [{
	    		"fan": 54,                       // Fan in %
	       		"hashrate": 23604564,            // HashRate of GPU in H/s
	       		"index": 0,
	       		"ispaused": false,
	       		"nonce_start": 6636918940706763208,
	       		"nonce_stop": 6636920040218390984,
	       		"pause_reason": "",              // Possible values: "", "temperature", "api", or "temperature,api"
	       		"power": 0.0,                    // Powerdrain in W
	       		"shares": {                      // Detailed info about shares from this GPU
	         		"accepted": 3,
	         		"acceptedstale": 0,
	         		"invalid": 0,
	         		"lastupdate": 58,              // Share info from this GPU updated X seconds ago
	         		"rejected": 0
	       		},
	       		"temp": 53 },{
	       		"fan": 53,
	       		"hashrate": 23104564,
	       		"index": 1,
	       		"ispaused": false,
	       		"nonce_start": 6636920040218391000,
	       		"nonce_stop": 6636921139730018000,
	       		"pause_reason": "",
	       		"power": 0.0,
	       		"shares": {
	         		"accepted": 2,
	         		"acceptedstale": 1,
	         		"invalid": 1,
	         		"lastupdate": 134,
	         		"rejected": 0
	       		}
       		}
    	]
  	}
	
	 */

	@Override
	public int getSpecificTemp(int gpu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpecificFan(int gpu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSharesPerMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAmtGPUs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getGPURate(int gpu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public StatusDetail getStatus(String IP_address, int port) throws IOException {
		return getStatus(new Server(IP_address, port));
	}		
	
	public StatusDetail getStatus(Server s) throws IOException {
		String data = Status.connect(s.getIPAddress(), s.getPort(), StatusHR.COMMAND);
		if (data != null)
			return new StatusDetail(data);
		
		return null;
	}
}