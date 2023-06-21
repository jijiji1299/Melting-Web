/**
 * 업로드 이미지 미리보기
 */

  function previewImage(event) {
    var reader = new FileReader();
    reader.onload = function() {
      var imgElement = '<img src="' + reader.result + '" alt="Image" />';
      var previewDiv = document.getElementById('imagePreview');
      previewDiv.innerHTML = imgElement; // 이미지 태그를 삽입하는 대신, <div>에 HTML 코드를 설정합니다.
    };
    reader.readAsDataURL(event.target.files[0]);
  }
  
  
