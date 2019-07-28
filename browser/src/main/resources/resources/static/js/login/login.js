function check_login()
{
    var param = $(".login-form").serialize();
    $.ajax({
       url:"/auth/form",
       type:"post",
       data:param,
       success:function(data){
            window.location.href = "index.html";
       },
        error:function(data){
           var data = eval("(" + data.responseText + ")");
           alert(data.content);
            $("#login_form").removeClass('shake_effect');
            setTimeout(function()
            {
                $("#login_form").addClass('shake_effect')
            },1);
        }
    });
}
function check_register(){
    var param = $(".sms-login-form").serialize();
    $.ajax({
        url:"/auth/sms",
        type:"post",
        data:param,
        success:function(data){
            window.location.href = "index.html";
        },
        error:function(data){
            var data = eval("(" + data.responseText + ")");
            alert(data.content);
            $("#login_form").removeClass('shake_effect');
            setTimeout(function()
            {
                $("#login_form").addClass('shake_effect')
            },1);
        }
    });
}
$(function(){
    $("#create").click(function(){
        check_register();
        return false;
    })
    $("#login").click(function(){
        check_login();
        return false;
    })
    $('#smsLogin').click(function () {
        $('form').animate({
            height: 'toggle',
            opacity: 'toggle'
        }, 'slow');
    });
    $('#normalLogin').click(function () {
        $('form').animate({
            height: 'toggle',
            opacity: 'toggle'
        }, 'slow');
    });
    
    $("#sendsms").click(function () {
        $.ajax({
            url:"/code/sms",
            type:"get",
            data:{tel:$("#telInput").val()},
            success:function(data){
                alert("验证码已发送");
            },
            error:function(data){
                var data = eval("(" + data.responseText + ")");
                alert(data.content);
                $("#login_form").removeClass('shake_effect');
                setTimeout(function()
                {
                    $("#login_form").addClass('shake_effect')
                },1);
            }
        });
    });
})