package org.hbz.eco4r.configfiguration;

public class DataModelVocabulary {

	public static final String FABIO_NS = "http://purl.org/spar/fabio/";
	
	// Publication Types
	public static final String FABIO_DOCTORAL_THESIS = "DoctoralThesis";
	public static final String FABIO_CONFERENCE_PROCEEDINGS = "ConferenceProceedings";
	public static final String FABIO_JOURNAL_ARTICLE = "JournalArticle";
	public static final String FABIO_BOOK_CHAPTER = "BookChapter";
	public static final String FABIO_BOOK = "Book";
	public static final String FABIO_REPORT_DOCUMENT = "ReportDocument";
	public static final String FABIO_RESEARCH_PAPER = "ResearchPaper";
	public static final String FABIO_CONFERENCE_PAPER = "ConferencePaper";
	
	public static final String[] FABIO_PUBTYPES = new String[]{
		FABIO_DOCTORAL_THESIS, 
		FABIO_CONFERENCE_PROCEEDINGS, 
		FABIO_JOURNAL_ARTICLE, 
		FABIO_BOOK_CHAPTER, 
		FABIO_BOOK, 
		FABIO_REPORT_DOCUMENT, 
		FABIO_RESEARCH_PAPER, 
		FABIO_CONFERENCE_PAPER
	};
	
	public static final String[] FABIO_PUBTYPE_URIS = new String[] {
		FABIO_NS + FABIO_DOCTORAL_THESIS, 
		FABIO_NS + FABIO_CONFERENCE_PROCEEDINGS, 
		FABIO_NS + FABIO_JOURNAL_ARTICLE, 
		FABIO_NS + FABIO_BOOK_CHAPTER, 
		FABIO_NS + FABIO_BOOK, 
		FABIO_NS + FABIO_REPORT_DOCUMENT, 
		FABIO_NS + FABIO_RESEARCH_PAPER, 
		FABIO_NS + FABIO_CONFERENCE_PAPER
	};
}
