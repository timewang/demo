<!DOCTYPE html>
<html lang="zh-CN">
<meta name="viewport"
      content="width=device-width, initial-scale=1, maximum-scale=1,user-scalable=no">
<head>
    <script src="${base}/script/jquery/jquery-1.10.2.min.js"></script>
    <script src="${base}/script/bootstrap/js/bootstrap.min.js"></script>
    <script src="${base}/script/bootstrap/js/jquery.pin.js"></script>
    <script src="${base}/script/bootstrap/js/bootstrapValidator.min.js"></script>
    <link href="${base}/script/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${base}/script/bootstrap/css/bootstrapValidator.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <div class="page-header">
                <h2>登录</h2>
            </div>

            <form id="signupForm" method="post" class="form-horizontal" action="${base}/authcenter" >
                <input type="hidden" name="redirectUrl" value="${(redirectUrl)!}">
                <div class="form-group">
                    <label class="col-md-3 control-label control-label-farmer">用户名</label>
                    <div class="col-md-5">
                        <input type="text" class="form-control input-lg" name="loginname" id="loginname"/>
                        <span class="help-block">请使用邮箱/手机号</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label control-label-farmer">密码</label>
                    <div class="col-md-5">
                        <input type="password" class="form-control input-lg" name="password" id="password"/>
                        <span id="passwordInfo" class="help-block">密码错误</span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-5 col-md-offset-3">
                        <span class="help-block"><a href="">忘记密码？</a></span>
                        <button id ="loginBtn" type="submit" class="btn btn-info btn-lg btn-block">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    var form = $('#signupForm');


    $(document).ready(function() {
        form.bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                loginname: {
                    validators: {
                        notEmpty: {
                            message: '请输入用户名'
                        }/*,
                        remote:{
                            url:'../user/validateUser.do?r='+Math.random(),
                            type: 'POST',
                            delay: 2000,
                            message: '用户名不存在'
                        }*/
                    }
                },
                password: {
                    feedbackIcons: false,
                    validators: {
                        notEmpty: {
                            message: '请输入密码'
                        }
                    }
                }
            }
        }).
            //ajax submit
                on('success.form.bv', function(e) {
                   // setTimeout(function(){},10000);
                    // Prevent form submission
                    e.preventDefault();

                    // Get the form instance
                    var $form = $(e.target);

                    // Get the BootstrapValidator instance
                    var bv = $form.data('bootstrapValidator');
                    $.post("${base}/authcenter", $form.serialize(), function(result) {
                        if(result.status == "success"){
                            alert(result);
                            //window.location.href = redirectUrl;
                        }
                    }, 'json');
                }).
            //设置按钮一直可用
                on('status.field.bv', function(e, data) {
                    // I don't want to add has-success class to valid field container
                    data.element.parents('.form-group').removeClass('has-success');
                    // I want to enable the submit button all the time
                    data.bv.disableSubmitButtons(false);
                });

        // Enable the password/confirm password validators if the password is not empty
//     $('#signupForm').find('[name="password"]').on('keyup', function() {
//         var isEmpty = $(this).val() == '';
//          $('#signupForm').bootstrapValidator('enableFieldValidators', 'password', !isEmpty);
//         if ($(this).val().length == 1) {
//             $('#signupForm').bootstrapValidator('validateField', 'password');
//         }
//     });
        //自动隐藏密码错误提示
        $('#password').on('focus keyup',function(){
            $('#passwordInfo').hide();
            form.data('bootstrapValidator').updateStatus('password','VALID','isEmpty');
        });
        //初始化时隐藏错误提示
        $('#passwordInfo').hide();

        //禁止bv默认的表单提交
        form.on('keypress',function(e){
            if(e.keyCode == 13){
                e.preventDefault();
            }

        });

    });

    //重写回车提交
    form.on('keypress',function(e){
        if(e.keyCode == 13){
            submit();
        }
    });


</script>
</body>
</html>