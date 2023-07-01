package com.melting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melting.dao.BoardDAO;
import com.melting.domain.Board;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDAO boardDao;
	
	
	@Override
	public int write(Board board) {
		int result = boardDao.insertBoard(board);
		return result;
	}


	@Override
	public Board read(int boardseq) {
		Board board = boardDao.read(boardseq);
		return board;
	}


	@Override
	public int delete(int boardseq) {
		int result = boardDao.delete(boardseq);
		return result;
	}


	@Override
	public int update(Board board) {
		int result = boardDao.update(board);
		return result;
	}


	@Override
	public int updateViewsCount(int boardseq) {
		return boardDao.updateViewsCount(boardseq);
	}


	@Override
	public List<Board> getRecommendList(int boardseq) {
		List<Board> list = boardDao.getRecommendList(boardseq);
		return list;
	}


	@Override
	public List<Board> getAllList() {
		List<Board> list = boardDao.getAllList();
		return list;
	}


	@Override
	public void updateLikeCount(int boardseq) {
		boardDao.updateLikeCount(boardseq);
		
	}


	@Override
	public List<Board> getWritedBestList() {
		List<Board> getWritedBestList = boardDao.getWritedBestList();
		return getWritedBestList;
	}


	@Override
	public List<Board> getWritedHumorList() {
		List<Board> getWritedHumorList = boardDao.getWritedHumorList();
		return getWritedHumorList;
	}


	@Override
	public List<Board> getWritedGameList() {
		List<Board> getWritedGameList = boardDao.getWritedGameList();
		return getWritedGameList;
	}


	@Override
	public List<Board> getWritedSportsList() {
		List<Board> getWritedSportsList = boardDao.getWritedSportsList();
		return getWritedSportsList;
	}







}
