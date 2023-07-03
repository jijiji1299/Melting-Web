package com.melting.crawling;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.melting.dao.CrawlingDAO;
import com.melting.domain.Crawling;

@Service
public class CrawlingServiceImpl implements CrawlingService {
	

	@Autowired
	CrawlingDAO crawlingDao;
	
	int count = 10;

	@Override
	public List<Crawling> getDcSearchCrawlingData() {
		List<Crawling> crawlingDataList = new ArrayList<>();
		
		try {
			
			String dcUrl = "https://www.dcinside.com/";
            Document document = Jsoup.connect(dcUrl).get();

            Elements titles = document.select(".rank_txt");
            Elements links = document.select(".busygall");
            
            int count = Math.min(10, titles.size());
            for (int i = 0; i < count; i++) {
                Element titleElement = titles.get(i);
                String title = titleElement.text();

                Element linkElement = links.get(i);
                String link = linkElement.attr("href");

                Crawling crawling = Crawling.builder()
                        .title(title)
                        .link(link)
                        .build();

                crawlingDataList.add(crawling);
                
            }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return crawlingDataList;
	}

	@Override
	public List<Crawling> getHitCrawlingData() {
		List<Crawling> crawlingDataList = new ArrayList<>();
		
		try {
			
			String dcUrl = "https://www.dcinside.com/";
            Document document = Jsoup.connect(dcUrl).get();

            Elements titles = document.select(".txt_box .tit");
            Elements images = document.select(".img_box img");
            Elements links = document.select(".link_thumb.main_log");
            
            int count = Math.min(4, images.size());
            for (int i = 0; i < count; i++) {
            	
            	Element titleElement = titles.get(i);
                String title = titleElement.text();
            	
                Element imageElement = images.get(i);
                String image = imageElement.attr("src");
                
                Element linkElement = links.get(i);
                String link = linkElement.attr("href");

                Crawling crawling = Crawling.builder()
                		.title(title)
                        .image(image)
                        .link(link)
                        .build();

                crawlingDataList.add(crawling);
                
            }
            
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return crawlingDataList;
	}


	@Override
	public List<Crawling> getCrawlingList() {
		List<Crawling> list = crawlingDao.getCrawlingList();
		return list;
	}


	@Override
	public List<Crawling> getViewscntSortedList() {
		List<Crawling> list = crawlingDao.getViewscntSortedList();
		return list;
	}


	@Override
	public List<Crawling> getLikecntSortedList() {
		List<Crawling> list = crawlingDao.getLikecntSortedList();
		return list;
	}


	@Override
	public List<Crawling> getReplycntSortedList() {
		List<Crawling> list = crawlingDao.getReplycntSortedList();
		return list;
	}

	@Override
	public List<Crawling> getViewsSortedBestList() {
		List<Crawling> list = crawlingDao.getViewsSortedBestList();
		return list;
	}

	@Override
	public List<Crawling> getViewsSortedHumorList() {
		List<Crawling> list = crawlingDao.getViewsSortedHumorList();
		return list;
	}

	@Override
	public List<Crawling> getViewsSortedGameList() {
		List<Crawling> list = crawlingDao.getViewsSortedGameList();
		return list;
	}

	@Override
	public List<Crawling> getViewsSortedSportsList() {
		List<Crawling> list = crawlingDao.getViewsSortedSportsList();
		return list;
	}

	@Override
	public List<Crawling> search(String searchword) {
		List<Crawling> list = crawlingDao.search(searchword);
		return list;
	}






    
	
}

