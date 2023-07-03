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

	public void updateLikeCount(int boardseq);

	public List<Board> getWritedBestList();

	public List<Board> getWritedHumorList();

	public List<Board> getWritedGameList();

	public List<Board> getWritedSportsList();

	public List<Board> search(String searchword);





}
