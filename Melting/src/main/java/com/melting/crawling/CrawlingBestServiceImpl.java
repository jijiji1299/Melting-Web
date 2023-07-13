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
public class CrawlingBestServiceImpl implements CrawlingBestService {
	
	@Autowired
	CrawlingDAO crawlingDao;
	
	int count = 20;
	
	/*디시 BEST*/
	@Scheduled(fixedDelay = 1800000)
	public List<Crawling> getDcInsideBestCrawlingData() {
        List<Crawling> crawlingDataList = new ArrayList<>();
        String site = "dcinside-best";
        String sort = "best";
        
        try {
        	
            String dcUrl = "https://www.dcinside.com/";
            Document document = Jsoup.connect(dcUrl)
            		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
            		.get();

            Elements titles = document.select("div.box.besttxt > p");
            Elements replycnts = document.select("div.box.besttxt > span");
            Elements kinds = document.select("div.box.best_info > span.name");
            Elements links = document.select("div.time_best .main_log");
            
            Math.min(count, titles.size());
            for (int i = 0; i < count; i++) {
                Element titleElement = titles.get(i);
                String title = titleElement.text();

                Element replycntElement = replycnts.get(i);
                String replycnt1 = replycntElement.text().replace("[", "").replace("]", "");
                
                Element kindElement = kinds.get(i);
                String kind = kindElement.text();
                
                Element linkElement = links.get(i);
                String link = linkElement.attr("href");
                
                // 게시물 페이지로 접속
                Document postDocument = Jsoup.connect(link)
                		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                        .referrer(dcUrl)
                		.get();	
                
                
                Element membernameElement = postDocument.selectFirst(".nickname");
                String membername = (membernameElement != null) ? membernameElement.text() : "작성자 비공개";

	
                Element likecntElement = postDocument.selectFirst(".gall_reply_num");
                if (likecntElement == null) {
                    continue;
                }
                String likecnt1 = likecntElement.text().replace("추천", "").trim();
                
                
                Element viewscntElement = postDocument.selectFirst(".gall_count");
                String viewscnt1 = viewscntElement.text().replace("조회", "").trim();
                
                Element regdateElement = postDocument.selectFirst(".gall_date");
                String regdate = regdateElement.text().replace(".", "-");
                regdate = regdate.substring(2, regdate.length() - 3);
                

                int likecnt;	
                try {
                    likecnt = Integer.parseInt(likecnt1);
                } catch (NumberFormatException e) {
                    continue;
                }
                
                int replycnt = Integer.parseInt(replycnt1);
                int viewscnt = Integer.parseInt(viewscnt1);
                
                
                Crawling crawling = Crawling.builder()
                		.site(site)
                        .title(title)
                        .replycnt(replycnt)
                        .kind(kind)
                        .link(link)
                        .membername(membername)
                        .likecnt(likecnt)
                        .viewscnt(viewscnt)
                        .regdate(regdate)
                        .sort(sort)
                        .build();

                crawlingDataList.add(crawling);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < count; i++) {
            Crawling crawling = crawlingDataList.get(i);
            crawlingDao.saveCrawlingData(crawling);
        }
        
        int rowcount = crawlingDao.countCrawlingData(site);
        
        if (rowcount > count) {
			crawlingDao.deleteOldData(site);
		}
        
        return crawlingDataList;
    }
	

	/*뽐뿌 BEST*/
	@Scheduled(fixedDelay = 600000)
    public List<Crawling> getPpomppuBestCrawlingData() {
        List<Crawling> crawlingDataList = new ArrayList<>();
        String site = "ppomppu-best";
        String sort = "best";
        
        try {
        	
            String ppomppuUrl = "https://www.ppomppu.co.kr/hot.php?category=2";
            Document document = Jsoup.connect(ppomppuUrl)
            		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
            		.get();

            Elements titles = document.select(".line .title");
            Elements replycnts = document.select(".list_comment2");
            Elements kinds = document.select(".board_left a");
            Elements links = document.select(".line .title");	
            Elements membernames = document.select(".name");
            Elements likecnts = document.select("table.board_table tr.line td:nth-child(6)");
            Elements viewscnts = document.select("table.board_table tr.line td:nth-child(7)");
            Elements regdates = document.select("table.board_table tr.line td:nth-child(5)");

            
            Math.min(count, titles.size());
            for (int i = 0; i < count; i++) {
                Element titleElement = titles.get(i);
                String title = titleElement.text();

                Element replycntElement = replycnts.get(i);
                String replycnt1 = replycntElement.text();
                
                Element kindElement = kinds.get(i+1);
                String kind = kindElement.text();
                
                Element linkElement = links.get(i);
                String link = "https://www.ppomppu.co.kr"+linkElement.attr("href");
                
                Element membernameElement = membernames.get(i);
                String membername = membernameElement.text();
                
                Element likecntElement = likecnts.get(i);
                String likecnt1 = likecntElement.text();
                
                int hyphenIndex = likecnt1.indexOf("-");
                if (hyphenIndex != -1) {
                    likecnt1 = likecnt1.substring(0, hyphenIndex).trim();
                }
                
                Element viewscntElement = viewscnts.get(i);
                String viewscnt1 = viewscntElement.text();
                
                Element regdateElement = regdates.get(i);
                String regdate = regdateElement.text().replace("/", "-");

                if (regdate.contains(":")) {
                    regdate = regdate.substring(0, regdate.length() - 3);
                }
                
                
            	// 게시물 페이지로 접속
                Document postDocument = Jsoup.connect(link)
                		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                		.referrer(ppomppuUrl)
                		.get();
                
                int likecnt = Integer.parseInt(likecnt1);
                int replycnt = Integer.parseInt(replycnt1);
                int viewscnt = Integer.parseInt(viewscnt1);
                
                Crawling crawling = Crawling.builder()
                		.site(site)
                        .title(title)
                        .replycnt(replycnt)
                        .kind(kind)
                        .link(link)
                        .membername(membername)
                        .likecnt(likecnt)
                        .viewscnt(viewscnt)
                        .regdate(regdate)
                        .sort(sort)
                        .build();
                
                crawlingDataList.add(crawling);
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < count; i++) {
            Crawling crawling = crawlingDataList.get(i);
            crawlingDao.saveCrawlingData(crawling);
        }
        
        int rowcount = crawlingDao.countCrawlingData(site);
        
        if (rowcount > count) {
			crawlingDao.deleteOldData(site);
		}
        
        return crawlingDataList;
    }


	/*더쿠 BEST*/
	@Scheduled(fixedDelay = 600000)
	@Override
	public List<Crawling> getTheqooBestCrawlingData() {
		List<Crawling> crawlingDataList = new ArrayList<>();
        String site = "theqoo-best";
        String sort = "best";
        String membername = "무명의 더쿠";
        int likecnt = 0;
        String kind = "HOT";
        
        try {
        	
        	String theqooUrl = "https://theqoo.net/hot?filter_mode=normal";
            Document document = Jsoup.connect(theqooUrl)
            		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
            		.get();

            Elements titles = document.select(".title a:nth-child(1)");
            Elements replycnts = document.select(".replyNum");
            Elements links = document.select(".title a:nth-child(1)");	
            Elements viewscnts = document.select(".m_no");
            
            
            Math.min(count, titles.size());
            for (int i = 0; i < count; i++) {
              Element titleElement = titles.get(i+6);
              String title = titleElement.text();
              
              
              Element replycntElement = replycnts.get(i);
              String replycnt1 = replycntElement.text();
              
              Element linkElement = links.get(i+6);
              String link = "https://theqoo.net"+linkElement.attr("href");
              
              Element viewscntElement = viewscnts.get(i+8);
              String viewscnt1 = viewscntElement.text().replace(",", "").trim();
              
          	// 게시물 페이지로 접속
            Document postDocument = Jsoup.connect(link)
            		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
            		.referrer(theqooUrl)
            		.get();

            
			Element regdateElement = postDocument.selectFirst(".side.fr span");
		    String regdate = regdateElement.text().replace(".", "-");
		    regdate = regdate.substring(2);
	
            
            int replycnt = Integer.parseInt(replycnt1);
            int viewscnt = Integer.parseInt(viewscnt1);
            
            
            Crawling crawling = Crawling.builder()
            		.site(site)
                    .title(title)
                    .kind(kind)
                    .replycnt(replycnt)
                    .link(link)
                    .membername(membername)
                    .viewscnt(viewscnt)
                    .likecnt(likecnt)
                    .regdate(regdate)
                    .sort(sort)
                    .build();
            
            crawlingDataList.add(crawling);
            
      		}
			
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < count; i++) {
            Crawling crawling = crawlingDataList.get(i);
            crawlingDao.saveCrawlingData(crawling);
        }
        
        int rowcount = crawlingDao.countCrawlingData(site);
        
        if (rowcount > count) {
			crawlingDao.deleteOldData(site);
		}
        
        return crawlingDataList;
    }
	
	
	
	/*에펨 BEST*/
	@Scheduled(fixedDelay = 1200000)
	public List<Crawling> getFmKoreaBestCrawlingData() {
        List<Crawling> crawlingDataList = new ArrayList<>();
        String site = "fm-best";
		String sort = "best";
        
        try {
        	
        	// 포텐 터짐 화제순
            String fmKoreaUrl = "https://www.fmkorea.com/index.php?mid=best2&listStyle=webzine&page=1";
            Document document = Jsoup.connect(fmKoreaUrl)
            		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
            		.get();

            Elements titles = document.select(".hotdeal_var8");
            Elements replycnts = document.select(".comment_count");
            Elements kinds = document.select(".category");
            Elements membernames = document.select(".author");
            Elements links = document.select(".hotdeal_var8");

            Math.min(count, titles.size());
            for (int i = 0; i < count; i++) {
                Element titleElement = titles.get(i);
                String title = titleElement.ownText();

                Element replycntElement = replycnts.get(i);
                String replycnt2 = replycntElement.text().replace("[", "").replace("]", "");
                
                Element kindElement = kinds.get(i);
                String kind = kindElement.text();
                
                Element membernameElement = membernames.get(i);
                String membername = membernameElement.text().replace("/", "").trim();
                
                Element linkElement = links.get(i);
                String link = "https://www.fmkorea.com"+linkElement.attr("href");
                
                // 게시물 페이지로 접속
                Document postDocument = Jsoup.connect(link)
                		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                		.referrer(fmKoreaUrl)
                		.get();
	
                Element viewscntElement = postDocument.selectFirst("div.side.fr > span:nth-child(1) > b");
                String viewscnt2 = viewscntElement.text().trim();
                
                Element likecntElement = postDocument.selectFirst("div.side.fr > span:nth-child(2) > b");
                String likecnt2 = likecntElement.text().trim();
                
                Element regdateElement = postDocument.selectFirst(".date.m_no");
                String regdate = regdateElement.text().replace(".", "-").substring(2);
                
                
                int likecnt = Integer.parseInt(likecnt2);	
                int replycnt = Integer.parseInt(replycnt2);
                int viewscnt = Integer.parseInt(viewscnt2);
                
                Crawling crawling = Crawling.builder()
                		.site(site)
                        .title(title)
                        .replycnt(replycnt)
                        .kind(kind)
                        .membername(membername) 
                        .link(link)
                        .likecnt(likecnt)
                        .viewscnt(viewscnt)
                        .sort(sort)
                        .regdate(regdate)
                        .build();
                
                crawlingDataList.add(crawling);
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < count; i++) {
            Crawling crawling = crawlingDataList.get(i);
            crawlingDao.saveCrawlingData(crawling);
            
        }
        
        int rowcount = crawlingDao.countCrawlingData(site);
        
        if (rowcount > count) {
			crawlingDao.deleteOldData(site);
		}
        
        System.out.println(crawlingDataList);
        return crawlingDataList;
    }

}
