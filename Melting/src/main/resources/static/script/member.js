 function validation() {
	 let memberid = document.getElementById("memberid");
	 let memberpwd  = document.getElementById("memberpwd");
	 let memberpwd2  = document.getElementById("memberpwd2");
	 let membername = document.getElementById("membername");
	 let checkResult1 = document.getElementById("checkResult1");
	 let checkResult2 = document.getElementById("checkResult2");
	 let checkResult3 = document.getElementById("checkResult3");
	 let checkResult4 = document.getElementById("checkResult4");
	 
	 if(memberid.value.trim().length < 3 || !memberid.value.includes('@') || memberid.value.trim().length == 0) {
		 checkResult1.textContent = "✔ 형식이 맞지 않습니다.";
		 memberid.focus();	
		 memberid.select();	
		 return false;
	 }
	 checkResult1.textContent = "";
	 
	 
	 if(memberpwd.value.trim().length < 3 || memberpwd.value.trim().length > 16 || memberpwd.value.trim().length == 0) {
		 checkResult2.textContent = "✔ 비밀번호는 최소 4자 이상 입력하세요.";
		 memberpwd.focus();
		 memberpwd.select();
		 return false;
	 }
	 checkResult2.textContent = "";
	 
	 
	 if(memberpwd2.value.trim() !== memberpwd.value.trim()) {	
		 checkResult3.textContent = "✔ 비밀번호를 다시 확인하세요.";
		 memberpwd2.focus();	
		 memberpwd2.select();	
		 return false;
	 }
	 checkResult3.textContent = "";
	 

	 if(membername.value.trim().length < 2 || membername.value.trim().length == 0) {
		 checkResult4.textContent = "✔ 닉네임을 최소 2자 이상 입력하세요.";
		 membername.focus();
		 membername.select();
		 return false;
	 }
	 checkResult4.textContent = "";
	 

	 return true;
 }