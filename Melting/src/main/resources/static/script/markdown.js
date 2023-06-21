$(function() {
    var editor = new toastui.Editor({
        el: document.querySelector("#editor"),
        initialEditType: "markdown",
        previewStyle: "vertical",
        height: "300px"
    });

    // 게시글 내용을 hidden 필드에 설정하여 전송
    $("form").submit(function() {
        var markdownContent = editor.getMarkdown();
        $("#boardtxt").val(markdownContent);
    });
});