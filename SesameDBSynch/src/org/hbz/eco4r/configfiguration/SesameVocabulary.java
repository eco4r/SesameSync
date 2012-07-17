package org.hbz.eco4r.configfiguration;

public class SesameVocabulary {

	public static final String SESAME_REPOSITORY_URI = "sesame.repositoryURI";
	public static final String SESAME_BASE_URI = "sesame.baseURI";
	public static final String SESAME_CONTEXT_STRATEGY = "sesame.contextStrategy";
	
	public static final String SESAME_CONTEXTSTRATEGY_PMH_BASE_URL = "pmhBaseURL";
	public static final String SESAME_CONTEXTSTRATEGY_FILE_ABSOLUTE_PATH = "fileAbsolutePath";
	public static final String SESAME_CONTEXTSTRATEGY_FILE_RELATIVE_PATH = "fileRelativePath";
	
	public static final String[] SESAME_CONTEXTSTRATEGIES = {
		SESAME_CONTEXTSTRATEGY_PMH_BASE_URL,
		SESAME_CONTEXTSTRATEGY_FILE_ABSOLUTE_PATH,
		SESAME_CONTEXTSTRATEGY_FILE_RELATIVE_PATH
	};
	
	public static final String ORE_NAMESPACE = "http://www.openarchives.org/ore/terms/";
	public static final String RDF_NAMESPACE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static final String DC_NAMESPACE = "http://purl.org/dc/elements/1.1/";
	public static final String DCTERMS_NAMESPACE = "http://purl.org/dc/terms/";
	public static final String FOAF_NAMESPACE = "http://xmlns.com/foaf/0.1/";
}
