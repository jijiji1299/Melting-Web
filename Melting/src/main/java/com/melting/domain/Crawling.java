package com.melting.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
@Builder

public class Crawling {
	
	private int crawlingseq;
	private String title;
	private String kind;
	private String link;
	private String image;
	private String membername;
	private Date created_at;
	private String replycnt1;
	private int replycnt;
	private String likecnt1;
	private int likecnt;
	private String viewscnt1;
	private int viewscnt;
	private String site;
	private String regdate;
	private String regdate1;
	

}
