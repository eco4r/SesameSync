package org.hbz.eco4r.main;

import java.util.List;

import org.apache.log4j.Logger;
import org.hbz.eco4r.configfiguration.BrokerConfiguration;
import org.hbz.eco4r.mysql.AggSummaryIngester;
import org.hbz.eco4r.mysql.MySQLConnection;
import org.openrdf.repository.RepositoryException;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		if (args == null || args.length != 3) {
			logger.info("USAGE: java -jar SesameDBSynch.jar [sesame_config_file_name] " +
					"[harvester_config_file_name] [mysql_config_file_name]");
		}
		else {
			try {
				String sesameConfig = args[0];
				String harvesterConfig = args[1];
				String mysqlConfig = args[2];
				
				BrokerConfiguration mysqlConf = new BrokerConfiguration(mysqlConfig);
				String passwd = mysqlConf.getDbPasswd();
				
				if (passwd.isEmpty()) {
					passwd = null;
				}
				
				MySQLConnection connection = new MySQLConnection(mysqlConf.getDbURL(), 
						mysqlConf.getDbUser(), mysqlConf.getDbPasswd());
				Broker broker = new Broker(sesameConfig, harvesterConfig);
				
				List<String> aggs = broker.getBrokerUtils().getPublicationAggURIs();
				
				AggSummaryIngester ingester = new AggSummaryIngester(broker, connection);
				
				for (String aggURI : aggs) {
					AggSummary aggSummary = new AggSummary(aggURI, broker);
					ingester.doIngestAggSummary(aggSummary);
				}
				
				broker.getConnection().commit();
			} 
			catch (RepositoryException e) {
				e.printStackTrace();
			}
		}
	}
}
