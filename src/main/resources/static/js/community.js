
function post() {
    var questionId = $("#question_id").val();
    console.log(questionId);
    var content = $("#comment_content").val();
    $.ajax({
        url:"/comment",
        type:"POST",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success:function(response)
        {
        if (response.code == 200){
            $("#comment_section").hide();
            var  isAccepted = confirm(response.message)
            if (isAccepted) {
                window.open("https://github.com/login/oauth/authorize?client_id=084488a7b311fcc0f1a6&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                window.localStorage.setItem("closable",true);
            }
        } else {
        }
        }
    });
}