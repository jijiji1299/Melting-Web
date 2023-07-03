package com.melting.crawling;

import java.util.List;

import com.melting.domain.Crawling;

public interface CrawlingService {

	public List<Crawling> getDcSearchCrawlingData();

	public List<Crawling> getHitCrawlingData();

	public List<Crawling> getCrawlingList();

	public List<Crawling> getViewscntSortedList();

	public List<Crawling> getLikecntSortedList();
	
	public List<Crawling> getReplycntSortedList();

	public List<Crawling> getViewsSortedBestList();

	public List<Crawling> getViewsSortedHumorList();

	public List<Crawling> getViewsSortedGameList();

	public List<Crawling> getViewsSortedSportsList();

	public List<Crawling> search(String searchword);





}
