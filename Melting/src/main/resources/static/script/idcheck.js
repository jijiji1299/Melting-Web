/**
 * jquary ajax를 이용해 id 중복확인 체크 
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
		

