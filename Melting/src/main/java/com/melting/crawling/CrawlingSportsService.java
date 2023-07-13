package com.melting.crawling;

import java.util.List;

import com.melting.domain.Crawling;

public interface CrawlingSportsService {

	public List<Crawling> getDcInsideSportsCrawlingData();
	
	public List<Crawling> getPpomppuSportsCrawlingData();
	
	public List<Crawling> getTheqooSportsCrawlingData();
	
	
}
