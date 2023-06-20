/**
 * jquary ajax : memberid, membername 중복확인 체크 
 */

$(function(){
	$("#memberid").keyup(function() {
		let mid = $(this).val();
		if(mid.trim().length < 3 || mid.trim().length == 0 ) {
			return false;	
		} 
		
		$.ajax({
			method : "GET"
			, url : "idCheck"
			, data : {"memberid" : mid}
			, success : function(resp) {	
				if(resp == "OK") {  
				} else if(resp == "FAIL") {
				}
			}
		})
	})
})

function fn_idChk(){
	$.ajax({
		url : "./idChk",
		type : "GET",
		dataType : "html",
		data : {"memberid" : $("#memberid").val()},
		success : function(data){
			if(data == 1){
				$("#checkResult1").text('✔ 중복된 아이디입니다.');
			}else if(data == 0){
				$("#idChk").attr("value", "Y");
				$("#checkResult1").text('✔ 중복되지 않은 아이디입니다.');
			}
		}
	})
}

 $(function(){
	$("#membername").keyup(function() {
		let mid = $(this).val();
		if(mid.trim().length == 0 ) {
			return false;	
		} 
		
		$.ajax({
			method : "GET"
			, url : "nameCheck"
			, data : {"membername" : mid}
			, success : function(resp) {	
				if(resp == "OK") {  
					$("#checkResult4").text('✔ 사용가능한 닉네임입니다.');
				} else if(resp == "FAIL") {
					$("#checkResult4").text('✔ 이미 사용중인 닉네임입니다.');
				}
			}
		})
	})
})
	
/*function fn_nameChk(){
	$.ajax({
		url : "./nameChk",
		type : "GET",
		dataType : "html",
		data : {"membername" : $("#membername").val()},
		success : function(data){
			if(data == 1){
				$("#checkResult4").text('✔ 중복된 아이디입니다.');
			}else if(data == 0){
				$("#nameChk").attr("value", "Y");
				$("#checkResult4").text('✔ 중복되지 않은 아이디입니다.');
			}
		}
	})
}*/	

