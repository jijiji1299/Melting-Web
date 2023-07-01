//package com.melting.crawling;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import com.melting.dao.CrawlingDAO;
//import com.melting.domain.Crawling;
//
//@Service
//public class CrawlingSportsServiceImpl implements CrawlingSportsService {
//	
//	@Autowired
//	CrawlingDAO crawlingDao;
//	
//	int count = 20;
//
//
//	
//	/*디시 Sports*/
//	@Override
//	@Scheduled(fixedDelay = 120000)
//	public List<Crawling> getDcInsideSportsCrawlingData() {
//		List<Crawling> crawlingDataList = new ArrayList<>();
//		
//		String site = "dcinside-sports";
//		String sort = "sports";
//		
//		try {
//			
//			// 해외축구 갤러리
//			String dcUrl = "https://gall.dcinside.com/board/lists?id=football_new8";
//			Document document = Jsoup.connect(dcUrl)
//	    		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
//	    		.get();
//			
//			Elements titles = document.select(".ub-content.us-post .gall_tit.ub-word a:nth-child(1)");
//			Elements links = document.select(".ub-content.us-post .gall_tit.ub-word a:nth-child(1)");
//			Elements viewscnts = document.select(".ub-content.us-post .gall_count");
//			Elements likecnts = document.select(".ub-content.us-post .gall_recommend");
//
//			
//			Math.min(count, titles.size());
//			for (int i = 0; i < count; i++) {
//				Element titleElement = titles.get(i);
//				String title = titleElement.text();
//				
//				Element linkElement = links.get(i);
//				String link = "https://gall.dcinside.com" + linkElement.attr("href");
//				
//				Element viewscntElement = viewscnts.get(i);
//				String viewscnt1 = viewscntElement.text();
//				
//				Element likecntElement = likecnts.get(i);
//				String likecnt1 = likecntElement.text();
//				
//					
//				// 게시물 페이지로 접속
//				Document postDocument = Jsoup.connect(link)
//						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
//						.referrer(dcUrl)
//						.get();	
//                
//                
//                Element replycntElement = postDocument.selectFirst(".gall_comment a");
//                String replycnt1 = replycntElement.text().replace("댓글", "").trim();
//                
//                
//                Element regdateElement = postDocument.selectFirst(".gall_date");
//                String regdate = regdateElement.text().replace(".", "-");
//                regdate = regdate.substring(2, regdate.length() - 3);
//                
//                Element membernameElement = postDocument.selectFirst(".fl .nickname em");
//                String membername = membernameElement.text();
//                
//                Element kindElement = postDocument.selectFirst(".title_headtext");
//                String kind = kindElement.text().replace("[", "").replace("]", "");
//                
//                
//                int viewscnt = Integer.parseInt(viewscnt1);
//                int likecnt = Integer.parseInt(likecnt1);
//                int replycnt = Integer.parseInt(replycnt1);
//                
//				
//				Crawling crawling = Crawling.builder()
//						.site(site)
//						.sort(sort)
//						.title(title)
//						.link(link)
//						.kind(kind)
//						.membername(membername)
//						.regdate(regdate)
//						.replycnt(replycnt)
//						.likecnt(likecnt)
//						.viewscnt(viewscnt)
//						.build();
//        	  
//				crawlingDataList.add(crawling);
//          }
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		for (int i = 0; i < count; i++) {
//			Crawling crawling = crawlingDataList.get(i);
//			crawlingDao.saveCrawlingData(crawling);
//		}
//  
//		int rowcount = crawlingDao.countCrawlingData(site);
//  
//		if (rowcount > count) {
//			crawlingDao.deleteOldData(site);
//		}
//		
//		
//		return crawlingDataList;
//	}
//	
//	
//	
////	/*디시 Sports*/
////	@Override
////	@Scheduled(fixedDelay = 120000)
////	public List<Crawling> getDcInsideSportsCrawlingData() {
////		List<Crawling> crawlingDataList = new ArrayList<>();
////		
////		String site = "dcinside-sports";
////		String sort = "sports";
////
////		try {
////			
////			// 스포츠 개념글
////			String dcUrl = "https://sports.dcinside.com/";
////			Document document = Jsoup.connect(dcUrl)
////	    		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
////	    		.get();
////			
////			Elements titles = document.select(".txt_box .tit");
////			Elements links = document.select(".bgcover a");
////			Elements kinds = document.select(".txt_box .info span");
////			
////			Math.min(count, titles.size());
////			for (int i = 0; i < count; i++) {
////				Element titleElement = titles.get(i);
////				String title = titleElement.text();
////				
////				Element linkElement = links.get(i);
////				String link = linkElement.attr("href");
////				
////				Element kindElement = kinds.get(i*2);
////				String kind = kindElement.text();
////					
////				// 게시물 페이지로 접속
////				Document postDocument = Jsoup.connect(link)
////						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
////						.referrer(dcUrl)
////						.get();	
////				
////				Element membernameElement = postDocument.selectFirst(".nickname");
////				String membername = (membernameElement != null) ? membernameElement.text() : "작성자 비공개";
////				
////                Element likecntElement = postDocument.selectFirst(".gall_reply_num");
////                if (likecntElement == null) {
////                    continue;
////                }
////                String likecnt1 = likecntElement.text().replace("추천", "").trim();
////                
////                
////                Element viewscntElement = postDocument.selectFirst(".gall_count");
////                String viewscnt1 = viewscntElement.text().replace("조회", "").trim();
////                
////                Element replycntElement = postDocument.selectFirst(".gall_comment a");
////                String replycnt1 = replycntElement.text().replace("댓글", "").trim();
////                
////                
////                Element regdateElement = postDocument.selectFirst(".gall_date");
////                String regdate = regdateElement.text().replace(".", "-");
////                regdate = regdate.substring(2, regdate.length() - 3);
////                
////
////                int likecnt;	
////                try {
////                    likecnt = Integer.parseInt(likecnt1);
////                } catch (NumberFormatException e) {
////                    continue;
////                }
////                
////                int viewscnt = Integer.parseInt(viewscnt1);
////                int replycnt = Integer.parseInt(replycnt1);
////                
////				
////				Crawling crawling = Crawling.builder()
////						.site(site)
////						.sort(sort)
////						.title(title)
////						.link(link)
////						.kind(kind)
////						.membername(membername)
////						.regdate(regdate)
////						.replycnt(replycnt)
////						.likecnt(likecnt)
////						.viewscnt(viewscnt)
////						.build();
////        	  
////				crawlingDataList.add(crawling);
////          }
////			
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		
////		for (int i = 0; i < count; i++) {
////			Crawling crawling = crawlingDataList.get(i);
////			crawlingDao.saveCrawlingData(crawling);
////		}
////  
////		int rowcount = crawlingDao.countCrawlingData(site);
////  
////		if (rowcount > count) {
////			crawlingDao.deleteOldData(site);
////		}
////		
////		
////		return crawlingDataList;
////	}
//
//	
//	
//	/*뽐뿌 Sports*/
//	@Override
//	@Scheduled(fixedDelay = 120000)
//	public List<Crawling> getPpomppuSportsCrawlingData() {
//		List<Crawling> crawlingDataList = new ArrayList<>();
//		
//		String site = "ppomppu-sports";
//		String sort = "sports";
//		String kind = "야구포럼";
//		
//		try {
//			
//			// 야구포럼
//			String ppomppuUrl = "https://www.ppomppu.co.kr/zboard/zboard.php?id=baseball";
//			Document document = Jsoup.connect(ppomppuUrl)
//					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
//            		.get();
//			
//			Elements titles = document.select(".list_title");
//            Elements links = document.select(".list_vspace a");	
//            Elements membernames = document.select(".list_name .list_vspace a");
//            Elements regdates = document.select("#revolution_main_table tr td:nth-child(4) nobr");
//            Elements viewscnts = document.select("#revolution_main_table tr td:nth-child(6)");
//
//            Math.min(count, titles.size());
//            for (int i = 0; i < count; i++) {
//                Element titleElement = titles.get(i+1);
//                String title = titleElement.text();
//                
//                Element linkElement = links.get(2*i+4);
//                String link = "https://www.ppomppu.co.kr/zboard/"+linkElement.attr("href");
//                
//                Element membernameElement = membernames.get(i+1);
//                String membername1 = membernameElement.text();
//                String membername;
//                
//                if (membername1.isEmpty()) {
//                	membername = "익명";
//                } else {
//                	membername = membername1;
//                }
//                
//                Element regdateElement = regdates.get(i+2);
//                String regdate = regdateElement.text();
//                regdate = regdate.substring(0, regdate.length() - 3);
//                
//                Element viewscntElement = viewscnts.get(i+3);
//                String viewscnt1 = viewscntElement.text();
//
//                
//                // 게시물 페이지로 접속
//                Document postDocument = Jsoup.connect(link)
//                		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
//                		.referrer(ppomppuUrl)
//                		.get();
//                
//                Element replycntElement = postDocument.selectFirst(".list_comment");
//                String replycnt1;
//                
//                if(replycntElement == null) {
//                	replycnt1 = "0";
//                } else {
//                	replycnt1 = replycntElement.text();
//                }
//                
//                Element likecntElement = postDocument.selectFirst("#vote_list_btn_txt");
//                String likecnt1 = likecntElement.text();
//                		
//                	
//                int likecnt = Integer.parseInt(likecnt1);
//                int replycnt = Integer.parseInt(replycnt1);
//                int viewscnt = Integer.parseInt(viewscnt1);
//                
//                Crawling crawling = Crawling.builder()
//                		.site(site)
//                        .title(title)
//                        .kind(kind)
//                        .link(link)
//                        .membername(membername)
//                        .likecnt(likecnt)
//                        .replycnt(replycnt)
//                        .viewscnt(viewscnt)
//                        .regdate(regdate)
//                        .sort(sort)
//                        .build();
//                
//                crawlingDataList.add(crawling);
//            }
//            
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		for (int i = 0; i < count; i++) {
//            Crawling crawling = crawlingDataList.get(i);
//            crawlingDao.saveCrawlingData(crawling);
//        }
//        
//        int rowcount = crawlingDao.countCrawlingData(site);
//        
//        if (rowcount > count) {
//			crawlingDao.deleteOldData(site);
//		}
//		
//		return crawlingDataList;
//	}
//	
//	
//	
////	/*더쿠 Sports*/
////	@Override
////	@Scheduled(fixedDelay = 120000)
////	public List<Crawling> getTheqooSportsCrawlingData() {
////		List<Crawling> crawlingDataList = new ArrayList<>();
////		
////		String site = "theqoo-sports";
////        String sort = "Sports";
////        String membername = "무명의 더쿠";
////        int likecnt = 0; 
////        String kind = "국내야구";
////        
////        try {
////        	
////        	// 국내야구 - 기아
////        	String theqooUrl = "https://theqoo.net/kbaseball?group_srl=185338643";
////            Document document = Jsoup.connect(theqooUrl)
////            		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
////            		.get();
////            
////            Elements titles = document.select(".title");
////            Elements links = document.select(".title a");
////
////            
////            Math.min(count, titles.size());
////            for (int i = 0; i < count; i++) {
////            	
////            	Element titleElement = titles.get(i+8);
////            	String title = titleElement.text();
////            	
////            	String link;
////            	
////            	
////            	int linksSize = links.size();
////            	
////            	if (linksSize >= 2) {
////                    Element linkElement = links.get(i+8);
////                    link = "https://theqoo.net" + linkElement.attr("href");
////                } else {
////                    continue;
////                }
////            	
////            	Crawling crawling = Crawling.builder()
////            			.site(site)
////            			.sort(sort)
////            			.membername(membername)
////            			.likecnt(likecnt)
////            			.kind(kind)
////            			.title(title)
////            			.link(link)
////            			.build();
////            	
////            	crawlingDataList.add(crawling);
////            	
////            }
////            
////			
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////        
////        for (int i = 0; i < count; i++) {
////            Crawling crawling = crawlingDataList.get(i);
////            crawlingDao.saveCrawlingData(crawling);
////        }
////        
////        int rowcount = crawlingDao.countCrawlingData(site);
////        
////        if (rowcount > count) {
////			crawlingDao.deleteOldData(site);
////		}
////        
////		return crawlingDataList;
////	}
//	
//	
//
//
//}
