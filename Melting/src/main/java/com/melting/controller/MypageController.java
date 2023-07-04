package com.melting.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.melting.domain.Board;
import com.melting.domain.Member;
import com.melting.domain.Reply;
import com.melting.service.MemberService;
import com.melting.service.MypageService;

@Controller
public class MypageController {
	
	@Autowired
	MypageService mypageService;
	
	private MemberService memberService;

	public MypageController(MemberService memberService) {
		this.memberService = memberService;
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
	public String updatename(Member member) {
		
		mypageService.updateMembername(member);
		mypageService.updateBoardMembername(member);
		mypageService.updateReplyMembername(member);
		
		return "redirect:/";
	}
	
	/*회원 탈퇴 처리*/
	@PostMapping("/mypage/deletemember")
	public String deletemember(HttpServletRequest request, Member member) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    // 인증되지 않은 상태로 돌아가는 처리
	    if (authentication != null && authentication.isAuthenticated()) {
	        SecurityContextHolder.clearContext();
	        request.getSession().invalidate();
	    }
		
	    // 회원 및 관련 데이터 삭제 로직
		mypageService.deleteMember(member);
		mypageService.deleteBoardMember(member);
		mypageService.deleteReplyMember(member);
		
		return "redirect:/";
	}
	
	
}

