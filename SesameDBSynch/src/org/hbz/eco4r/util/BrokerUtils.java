package org.hbz.eco4r.util;

import java.util.ArrayList;
import java.util.List;

import org.hbz.eco4r.configfiguration.Configuration;
import org.hbz.eco4r.configfiguration.HarvesterConfiguration;
import org.hbz.eco4r.configfiguration.Property;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

public class BrokerUtils {
	
	private RepositoryConnection connection;
	private Configuration harvesterConfig;
	

	public BrokerUtils(RepositoryConnection connection, 
			Configuration harvesterConfig) {
		this.connection = connection; 
		this.harvesterConfig = harvesterConfig;
	}
	
	//==== Publication AggURIs ====
	public List<String> getPublicationAggURIs() {
		List<String> publicationAggURIs = new ArrayList<String>();
		
		String query = QueryFactory.getPublicationAggURIs();
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfS = bindingSet.getValue("agg");
				String agg = valueOfS.stringValue();
				if (!publicationAggURIs.contains(agg)) {
					publicationAggURIs.add(agg);
				}
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return publicationAggURIs;
	}
	
	// ==== Resource Map ====
	public List<String> getReMURIs(String aggURI) {
		List<String> remURIs = new ArrayList<String>();
		
		String query = QueryFactory.getReMURIsQuery(aggURI);
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfS = bindingSet.getValue("remURI");
				String remURI = valueOfS.stringValue();
				if (!remURIs.contains(remURI)) {
					remURIs.add(remURI);
				}
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return remURIs;
	}
	
	// ==== Date ====
	public String getDate(String aggURI) {
		String date = null;
		
		String query = QueryFactory.getDateAccepted(aggURI);
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfS = bindingSet.getValue("date");
				date = valueOfS.stringValue();
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	// ==== Creators ====
	public List<String> getCreators(String aggURI) {
		List<String> creators = new ArrayList<String>();
		
		String query = QueryFactory.getCreators(aggURI);
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfS = bindingSet.getValue("name");
				String name = valueOfS.stringValue();
				
				if (!creators.contains(name)) {
					creators.add(name);
				}
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return creators;
	}
	
	// ==== pubtypes ====
	public List<String> getPubTypesOfAgg(String aggURI){
		List<String> pubTypes = new ArrayList<String>();
		
		String query = QueryFactory.getPubtypesQuery(aggURI);
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfS = bindingSet.getValue("o");
				String stringValueOfO = valueOfS.stringValue();
				if (!pubTypes.contains(stringValueOfO)) {
					pubTypes.add(stringValueOfO);
				}
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return pubTypes;
	}
	 
	public List<String> getPubTypesOfAgg(String aggURI, List<String> excludedNamespaces){
		List<String> pubTypes = new ArrayList<String>();
		
		String query = QueryFactory.getPubtypesQuery(aggURI);
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfS = bindingSet.getValue("o");
				String stringValueOfO = valueOfS.stringValue();
				if (!this.isPartOfElementList(stringValueOfO, excludedNamespaces)) {
					if (!pubTypes.contains(stringValueOfO)) {
						pubTypes.add(stringValueOfO);
					}
				}
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return pubTypes;
	}
	
	// ==== DDC Subjects ====
	
	public List<String> getDDCSubjectsOfAgg(String aggURI) {
		List<String> ddcSubjectsOfReM = new ArrayList<String>();
		
		String query = QueryFactory.getDDCSubjectsOfReMQuery(aggURI);
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfS = bindingSet.getValue("o");
				String stringValueOfO = valueOfS.stringValue();
				if (!ddcSubjectsOfReM.contains(stringValueOfO)) {
					ddcSubjectsOfReM.add(stringValueOfO);
				}
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return ddcSubjectsOfReM;
	}
	
	// ==== Context ====
	
	public String getContextOfAgg(String aggURI) {
		String context = null;
		String query = QueryFactory.getContextOfReMQuery(aggURI);
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfS = bindingSet.getValue("c");
				String c = valueOfS.stringValue();
				context = c;
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return context;
	}
	
	// ==== Repositories ====
	
	public String getRepository(String aggURI) {
		String repository = null;
		List<String> activeRepos = this.getActiveRepositoryPrefixes(this.harvesterConfig);
		
		String context = this.getContextOfAgg(aggURI);
		
		if (context != null) {
			for (String activeRepo : activeRepos) {
				HarvesterConfiguration config = new HarvesterConfiguration(activeRepo, this.harvesterConfig);
				String out = config.getOutdir();
			
				if (context.contains(out)) {
					repository = config.getBaseURL();
					break;
				}
			}
		}
		
		return repository;
	}
	
	public List<String> getActiveRepositoryPrefixes(
			Configuration harvesterConfig) {
		List<String> activeRepositoryPrefixes = new ArrayList<String>();
		
		List<Property> properties = harvesterConfig.getProperties();
		
		for (Property property : properties) {
			String key = property.getKey().trim();
			if (!key.startsWith("#")) {
				String activeRepo = key.split("\\.")[0];
				if (!activeRepositoryPrefixes.contains(activeRepo)) {
					activeRepositoryPrefixes.add(activeRepo);
				}
			}
		}
		
		return activeRepositoryPrefixes;
	}
	
	// ==== Titles ====
	
	public String getTitleOfAgg(String aggURI) {
		String title = null;
		
		String query = QueryFactory.getTitlesQuery(aggURI);
		TupleQueryResult result = this.evaluate(query);
		
		try {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfT = bindingSet.getValue("t");
				title = valueOfT.stringValue();
			}
		} 
		catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		return title;
	}
	
	// ==== Query Evaluation ====

	private TupleQueryResult evaluate(String query) {
		TupleQueryResult result = null;
		TupleQuery tupleQuery;

		try {
			tupleQuery = this.connection.prepareTupleQuery(
					QueryLanguage.SPARQL, query);
			result = tupleQuery.evaluate();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}

		return result;
	}
		
	
	// Misc Methods
	
	private boolean isPartOfElementList(String str, 
			List<String> excludedNamespaces) {
		boolean isPartOfElementList = false;
		
		for (String element : excludedNamespaces) {
			if (str.contains(element)){
				isPartOfElementList = true;
				break;
			}
		}
		
		return isPartOfElementList;
	}	
		
		

	public RepositoryConnection getConnection() {
		return connection;
	}

	public void setConnection(RepositoryConnection connection) {
		this.connection = connection;
	}

	public Configuration getHarvesterConfig() {
		return harvesterConfig;
	}

	public void setHarvesterConfig(Configuration harvesterConfig) {
		this.harvesterConfig = harvesterConfig;
	}
}
