package org.hbz.eco4r.sandbox;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hbz.eco4r.main.Broker;
import org.hbz.eco4r.util.BrokerUtils;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFParseException;


public class BrokerDemo {

	public static void main(String[] args) throws RDFParseException, RepositoryException, IOException {
		
		String sesameConfig = "sesame";
		String harvesterConfig = "harvester";
				
		Broker broker = new Broker(sesameConfig, harvesterConfig);
		BrokerUtils brokerUtils = broker.getBrokerUtils();
		
		
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		
		List<String> aggPubURIs = brokerUtils.getPublicationAggURIs();
		
		for (String aggPubURI : aggPubURIs) {
			List<String> ddcs = brokerUtils.getDDCSubjectsOfAgg(aggPubURI);
			
			for (String ddc : ddcs) {
				String ddcNumber = ddc.split("http://dewey.info/class/")[1].replace("/", "");
				String firstChar = String.valueOf(ddcNumber.charAt(0));
				
				if (result.containsKey(firstChar)) {
					int count = result.get(firstChar);
					result.put(String.valueOf(firstChar), count + 1);
				}
				else {
					result.put(String.valueOf(firstChar), 1);
				}
			}
		}
		
		for (Map.Entry<String, Integer> entry : result.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
