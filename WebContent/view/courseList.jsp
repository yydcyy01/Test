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
	        url:"CourseServlet?method=getCourseList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName: 'id',
	        sortOrder: 'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'课程ID',width:50, sortable: true},    
 		        {field:'name',title:'课程名',width:100},
 		        {field:'credithour',title:'学分',width:100},
 		        {field:'classhour',title:'讲授学时',width:100},
 		        {field:'practicehour',title:'实验学时',width:100},
 		        {field:'remark',title:'备注',width:200,
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
	    
	    
	  	
	  	//设置添加课程窗口
	    $("#addDialog").dialog({
	    	title: "添加课程",
	    	width: 500,
	    	height: 400,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							//var gradeid = $("#add_gradeList").combobox("getValue");
							$.ajax({
								type: "post",
								url: "CourseServlet?method=AddCourse",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										$("#add_credithour").textbox('setValue', "");
										$("#add_classhour").textbox('setValue', "");
										$("#add_practicehour").textbox('setValue', "");
										$("#remark").textbox('setValue', "");
										
										
										//重新刷新页面数据
										$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","添加失败!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						
						$("#add_name").textbox('setValue', "");
						$("#add_credithour").textbox('setValue', "");
						$("#add_classhour").textbox('setValue', "");
						$("#add_practicehour").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
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
            	$.messager.alert("消息提醒", "请选择数据进行修改!", "warning");
            	return;
            }
//             alert("进来了")
        	$("#editDialog").dialog("open");
	  	});
	  
	  //设置编辑课程窗口
	    $("#editDialog").dialog({
	    	title: "编辑课程",
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
					text:'确定修改',
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
								url: "CourseServlet?method=EditCourse",
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","修改成功!","info");
										//关闭窗口
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
// 										$("#add_name").textbox('setValue', "");
// 										$("#add_credithour").textbox('setValue', "");
// 										$("#add_classhour").textbox('setValue', "");
// 										$("#add_practicehour").textbox('setValue', "");
// 										$("#remark").textbox('setValue', "");
										//重新刷新页面数据
							  			//$('#gradeList').combobox("setValue", gradeid);
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","修改失败!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						$("#edit_name").textbox('setValue', "");
						$("#edit_credithour").textbox('setValue', "");
						$("#edit_classhour").textbox('setValue', "");
						$("#edit_practicehour").textbox('setValue', "");
					}
				},
			],
			onBeforeOpen: function(){
// 				alert('ID:'+$('#dataList').datagrid('getSelected').id)
				var selectRow = $("#dataList").datagrid("getSelected");
// 				设置值
// 				$("#edit_name").textbox('setValue', $('#dataList').datagrid('getSelected').name);
// 				$("#edit_credithour").textbox('setValue', $('#dataList').datagrid('getSelected').credithour);
// 				$("#edit_classhour").textbox('setValue', $('#dataList').datagrid('getSelected').classhour);
// 				$("#edit_practicehour").textbox('setValue', $('#dataList').datagrid('getSelected').practicehour);
// 				$("#edit_remark").val("123"); 

// 				$("#edit_id").textbox('setValue', selectRow.id);//个斑马,坑劳资半天
				
				$("#edit-id").val(selectRow.id);

// 				alert($("#edit_id").val());
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_credithour").textbox('setValue', selectRow.credithour);
				$("#edit_classhour").textbox('setValue', selectRow.classhour);
				$("#edit_practicehour").textbox('setValue', selectRow.practicehour);
				$("#edit_remark").val(selectRow.remark);
				
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
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
		<div style="float: left; margin-right: 10px;"><a id="edit-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		<div style="margin-top: 3px;">课程名称：<input id="courseName" class="easyui-textbox" name="courseName" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>课程名称:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name"  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>学分:</td>
	    			<td><input id="add_credithour" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="credithour"  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>讲授学时:</td>
	    			<td><input id="add_classhour" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="classhour"  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>实验学时:</td>
	    			<td><input id="add_practicehour" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="practicehour"  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>备注</td>
	    			<td>
	    				<textarea id="remark" name="remark" style="width: 200px; height: 60px;" class="" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 编辑窗口 -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    	<input type="hidden" id="edit-id" name="id">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>课程名称:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name"  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>学分:</td>
	    			<td><input id="edit_credithour" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="credithour"  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>讲授学时:</td>
	    			<td><input id="edit_classhour" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="classhour"  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>实验学时:</td>
	    			<td><input id="edit_practicehour" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="practicehour"  data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>备注</td>
	    			<td>
	    				<textarea id="edit_remark" name="remark" style="width: 200px; height: 60px;" class="" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
</body>
</html>