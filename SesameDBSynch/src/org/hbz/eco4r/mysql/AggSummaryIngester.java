package org.hbz.eco4r.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.hbz.eco4r.main.AggSummary;
import org.hbz.eco4r.main.Broker;
import org.hbz.eco4r.util.BrokerUtils;

public class AggSummaryIngester {
	
	private static Logger logger = Logger.getLogger(AggSummaryIngester.class);

	private List<String> publicationAggURIs;
	private BrokerUtils brokerUtils;
	private MySQLConnection mySQLConnection;
	
	public AggSummaryIngester(Broker broker, MySQLConnection mySQLConnection) {
		this.setBrokerUtils(broker.getBrokerUtils());
		this.setPublicationAggURIs(this.brokerUtils.getPublicationAggURIs());
		this.setMySQLConnection(mySQLConnection);
	}

	public int doIngestAggSummary(AggSummary aggSummary) {
		int ingestStatus = -1;
		
		String aggURI = aggSummary.getAggURI();
		int index = getAggIndexFromDB(aggURI);
		
		if (index == -1) {
			ingestStatus = this.insertNew(aggSummary);
		}
		else { 
			ingestStatus = this.update(aggSummary);
		}
		
		return ingestStatus;
	}
	
	private int update(AggSummary aggSummary) {
		int ingestStatus = -1;
		String aggURI = aggSummary.getAggURI();
		
		String deleteQuery = "DELETE FROM `aggregation_summary`.`agg_main` " + 
				 "WHERE `agg_uri`=?";
		try {
			PreparedStatement preparedStatement = this.mySQLConnection.
				getConnection().prepareStatement(deleteQuery);
			preparedStatement.setString(1, aggURI);
			ingestStatus = preparedStatement.executeUpdate();
			preparedStatement.close();
			this.insertNew(aggSummary);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingestStatus;
	}

	private int insertNew(AggSummary aggSummary) {
		int ingestStatus = -1;
		try {
			List<String> remURIs = aggSummary.getRemURIs();
			String dateAccepted = aggSummary.getDateAccepted();
			List<String> creators = aggSummary.getCreators();
			List<String> pubTypes = aggSummary.getPubTypes();
			List<String> ddcSubjects = aggSummary.getDdcSubjects();
			String context = aggSummary.getContext();
			String repository = aggSummary.getRepository();
			String title = aggSummary.getTitle();
			String aggURI = aggSummary.getAggURI();

			String query1 = " INSERT INTO `aggregation_summary`.`agg_main`"
					+ "(`id`,`agg_uri`,`accept_date`,`repository`,`title`,`context`) "
					+ "VALUES (NULL,?,?,?,?,?); ";

			
			
			// just for dubugging purposes
			String repStr = query1.replaceAll("\\?", "%s");
			String[] params = new String[]{aggURI, dateAccepted, repository, title, context};
			String str = String.format(repStr, (Object[])params);
			logger.info(str);
			//----------------------------
			
			PreparedStatement preparedStatement = this.mySQLConnection
					.getConnection().prepareStatement(query1,
							Statement.RETURN_GENERATED_KEYS);

			if (aggURI != null && !aggURI.isEmpty()) {
				preparedStatement.setString(1, aggURI);
				preparedStatement.setString(2, dateAccepted);
				preparedStatement.setString(3, repository);
				preparedStatement.setString(4, title);
				preparedStatement.setString(5, context);
			}
			
			ingestStatus = preparedStatement.executeUpdate();

			int lastGeneratedIndex = -1;
			ResultSet rs = preparedStatement.getGeneratedKeys();
			while (rs.next()) {
				lastGeneratedIndex = rs.getInt(1);
			}
			preparedStatement.close();

			
			// Inserting statements for the rems
			for (String remURI : remURIs) {
				if (remURI != null && !remURI.isEmpty()) {
					Statement stmt = this.mySQLConnection.getConnection()
							.createStatement();
					String query2 = " INSERT INTO `aggregation_summary`.`rems` "
							+ "(`id`,`agg_id`,`rem_uri`) " + "VALUES (NULL, '"
							+ lastGeneratedIndex + "', '" + remURI + "');";
					
					// just for debugging purposes
					logger.info(query2);
					//----------------------------
					
					stmt.executeUpdate(query2);
					stmt.close();
				}
			}

			
			// Inserting statements for the creators 
			for (String creator : creators) {
				if (creator != null && !creator.isEmpty() && creator != ",") {
					if (creator.contains("'")) {
						creator = creator.replaceAll("'", "´");
					}
					Statement stmt = this.mySQLConnection.getConnection()
							.createStatement();
					String query2 = " INSERT INTO `aggregation_summary`.`creator` "
							+ "(`id`,`agg_id`,`creator`) " + "VALUES (NULL, '"
							+ lastGeneratedIndex + "', '" + creator + "');";
					
					// just for debugging purposes
					logger.info(query2);
					//----------------------------
					
					stmt.executeUpdate(query2);
					stmt.close();	
				}
			}

			// Inserting statements for the publication types
			for (String pubtype : pubTypes) {
				if (pubtype != null && !pubtype.isEmpty()) {
					Statement stmt = this.mySQLConnection.getConnection()
							.createStatement();
					String query2 = " INSERT INTO `aggregation_summary`.`pub_type` "
							+ "(`id`,`agg_id`,`pubtype`) "
							+ "VALUES (NULL, '"
							+ lastGeneratedIndex + "', '" + pubtype + "');";
					
					// just for debugging purposes
					logger.info(query2);
					//----------------------------
					
					stmt.executeUpdate(query2);
					stmt.close();
				}
			}

			
			// Inserting statements for the ddc subjects
			for (String subject : ddcSubjects) {
				if (subject != null && !subject.isEmpty()) {
					Statement stmt = this.mySQLConnection.getConnection()
							.createStatement();
					String query2 = " INSERT INTO `aggregation_summary`.`subject` "
							+ "(`id`,`agg_id`,`subj`) " + "VALUES (NULL, '"
							+ lastGeneratedIndex + "', '" + subject + "');";
					
					// just for debugging purposes
					logger.info(query2);
					//----------------------------
					
					stmt.executeUpdate(query2);
					stmt.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingestStatus;
	}

	private int getAggIndexFromDB(String aggURI) {
		int index = -1;
		String query = "SELECT DISTINCT `id`, `agg_uri` FROM `aggregation_summary`.`agg_main`;";
		
		Statement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = this.getMySQLConnection().getConnection().createStatement();
			resultSet = stmt.executeQuery(query);
			
			while (resultSet.next()) {
				String agg_uri = resultSet.getString("agg_uri");
				if (aggURI.compareTo(agg_uri) == 0) {
					index = resultSet.getInt("id");
					break;
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (resultSet != null) {
		        try {
		            resultSet.close();
		        } 
		        catch (SQLException sqlEx) { // ignore }
		        	resultSet = null;
		        }
			}

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } 
		        catch (SQLException sqlEx) { // ignore }

		        stmt = null;
		        }
		    }
		}
			
		
		return index;
	}

	public List<String> getPublicationAggURIs() {
		return publicationAggURIs;
	}
	public void setPublicationAggURIs(List<String> publicationAggURIs) {
		this.publicationAggURIs = publicationAggURIs;
	}
	public BrokerUtils getBrokerUtils() {
		return brokerUtils;
	}
	public void setBrokerUtils(BrokerUtils brokerUtils) {
		this.brokerUtils = brokerUtils;
	}
	public MySQLConnection getMySQLConnection() {
		return mySQLConnection;
	}
	public void setMySQLConnection(MySQLConnection mySQLConnection) {
		this.mySQLConnection = mySQLConnection;
	}
}
