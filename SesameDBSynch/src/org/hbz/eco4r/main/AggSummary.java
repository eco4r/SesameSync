package org.hbz.eco4r.main;

import java.util.Arrays;
import java.util.List;

import org.hbz.eco4r.util.BrokerUtils;

public class AggSummary {

	private String aggURI;
	private List<String> remURIs;
	private String dateAccepted;
	private List<String> creators;
	private List<String> pubTypes;
	private List<String> ddcSubjects;
	private String context;
	private String repository;
	private String title;
	private BrokerUtils brokerUtils;
	
	
	public AggSummary(String aggURI, Broker broker) {
		this.setBrokerUtils(broker.getBrokerUtils());
		this.setAggURI(aggURI);
		this.setRemURIs(this.getBrokerUtils().getReMURIs(this.aggURI));
		this.setDateAccepted(this.getBrokerUtils().getDate(this.aggURI));
		this.setCreators(this.getBrokerUtils().getCreators(this.aggURI));
		this.setPubTypes(this.getBrokerUtils().getPubTypesOfAgg(this.aggURI, 
				Arrays.asList(new String[]{"http://www.openarchives.org/ore/terms/"})));
		this.setDdcSubjects(this.getBrokerUtils().getDDCSubjectsOfAgg(this.aggURI));
		this.setContext(this.getBrokerUtils().getContextOfAgg(this.aggURI));
		this.setRepository(this.getBrokerUtils().getRepository(this.aggURI));
		this.setTitle(this.brokerUtils.getTitleOfAgg(this.aggURI));
	}
	
	
	
	public String getAggURI() {
		return aggURI;
	}
	public void setAggURI(String aggURI) {
		this.aggURI = aggURI;
	}
	public String getDateAccepted() {
		return dateAccepted;
	}
	public void setDateAccepted(String dateAccepted) {
		this.dateAccepted = dateAccepted;
	}
	public List<String> getCreators() {
		return creators;
	}
	public void setCreators(List<String> creators) {
		this.creators = creators;
	}
	public List<String> getPubTypes() {
		return pubTypes;
	}
	public void setPubTypes(List<String> pubTypes) {
		this.pubTypes = pubTypes;
	}
	public List<String> getDdcSubjects() {
		return ddcSubjects;
	}
	public void setDdcSubjects(List<String> ddcSubjects) {
		this.ddcSubjects = ddcSubjects;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getRepository() {
		return repository;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BrokerUtils getBrokerUtils() {
		return brokerUtils;
	}
	public void setBrokerUtils(BrokerUtils brokerUtils) {
		this.brokerUtils = brokerUtils;
	}
	public List<String> getRemURIs() {
		return remURIs;
	}
	public void setRemURIs(List<String> remURIs) {
		this.remURIs = remURIs;
	}
}
