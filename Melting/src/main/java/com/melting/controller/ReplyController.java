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
	public String replyWrite(Reply reply, Board board) {
		int result = replyService.writeReply(reply);
		System.out.println(reply);
		return "redirect:/read?boardseq="+board.getBoardseq();
	}
	
	/*댓글 삭제*/
	@GetMapping("/reply/replydelete")
	public String replydelete(int replyseq, Model model, Authentication authentication) {
		int result = replyService.deleteReply(replyseq);
		
		// 유저이름 불러오기 (membername)
		if (authentication != null) {
			String username = authentication.getName();
			Member member = memberService.getMemberUsername(username);
			String membername = member.getMembername();
			model.addAttribute("membername", membername);
		}
		
		return "redirect:/";
	}
	
	
//	@PostMapping("reply/replywrite")
//	@ResponseBody
//	public String replyWrite(Reply reply, @AuthenticationPrincipal UserDetails user) {
//		System.out.println(reply);
//		reply.setMemberid(user.getUsername()); // 로그인 후 수정
//		
//		int result = replyService.writeReply(reply);
//		if(result == 1) {
//			return "OK";	
//		}
//		return "Fail";
//	}
//	
//	@GetMapping("/reply/replylist")
//	@ResponseBody
//	public List<Reply> replylist(int boardseq) {
//		List<Reply> replylist = replyService.listReply(boardseq);
//		
//		return replylist;
//	}
//	
//	
//	@GetMapping("/reply/replydelete")
//	@ResponseBody
//	public String replydelete(int replyseq) {
//		int result = replyService.deleteReply(replyseq);
//		
//		if(result == 1) {
//			return "OK";
//		}
//		return "Fail";
//	}

}
