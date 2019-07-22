<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>课程列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'课程列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"ScoreServlet?method=getScoreList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName: 'id',
	        sortOrder: 'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'序号',width:50, sortable: true},    
 		        {field:'studentId',title:'学生学号',width:100},
 		        {field:'courseId',title:'课程编号',width:100},
 		        {field:'semester',title:'开课学期',width:100},
 		        {field:'score1',title:'一考成绩',width:100},
 		        {field:'score2',title:'二考成绩',width:100},
 		        {field:'score3',title:'三考成绩',width:200,
 	 		        }

 		        ]], 
	        toolbar: "#toolbar"
	    }); 
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    });
	   
	    //删除
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
	    	//console.log(selectRow);
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var courseid = selectRow.id;
            	$.messager.confirm("消息提醒", "将删除课程信息，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "CourseServlet?method=DeleteCourse",
							data: {courseid : courseid},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
								} else{
									$.messager.alert("消息提醒","删除失败!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	    	

	  	//搜索按钮监听事件
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			courseName: $('#courseName').val()
	  		});
	  	});
	  	
	  //编辑按钮监听事件
	  	$("#edit-btn").click(function(){
	  		var selectRow = $("#dataList").datagrid("getSelected");
	    	console.log(selectRow);
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择需要添加的课程!", "warning");
            	return;
            }
//             alert("进来了")
        	$("#editDialog").dialog("open");
	  	});
	  
	  //设置编辑课程窗口
	    $("#editDialog").dialog({
	    	title: "選課确定",
	    	width: 500,
	    	height: 400,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'确定无误',
					plain: true,
					iconCls:'icon-edit',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "ScoreServlet?method=AddScore",
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加课程成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");

										//重新刷新页面数据

							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","失败!!1,请检查是否重复添加2,只允许学生增/删课程","warning");
										return;
									}
								}
							});
						}
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
// 				设置值
				$("#edit-id").val(selectRow.id);
			}
	    });
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	</table> 
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left; margin-right: 10px;"><a id="edit-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		<div style="margin-top: 3px;">
		学生学号：<input id="studentId" class="easyui-textbox" name="studentId" />
		课程编号：<input id="courseId" class="easyui-textbox" name="courseId" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>
	</div>
	
	<!-- 選課窗口 -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    	<input type="hidden" id="edit-id" name="id">
<!--     	<input id="edit-id" name="id"> -->
	    	<table cellpadding="8" >
<!-- 	    		<tr> -->
<!-- 	    			<td>学生学号 -->
<!-- 	    			<td><input id="add_studentId" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="studentId"  data-options="required:true, missingMessage:'不能为空'" /></td> -->
<!-- 	    		</tr> -->
<!-- 	    		<tr> -->
<!-- 	    			<td>课程编号 -->
<!-- 	    			<td><input id="add_courseId" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="courseId"  data-options="required:true, missingMessage:'不能为空'" /></td> -->
<!-- 	    		</tr> -->
	    	</table>
	    </form>
	</div>
	
</body>
</html>