package com.melting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.melting.domain.Board;
import com.melting.domain.Member;
import com.melting.domain.Reply;
import com.melting.service.BoardService;
import com.melting.service.MemberService;
import com.melting.service.MypageService;

@Controller
public class MypageController {
	
	@Autowired
	MypageService mypageService;
	
	private MemberService memberService;
	private BoardService boardService;

	public MypageController(MemberService memberService, BoardService boardService) {
		this.memberService = memberService;
		this.boardService = boardService;
	}
	
	/*마이페이지 화면 요청*/
	@GetMapping("/mypage")
	public String mypage(Authentication authentication, Model model) {
		
		// 유저이름 불러오기 (membername)
		if (authentication != null) {
			String username = authentication.getName();
			Member member = memberService.getMemberUsername(username);
			String membername = member.getMembername();
			String memberid = member.getMemberid();
			
			model.addAttribute("membername", membername);
			model.addAttribute("memberid", memberid);
			
			System.out.println("memberid는" + memberid);
			
		}
		
		return "/mypage/mypage";
	}
	
	/*내가 쓴 글 확인하기*/
	@GetMapping("/mypage/mywrite")
	public String mywrite(String membername, Model model, Authentication authentication) {
		List<Board> list = mypageService.mywrite(membername);
		model.addAttribute("list", list);
		
		// 유저이름 불러오기 (membername)
		if (authentication != null) {
			String username = authentication.getName();
			Member member = memberService.getMemberUsername(username);
			String membername2 = member.getMembername();
			model.addAttribute("membername", membername2);
		}
		
		return "/mypage/mywrite";
	}
	
	/*내가 쓴 댓글 확인하기*/
	@GetMapping("/mypage/myreply")
	public String myreply(String membername, Model model, Authentication authentication) {
		List<Reply> list = mypageService.myreply(membername);
		model.addAttribute("list", list);
		System.out.println("내가 쓴 댓글 :" + list);
		
		
		
		// 유저이름 불러오기 (membername)
		if (authentication != null) {
			String username = authentication.getName();
			Member member = memberService.getMemberUsername(username);
			String membername2 = member.getMembername();
			model.addAttribute("membername", membername2);
		}
		
		return "/mypage/myreply";
	}
	
	
	/*닉네임 변경 처리*/
	@PostMapping("/mypage/updatename")
	public String updatename(Member member, Board board, Reply reply, 
			RedirectAttributes rttr, Model model) {
		
		int result1 = mypageService.updateMembername(member);
		int result2 = mypageService.updateBoardMembername(board);
		int result3 = mypageService.updateReplyMembername(reply);
		
		
//		rttr.addAttribute("memberid", member.getMemberid());
//		
//		String membername = member.getMembername();
//		String memberid = member.getMemberid();
//		
//		model.addAttribute("membername", membername);
//		model.addAttribute("memberid", memberid);
		model.addAttribute("Member", member);
		
		
		return "redirect:/";
	}
	
//	/*회원 탈퇴 처리*/
//	@PostMapping("/mypage/deletemember")
//	public String deletemember(Member member, Board board, Reply reply) {
//		
//		mypageService.deletemember(member);
//		mypageService.deletemember(board);
//		mypageService.deletemember(reply);
//		
//		return "redirect:/main";
//	}
	
	
}

