package com.melting.service;

import java.util.List;

import com.melting.domain.Board;
import com.melting.domain.Member;
import com.melting.domain.Reply;

public interface MypageService {

	public List<Board> mywrite(String membername);

	public List<Reply> myreply(String membername);

	public int updatename(Member member);

	public int updateMembername(Member member);

	public int updateBoardMembername(Board board);

	public int updateReplyMembername(Reply reply);

	
	

}
