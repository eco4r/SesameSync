package org.hbz.eco4r.configfiguration;

import java.util.List;

import org.hbz.eco4r.configfiguration.Configuration;
import org.hbz.eco4r.configfiguration.Property;

import static org.hbz.eco4r.configfiguration.SesameVocabulary.*;

public class SesameConfiguration {

	private String repositoryURI;
	private String contextStrategy;
	private String repositoryBaseURI;
	
	public SesameConfiguration(Configuration config) {
		List<Property> props = config.getProperties();
		for (Property prop : props) {
			String key = prop.getKey().trim();
			
			if (key.compareTo(SESAME_REPOSITORY_URI) == 0) {
				this.setRepositoryURI(prop.getValues().get(0).trim());
			}
			
			if (key.compareTo(SESAME_CONTEXT_STRATEGY) == 0) {
				this.setContextStrategy(prop.getValues().get(0).trim());
			}
			
			if (key.compareTo(SESAME_BASE_URI) == 0) {
				this.setRepositoryBaseURI(prop.getValues().get(0).trim());
			}
		}
	}
	
	public String getRepositoryURI() {
		return repositoryURI;
	}
	public void setRepositoryURI(String repositoryURI) {
		this.repositoryURI = repositoryURI;
	}
	public String getContextStrategy() {
		return contextStrategy;
	}
	public void setContextStrategy(String contextStrategy) {
		this.contextStrategy = contextStrategy;
	}
	public String getRepositoryBaseURI() {
		return repositoryBaseURI;
	}
	public void setRepositoryBaseURI(String repositoryBaseURI) {
		this.repositoryBaseURI = repositoryBaseURI;
	}
}
