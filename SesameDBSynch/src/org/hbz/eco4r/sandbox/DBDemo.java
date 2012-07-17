package org.hbz.eco4r.sandbox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hbz.eco4r.main.AggSummary;
import org.hbz.eco4r.main.Broker;
import org.hbz.eco4r.mysql.AggSummaryIngester;
import org.hbz.eco4r.mysql.MySQLConnection;
import org.openrdf.repository.RepositoryException;

public class DBDemo {
	

	public static void main(String[] args) throws ClassNotFoundException, SQLException, RepositoryException {
		
		
		
		String sesameConfig = "sesame";
		String harvesterConfig = "harvester";
		
		MySQLConnection connection = new MySQLConnection("jdbc:mysql://localhost/aggregation_summary", "root", "uzs2zt");
//		Broker broker = new Broker(sesameConfig, harvesterConfig);
//		List<String> aggs = broker.getBrokerUtils().getPublicationAggURIs();
//		
//		List<String> fedoraAggs = new ArrayList<String>();
//		
//		for (String agg : aggs) {
//			if (agg.contains("fedora")) {
//				if (!fedoraAggs.contains(agg)) {
//					fedoraAggs.add(agg);
//				}
//			}
//		}
//		
//		String pidsStr = "";
//		
//		for (String fedoraAgg : fedoraAggs) {
//			String[] parts = fedoraAgg.split("/");
//			String pid = parts[parts.length -1];
//			pidsStr += pid + ",";
//		}
//		
//		System.out.println(pidsStr);
//		for (String aggURI : aggs) {
//			AggSummaryIngester ingester = new AggSummaryIngester(broker, connection);
//			AggSummary aggSummary = new AggSummary(aggURI, broker);
//			ingester.doIngestAggSummary(aggSummary);
//			
//			broker.getConnection().commit();
//		}
	}
}