<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看树</title>
    <link rel="stylesheet" href="${base}/script/zTree_v3-master/css/demo.css" type="text/css">
    <link rel="stylesheet" href="${base}/script/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <#--<script type="text/javascript" src="${base}/script/zTree_v3-master/js/jquery-1.4.4.min.js"></script>-->
    <script type="text/javascript" src="${base}/script/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/script/zTree_v3-master/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript">
        var setting = {
            async: {
                enable: true,
                url:"${base}/trees?type=${type}",
                autoParam:["id=pid", "name=n", "level=lv"],
                otherParam:{"otherParam":"zTreeAsyncTest"},
                dataFilter: filter
            }
        };

        function filter(treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            for (var i=0, l=childNodes.length; i<l; i++) {
                childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            }
            return childNodes;
        }

        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting);
        });
    </script>
</head>
<body>
<div class="zTreeDemoBackground left">
    <ul id="treeDemo" class="ztree"></ul>
</div>
</body>
</html>