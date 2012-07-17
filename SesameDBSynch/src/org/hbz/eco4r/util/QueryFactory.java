package org.hbz.eco4r.util;

import static org.hbz.eco4r.configfiguration.DataModelVocabulary.*;
import static org.hbz.eco4r.configfiguration.SesameVocabulary.*;

public class QueryFactory {
	
	final static String ORE_PREFIX = "PREFIX ore:<" + ORE_NAMESPACE + ">";
	final static String RDF_PREFIX = "PREFIX rdf:<" + RDF_NAMESPACE + ">";
	final static String DC_PREFIX = "PREFIX dc:<" + DC_NAMESPACE + ">";
	final static String DCTERMS_PREFIX = "PREFIX dcterms:<" + DCTERMS_NAMESPACE + ">";
	final static String FOAF_PREFIX = "PREFIX foaf:<" + FOAF_NAMESPACE + ">";
	final static String FABIO_PREFIX = "PREFIX j.5:<" + FABIO_NS + ">";
	
	
	public static String getPublicationAggURIs() {
		String query = null;
		String[] strArr = new String[]{FABIO_PREFIX, RDF_PREFIX, 
				FABIO_PUBTYPES[0], FABIO_PUBTYPES[1], 
				FABIO_PUBTYPES[2], FABIO_PUBTYPES[3], 
				FABIO_PUBTYPES[4], FABIO_PUBTYPES[5], 
				FABIO_PUBTYPES[6], FABIO_PUBTYPES[7]};
		
		String s = "SELECT DISTINCT ?agg " +
				"WHERE { " +
				"{ ?agg rdf:type j.5:%s . } " +
				"UNION " +
				"{ ?agg rdf:type j.5:%s . } " +
				"UNION " +
				"{ ?agg rdf:type j.5:%s . } " +
				"UNION " +
				"{ ?agg rdf:type j.5:%s . } " +
				"UNION " +
				"{ ?agg rdf:type j.5:%s . } " +
				"UNION " +
				"{ ?agg rdf:type j.5:%s . } " +
				"UNION " +
				"{ ?agg rdf:type j.5:%s . } " +
				"UNION " +
				"{ ?agg rdf:type j.5:%s . } " +
				" } ";
		
		query = String.format("%s %s " + s, (Object[])strArr);
		
		return query;
	}
	
	
	public static String getReMURIsQuery(String aggURI) {
		String query = null;
		String[] strArr = new String[]{ORE_PREFIX, aggURI};
		
		
		String s = "SELECT DISTINCT ?remURI " +
				"WHERE { " +
				"<%s> ore:isDescribedBy ?remURI . " +
				" } ";
		
		query = String.format("%s " + s, (Object[])strArr);
		
		return query;
	}
	
	
	public static String getDateAccepted(String remURI) {
		String query = null;
		String[] strArr = new String[]{DC_PREFIX, DCTERMS_PREFIX, remURI, remURI};
		
		String s = "SELECT DISTINCT ?date " + 
				"WHERE { " +
				"{ <%s> dc:date ?date . }" +
				" UNION " +
				"{ <%s> dcterms:dateAccepted ?date . }" + 
			"}";
		query = String.format("%s %s " + s, (Object[])strArr);
		
		return query;
	}
	
	public static String getCreators(String remURI) {
		String query = null;
		String[] strArr = new String[]{DC_PREFIX, FOAF_PREFIX, remURI};
		
		String s = "SELECT DISTINCT ?name " + 
				"WHERE { " +
					"{ <%s> dc:creator ?blanc . }" +
					"{ ?blanc foaf:name ?name . }" + 
				"}";
		
		query = String.format("%s %s " + s, (Object[])strArr);
		
		return query;
	}
	
	
	public static String getTitlesQuery (String remURI) {
		String query = null;
		String[] strArr = new String[]{DCTERMS_PREFIX, DC_PREFIX, remURI, remURI};
		
		String s = "SELECT DISTINCT ?t " + 
					"WHERE { " +
						"{ <%s> dc:title ?t }" +
						" UNION " + 
						"{ <%s> dcterms:title ?t }" + 
					"}";
		query = String.format("%s %s " + s, (Object[])strArr);
		
		return query;
	}
	
	
	public static String getContextOfReMQuery (String remURI) {
		String query = null;
		String[] strArr = new String[]{remURI};
		
		String s = "SELECT DISTINCT ?c " + 
					"WHERE { " + 
						"GRAPH ?c { " + 
							"<%s> ?y ?z . " +
						"} " +
					"}";
		query = String.format(s, (Object[])strArr);
		
		return query;
	}
	
	public static String getPubtypesQuery(String remURI) {
		String query = null;
		
		String[] prefixes = new String[]{RDF_PREFIX};
		
		String q = "SELECT DISTINCT ?o " + 
				"WHERE { " + 
				"<" + remURI + ">" + " rdf:type " + "?o ." + 
			"}";
		
		query = String.format("%s " + q, (Object[]) prefixes);
		
		return query;
	}
	
	public static String getDDCSubjectsOfReMQuery(String remURI) {
		String query = null;
		
		String[] prefixes = new String[]{DC_PREFIX};
		
		String q = "SELECT DISTINCT ?o " + 
				"WHERE { " + 
				"<" + remURI + ">" + " dc:subject " + "?o ." + 
			"}";
		
		query = String.format("%s " + q, (Object[]) prefixes);
		
		return query;
	}
}
