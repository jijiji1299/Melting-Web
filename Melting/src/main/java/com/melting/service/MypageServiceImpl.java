package com.melting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melting.dao.MypageDAO;
import com.melting.domain.Board;
import com.melting.domain.Member;
import com.melting.domain.Reply;

@Service
public class MypageServiceImpl implements MypageService {
	
	@Autowired
	private MypageDAO mypageDao;

	@Override
	public List<Board> mywrite(String membername) {
		List<Board> list = mypageDao.mywrite(membername);
		return list;
	}

	@Override
	public List<Reply> myreply(String membername) {
		List<Reply> list = mypageDao.myreply(membername);
		return list;
	}

	@Override
	public int updatename(Member member) {
		int result = mypageDao.updatename(member);
		return result;
	}

	@Override
	public int updateMembername(Member member) {
		int result =mypageDao.updateMembername(member);
		return result;
	}

	@Override
	public int updateBoardMembername(Member member) {
		int result =mypageDao.updateBoardMembername(member);
				return result;
	}

	@Override
	public int updateReplyMembername(Member member) {
		int result =mypageDao.updateReplyMembername(member);
				return result;
	}

	@Override
	public void deleteMember(Member member) {
		mypageDao.deleteMember(member);
		
	}

	@Override
	public void deleteBoardMember(Member member) {
		mypageDao.deleteBoardMember(member);
		
	}

	@Override
	public void deleteReplyMember(Member member) {
		mypageDao.deleteReplyMember(member);
	}



}

