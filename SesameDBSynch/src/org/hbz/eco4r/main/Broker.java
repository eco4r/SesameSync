package org.hbz.eco4r.main;

import java.io.File;

import org.apache.log4j.Logger;
import org.hbz.eco4r.configfiguration.Configuration;
import org.hbz.eco4r.configfiguration.SesameConfiguration;
import org.hbz.eco4r.util.BrokerUtils;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.nativerdf.NativeStore;

public class Broker {

	private static Logger logger = Logger.getLogger(Broker.class);

	private SesameConfiguration sesameConfig;
	private Configuration harvesterConfig;
	private Repository repository;
	private RepositoryConnection connection;
	private BrokerUtils brokerUtils;
	
	public Broker() {
		
	}
	
	public Broker(String sesameConfigFile, String harvesterConfigFile) {
		this.sesameConfig = new SesameConfiguration(new Configuration(sesameConfigFile));
		this.harvesterConfig = new Configuration(harvesterConfigFile);
		
		File repositoryURI = new File(this.sesameConfig.getRepositoryURI());

		try {
			if (repositoryURI.exists()) {
				NativeStore nativeStore = new NativeStore(repositoryURI);
				Repository repository = new SailRepository(nativeStore);
				repository.initialize();
				this.setRepository(repository);
				this.setConnection(repository.getConnection());
				this.setBrokerUtils(new BrokerUtils(this.getConnection(), this.getHarvesterConfig()));
			}
			else {
				logger.error("No such file: " + repositoryURI.getAbsolutePath(), new NoSuchFieldError());
			}
		} 
		catch (RepositoryException e) {
			e.printStackTrace();
		}
	}
	
	public SesameConfiguration getSesameConfig() {
		return sesameConfig;
	}
	public void setSesameConfig(SesameConfiguration sesameConfig) {
		this.sesameConfig = sesameConfig;
	}
	public Configuration getHarvesterConfig() {
		return harvesterConfig;
	}
	public void setHarvesterConfig(Configuration harvesterConfig) {
		this.harvesterConfig = harvesterConfig;
	}
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public RepositoryConnection getConnection() {
		return connection;
	}
	public void setConnection(RepositoryConnection connection) {
		this.connection = connection;
	}
	public BrokerUtils getBrokerUtils() {
		return brokerUtils;
	}
	public void setBrokerUtils(BrokerUtils brokerUtils) {
		this.brokerUtils = brokerUtils;
	}
}
