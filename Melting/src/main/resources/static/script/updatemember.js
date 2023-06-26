function checkNameLength() {
    let nameInput = document.getElementById("membername");
    let checkResult = document.getElementById("checkResult");

    if (nameInput.value.length < 2 || nameInput.value.length > 5) {
        checkResult.textContent = "닉네임은 2자에서 5자 사이어야 합니다.";
    } else {
        checkResult.textContent = "";
    }
}

function checkNameDuplicate() {
    let nameInput = document.getElementById("membername");
    let checkResult = document.getElementById("checkResult");

    if (nameInput.value.trim().length === 0) {
        checkResult.textContent = "";
        return;
    }

    $.ajax({
        method: "GET",
        url: "nameCheck",
        data: { "membername": nameInput.value.trim() },
        success: function(resp) {
            if (resp === "FAIL") {
                checkResult.textContent = "이미 사용중인 닉네임입니다.";
            } else {
                checkResult.textContent = "";
            }
        }
    });
}

function validateNickname() {
    let nameInput = document.getElementById("membername");
    let checkResult = document.getElementById("checkResult");
    let submitButton = document.getElementById("submitButton");

    if (nameInput.value.trim().length === 0 || checkResult.textContent !== "") {
		submitButton.disabled = true; // 유효성 검사 실패 시 버튼 비활성화
        return false; // 폼 제출 중단
    }

	submitButton.disabled = false; // 유효성 검사 통과 시 버튼 활성화
    return true; // 폼 제출
}
