<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编辑树--组织结构编辑</title>
    <link rel="stylesheet" href="${base}/script/zTree_v3-master/css/demo.css" type="text/css">
    <link rel="stylesheet" href="${base}/script/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<#--<script type="text/javascript" src="${base}/script/zTree_v3-master/js/jquery-1.4.4.min.js"></script>-->
    <script type="text/javascript" src="${base}/script/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/script/zTree_v3-master/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript">
        var setting = {
            drag: {
                autoExpandTrigger: true,
                prev: dropPrev,
                inner: dropInner,
                next: dropNext
            },
            edit: {
                enable: true
            },
            async: {
                enable: true,
                url:"${base}/trees?type=${type}",
                autoParam:["id=pid", "name=n", "level=lv"],
                otherParam:{"otherParam":"zTreeAsyncTest"},
                dataFilter: filter
            },
            callback: {
                //beforeDrag: beforeDrag,
                beforeRemove: beforeRemove,
                onRemove:onRemove,
                beforeRename: beforeRename,
                onRename: onRename,
                beforeDrag: beforeDrag,
                beforeDrop: beforeDrop,
                beforeDragOpen: beforeDragOpen,
                onDrag: onDrag,
                onDrop: onDrop,
                onExpand: onExpand,
                //onRightClick: OnRightClick
            }
        };

        var newCount = 1;
        function add(e) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    isParent = e.data.isParent,
                    nodes = zTree.getSelectedNodes(),
                    treeNode = nodes[0];
            var pid = "0";
            var setParent = false;
            if (treeNode) {
                pid = treeNode.id;
                if(!treeNode.isParent){
                    setParent = true;
                }
            }
            $.ajax({
                async : false,
                url : '${base}/'+"tree",
                type : "POST",
                dataType : "json",
                data : {_method: 'POST',"name":"new node","isParent":isParent,"type":"${type}","pid":pid,"setParent":setParent},
                success : function(data) {
                    if (treeNode) {
                        treeNode = zTree.addNodes(treeNode, data);
                    } else {
                        treeNode = zTree.addNodes(null, data);
                    }
                },
                error : function(e) {
                    window.console.log("增加节点失败！");
                }
            });
            if (treeNode) {
                zTree.editName(treeNode[0]);
            } else {
                alert("叶子节点被锁定，无法增加子节点");
            }
        };

        function OnRightClick(event, treeId, treeNode) {
            if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                zTree.cancelSelectedNode();
                showRMenu("root", event.clientX, event.clientY);
            } else if (treeNode && !treeNode.noR) {
                zTree.selectNode(treeNode);
                showRMenu("node", event.clientX, event.clientY);
            }
        }

        function showRMenu(type, x, y) {
            $("#rMenu ul").show();
            if (type=="root") {
                $("#m_del").hide();
                $("#m_check").hide();
                $("#m_unCheck").hide();
            } else {
                $("#m_del").show();
                $("#m_check").show();
                $("#m_unCheck").show();
            }
            rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

            $("body").bind("mousedown", onBodyMouseDown);
        }
        function hideRMenu() {
            if (rMenu) rMenu.css({"visibility": "hidden"});
            $("body").unbind("mousedown", onBodyMouseDown);
        }
        function onBodyMouseDown(event){
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
                rMenu.css({"visibility" : "hidden"});
            }
        }
        var addCount = 1;
        function addTreeNode() {
            hideRMenu();
            var newNode = { name:"增加" + (addCount++)};
            if (zTree.getSelectedNodes()[0]) {
                newNode.checked = zTree.getSelectedNodes()[0].checked;
                zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
            } else {
                zTree.addNodes(null, newNode);
            }
        }
        function removeTreeNode() {
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            if (nodes && nodes.length>0) {
                if (nodes[0].children && nodes[0].children.length > 0) {
                    var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
                    if (confirm(msg)==true){
                        zTree.removeNode(nodes[0]);
                    }
                } else {
                    zTree.removeNode(nodes[0]);
                }
            }
        }
        function checkTreeNode(checked) {
            var nodes = zTree.getSelectedNodes();
            if (nodes && nodes.length>0) {
                zTree.checkNode(nodes[0], checked, true);
            }
            hideRMenu();
        }
        function resetTree() {
            hideRMenu();
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }

        var zTree, rMenu;

        function dropPrev(treeId, nodes, targetNode) {
            window.console.log("dropPrev！..............");
            var pNode = targetNode.getParentNode();
            if (pNode && pNode.dropInner === false) {
                return false;
            } else {
                for (var i=0,l=curDragNodes.length; i<l; i++) {
                    var curPNode = curDragNodes[i].getParentNode();
                    if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        }
        function dropInner(treeId, nodes, targetNode) {
            window.console.log("dropInner！..............");
            if (targetNode && targetNode.dropInner === false) {
                return false;
            } else {
                for (var i=0,l=curDragNodes.length; i<l; i++) {
                    if (!targetNode && curDragNodes[i].dropRoot === false) {
                        return false;
                    } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        }
        function dropNext(treeId, nodes, targetNode) {
            window.console.log("dropNext！..............");
            var pNode = targetNode.getParentNode();
            if (pNode && pNode.dropInner === false) {
                return false;
            } else {
                for (var i=0,l=curDragNodes.length; i<l; i++) {
                    var curPNode = curDragNodes[i].getParentNode();
                    if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        }

        var log, className = "dark", curDragNodes, autoExpandNode;
        function beforeDrag(treeId, treeNodes) {
            window.console.log("beforeDrag！..............");
            className = (className === "dark" ? "":"dark");
            for (var i=0,l=treeNodes.length; i<l; i++) {
                if (treeNodes[i].drag === false) {
                    curDragNodes = null;
                    return false;
                } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
                    curDragNodes = null;
                    return false;
                }
            }
            curDragNodes = treeNodes;
            return true;
        }
        function beforeDragOpen(treeId, treeNode) {
            window.console.log("beforeDragOpen！..............");
            autoExpandNode = treeNode;
            return true;
        }
        function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
            window.console.log("beforeDrop！..............");
            className = (className === "dark" ? "":"dark");
            return true;
        }
        function onDrag(event, treeId, treeNodes) {
            window.console.log("onDrag！..............");
            className = (className === "dark" ? "":"dark");
        }
        function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
            window.console.log("onDrop！..............");
            className = (className === "dark" ? "":"dark");
            var targetId = "0";
            if(targetNode != null ){
                targetId = targetNode.id;
            }
            $.ajax({
                async : false,
                url : '${base}/'+"tree/" + treeNodes[0].id + "/" + targetId,
                type : "POST",
                dataType : "json",
                data : {_method: 'PUT'},
                success : function(data) {
                    if(data == 'success'){
                        //var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        //treeNode.name = newName;
                        //zTree.updateNode(treeNode,false);
                    }else{
                        alert("移动失败！");
                    }
                },
                error : function(e) {
                    window.console.log("移动成功！");
                }
            });
        }
        function onExpand(event, treeId, treeNode) {
            window.console.log("onExpand！..............");
            if (treeNode === autoExpandNode) {
                className = (className === "dark" ? "":"dark");
            }
        }


        function beforeRename(treeId, treeNode, newName, isCancel){
            //alert(treeNode.name);
            return true;
        }

        function onRename(event,treeId, treeNode,isCancel){
            window.console.log("onRename！..............");
            $.ajax({
                async : false,
                url : '${base}/'+"treename",
                type : "POST",
                dataType : "json",
                data : {'id':treeNode.id,_method: 'PUT','treeName':treeNode.name},
                success : function(data) {
                    if(data == 'success'){
                        //var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        //treeNode.name = newName;
                        //zTree.updateNode(treeNode,false);
                    }else{
                        alert("名称修改失败！");
                    }
                },
                error : function(e) {
                    window.console.log("修改名称时出现错误");
                }
            });
            return true;
        }

        function onRemove(event, treeId, treeNode){
            window.console.log("onRemove！..............");
            //alert(treeNode.name);
            $.ajax({
                async : false,
                url : '${base}/'+"tree",
                type : "POST",
                dataType : "json",
                data : {'id':treeNode.id,'_method': 'DELETE'},
                success : function(data) {
                    if(data == 'success'){
                        //var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        //treeNode.name = newName;
                        //zTree.updateNode(treeNode,false);
                    }else{
                        alert("删除失败失败！");
                    }
                },
                error : function(e) {
                    window.console.log("删除出错错误");
                }
            });
            return true;
        }

        function beforeRemove(treeId, treeNode){
            window.console.log("beforeRemove！..............");
            return window.confirm("确认删除该节点吗？");
        }

        function beforeDrag(treeId, treeNodes) {
            window.console.log("1232432454");
            return true;
        }

        function setEdit() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.setting.edit.showRemoveBtn = true;
            zTree.setting.edit.showRenameBtn = true;
            zTree.setting.edit.removeTitle = "删除";
            zTree.setting.edit.renameTitle = "重命名";
           /* showCode(['setting.edit.showRemoveBtn = ' + remove, 'setting.edit.showRenameBtn = ' + rename,
                'setting.edit.removeTitle = "' + removeTitle +'"', 'setting.edit.renameTitle = "' + renameTitle + '"']);*/
        }

        function filter(treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            for (var i=0, l=childNodes.length; i<l; i++) {
                childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            }
            return childNodes;
        }

        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting);
            setEdit();
            zTree = $.fn.zTree.getZTreeObj("treeDemo");
            $("#addParent").bind("click", {isParent:true}, add);
            $("#addLeaf").bind("click", {isParent:false}, add);
           // rMenu = $("#rMenu");
//            $("#remove").bind("change", setEdit);
//            $("#rename").bind("change", setEdit);
//            $("#removeTitle").bind("propertychange", setEdit)
//                    .bind("input", setEdit);
//            $("#renameTitle").bind("propertychange", setEdit).bind("input", setEdit);
        });
    </script>
    <style type="text/css">
        div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
        div#rMenu ul li{
            margin: 1px 0;
            padding: 0 5px;
            cursor: pointer;
            list-style: none outside none;
            background-color: #DFDFDF;
        }
    </style>
</head>
<body>
<div class="zTreeDemoBackground left">
    <ul id="treeDemo" class="ztree" style="width:400px;;"></ul>
</div>
&nbsp;&nbsp;&nbsp;&nbsp;[<a id="addParent" href="#" title="增加父节点" onclick="return false;">增加父节点</a> ]
&nbsp;&nbsp;&nbsp;&nbsp;[ <a id="addLeaf" href="#" title="增加叶子节点" onclick="return false;">增加叶子节点</a>]
<div id="rMenu">
    <ul>
        <li id="m_add" onclick="addTreeNode();">增加节点</li>
        <li id="m_del" onclick="removeTreeNode();">删除节点</li>
        <#--<li id="m_check" onclick="checkTreeNode(true);">Check节点</li>
        <li id="m_unCheck" onclick="checkTreeNode(false);">unCheck节点</li>-->
        <#--<li id="m_reset" onclick="resetTree();">恢复zTree</li>-->
    </ul>
</div>
</body>
</html>