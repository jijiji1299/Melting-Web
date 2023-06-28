package com.melting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.melting.domain.Board;
import com.melting.domain.Member;
import com.melting.domain.Reply;
import com.melting.service.MemberService;
import com.melting.service.ReplyService;

@Controller
public class ReplyController {
	private MemberService memberService;
	
	@Autowired
	ReplyService replyService;
	
	public ReplyController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
	/*댓글 달기*/
	@PostMapping("/reply/replywrite")
	public String replyWrite(Reply reply, Board board, Authentication authentication, Model model, int boardseq) {
		int result = replyService.writeReply(reply);
		
		// 유저이름 불러오기 (membername)
		if (authentication != null) {
			String username = authentication.getName();
			Member member = memberService.getMemberUsername(username);
			String membername = member.getMembername();
			String memberid = member.getMemberid();
			
			model.addAttribute("membername", membername);
			model.addAttribute("memberid", memberid);
		}
		
		// 댓글수 증가
		replyService.updateReplyCount(boardseq);
		
		return "redirect:/read?boardseq="+board.getBoardseq();
	}
	
	/*댓글 삭제*/
	@PostMapping("/reply/replydelete")
	public String replydelete(int replyseq, Board board, Model model, Authentication authentication, int boardseq) {
		int result = replyService.deleteReply(replyseq);
		
		// 유저이름 불러오기 (membername)
		if (authentication != null) {
			String username = authentication.getName();
			Member member = memberService.getMemberUsername(username);
			String membername = member.getMembername();
			model.addAttribute("membername", membername);
		}
		
		// 댓글수 감소
		replyService.downReplyCount(boardseq);
		
		return "redirect:/read?boardseq="+board.getBoardseq();
	}
	
	

}
