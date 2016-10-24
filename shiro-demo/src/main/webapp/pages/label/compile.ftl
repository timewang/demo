<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>写标签</title>
    <script type="text/javascript" src="${base}/script/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/script/bootstrap/js/bootstrap.min.js"></script>
<#--    <script src="${base}/js/component/bootstrap/js/jquery.pin.js"></script>-->
    <script type="text/javascript" src="${base}/script/bootstrap/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="${base}/script/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${base}/script/tmpl/jquery.tmpl.js"></script>

<#--    <script type="text/javascript" src="${base}/js/component/select2/js/select2.full.min.js"></script>

    <script type="text/javascript" src="${base}/js/component/select2/css/select2.css"></script>-->

    <link href="${base}/script/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${base}/script/bootstrap/css/bootstrapValidator.css" rel="stylesheet">
    <link href="${base}/script/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="page-header">
                <h2>随便写点啥</h2>
            </div>
            <form action="${base}/label" method="post" class="form-horizontal" id="label">
                <input name="_method" value="${_method}" type="hidden">
                <input name="id" id="id" value="${(label.id)!}" type="hidden">
                <input name="username" value="wangzhongfu" type="hidden">
                <div class="form-group">
                    <label class="col-md-3 control-label control-label-farmer">*名称</label>
                    <div class="col-md-5">
                        <input type="text" class="form-control input-lg" name="value" id="value" value="${(label.value)!}"/>
                        <span id="acceptorInfo" class="help-block"></span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-5 col-md-offset-3">
                        <button id ="saveBtn" type="submit" class="btn btn-primary btn-lg btn-block">保存</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
</html>