package com.melting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.melting.domain.Board;
import com.melting.domain.Member;
import com.melting.domain.Reply;

@Mapper
public interface MypageDAO {

	public List<Board> mywrite(String membername);

	public List<Reply> myreply(String membername);

	public int updatename(Member member);

	public int updateMembername(Member member);

	public int updateBoardMembername(Board board);

	public int updateReplyMembername(Reply reply);


}

