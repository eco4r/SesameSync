package org.hbz.eco4r.configfiguration;

import static org.hbz.eco4r.configfiguration.HarvesterVocabulary.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hbz.eco4r.configfiguration.Configuration;
import org.hbz.eco4r.configfiguration.Property;
import org.joda.time.DateTime;
import org.joda.time.Instant;

public class HarvesterConfiguration {

	private String baseURL;
	private String metadataPrefix;
	private String set;
	private Date from;
	private Date until;
	private String outdir;
	private boolean splitBySet;
	private String zipName;
	private String zDir;
	private boolean writeHeaders;
	private boolean harvestAll;
	private boolean harvestAllIfNoDeletedRecord;
	private String dateFilePath;
	private boolean needsPostProcessing;
	private Configuration config;

	
	public HarvesterConfiguration(String prefix, Configuration config) {
		List<Property> props = config.getProperties();
		this.setConfig(config);
		
		for (Property prop : props) {
			String key = prop.getKey().trim();
			if (key.contains(prefix)) {
				if (key.contains(BASE_URI)){
					String value = prop.getValues().get(0).trim();
					this.setBaseURL(value);
				}
				if (key.contains(METADATA_PREFIX)){
					String value = prop.getValues().get(0).trim();
					this.setMetadataPrefix(value);
				}
				if (key.contains(SET)){
					String value = prop.getValues().get(0).trim();
					this.setSet(value);
				}
				if (key.contains(FROM)) {
					String value = prop.getValues().get(0).trim();
					if (value != null && !value.isEmpty()) {
						Date date = this.string2Date(value);
						this.setFrom(date);
					}
					else {
						String path = this.getDateFile(); 
						if (path != null && !path.isEmpty()) {
							this.setDateFilePath(path);
							this.setFrom(this.getDateFromFile(this.dateFilePath));
						}
						else {
							this.setFrom(null);
						}
					}
						
				}
				if (key.contains(UNTIL)) {
					String value = prop.getValues().get(0).trim();
					if (value != null && !value.isEmpty()) {
						Date date = this.string2Date(value);
						this.setUntil(date);
					}
					else 
						this.setUntil(this.getDateFromFile(this.dateFilePath));
				}
				if (key.contains(OUTDIR)){
					String value = prop.getValues().get(0).trim();
					this.setOutdir(value);
				}
				if (key.contains(SPLIT_BY_SET)){
					String value = prop.getValues().get(0).trim();
					this.setSplitBySet(Boolean.parseBoolean(value));
				}
				if (key.contains(ZIP_NAME)){
					String value = prop.getValues().get(0).trim();
					this.setZipName(value);
				}
				if (key.contains(ZIP_DIR)){
					String value = prop.getValues().get(0).trim();
					this.setzDir(value);
				}
				if (key.contains(WRITE_HEADERS)){
					String value = prop.getValues().get(0).trim();
					this.setWriteHeaders(Boolean.parseBoolean(value));
				}
				if (key.contains(HARVEST_ALL)){
					String value = prop.getValues().get(0).trim();
					this.setHarvestAll(Boolean.parseBoolean(value));
				}
				if (key.contains(HARVEST_ALL_IF_NO_DELETED_RECORD)){
					String value = prop.getValues().get(0).trim();
					this.setHarvestAllIfNoDeletedRecord(Boolean.parseBoolean(value));
				}
				if (key.contains(DATE_FILE_PATH)){
					String value = prop.getValues().get(0).trim();
					this.setDateFilePath(value);
				}
				if (key.contains(NEEDS_POSTPROCWESSING)) {
					boolean value = Boolean.parseBoolean(prop.getValues().get(0).trim());
					this.setNeedsPostProcessing(value);
				}
			}
		}
	}

	private String getDateFile() {
		String dateFile = "";
		List<Property> props = this.config.getProperties();
		for (Property prop : props) {
			String key = prop.getKey();
			if (key.contains("dateFilePath"))
				dateFile = prop.getValues().get(0);
				
		}
		return dateFile;
	}

	private Date getDateFromFile(String dateFilePath) {
		Date date = null;
		File file = new File(dateFilePath);
		try {
			if (file.exists()){
				Scanner scanner = new Scanner(file);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					DateTime dateTime = DateTime.parse(line);
					Instant ist = new Instant(dateTime);
					date = ist.toDate();
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		return date;
	}

	private Date string2Date(String value) {
		
		DateTime dateTime = DateTime.parse(value);
		Instant ist = new Instant(dateTime);
		Date date = ist.toDate();
		
		return date;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getMetadataPrefix() {
		return metadataPrefix;
	}

	public void setMetadataPrefix(String metadataPrefix) {
		this.metadataPrefix = metadataPrefix;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getUntil() {
		return until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}

	public String getOutdir() {
		return outdir;
	}

	public void setOutdir(String outdir) {
		this.outdir = outdir;
	}

	public boolean isSplitBySet() {
		return splitBySet;
	}

	public void setSplitBySet(boolean splitBySet) {
		this.splitBySet = splitBySet;
	}

	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public String getzDir() {
		return zDir;
	}

	public void setzDir(String zDir) {
		this.zDir = zDir;
	}

	public boolean isWriteHeaders() {
		return writeHeaders;
	}

	public void setWriteHeaders(boolean writeHeaders) {
		this.writeHeaders = writeHeaders;
	}

	public boolean isHarvestAll() {
		return harvestAll;
	}

	public void setHarvestAll(boolean harvestAll) {
		this.harvestAll = harvestAll;
	}

	public boolean isHarvestAllIfNoDeletedRecord() {
		return harvestAllIfNoDeletedRecord;
	}

	public void setHarvestAllIfNoDeletedRecord(boolean harvestAllIfNoDeletedRecord) {
		this.harvestAllIfNoDeletedRecord = harvestAllIfNoDeletedRecord;
	}

	public String getDateFilePath() {
		return dateFilePath;
	}

	public void setDateFilePath(String dateFilePath) {
		this.dateFilePath = dateFilePath;
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public boolean isNeedsPostProcessing() {
		return needsPostProcessing;
	}

	public void setNeedsPostProcessing(boolean needsPostProcessing) {
		this.needsPostProcessing = needsPostProcessing;
	}
}
