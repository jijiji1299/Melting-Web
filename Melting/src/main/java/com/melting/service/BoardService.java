package com.melting.service;

import java.util.List;

import com.melting.domain.Board;

public interface BoardService {

	public int write(Board board);

	public Board read(int boardseq);

	public int delete(int boardseq);

	public int update(Board board);
	
	public int updateViewsCount(int boardseq);

	public List<Board> getRecommendList(int boardseq);

	public List<Board> getAllList();




}
