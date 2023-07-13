package com.melting.crawling;

import java.util.List;

import com.melting.domain.Crawling;

public interface CrawlingGameService {
	
	public List<Crawling> getDcInsideGameCrawlingData();
	
	public List<Crawling> getPpomppuGameCrawlingData();
	
	public List<Crawling> getTheqooGameCrawlingData();
	
}
