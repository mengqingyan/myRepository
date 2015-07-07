function Ajax1() {
	
	var divs = $("#root").children();
	var users = [];
	
	for (var i = 0; i < divs.length; i++) {
		var inputs = $(divs[i]).children("input");
		var username = $(inputs[0]).val();
		var password = $(inputs[1]).val();
		var user = {"username": username,
					"password":password};
		users.push(user);
	}
//	console.log("users:" + JSON.stringify(users, null, null));
//	console.log("users:" + JSON.stringify({"users":users}, null, null));
//	var str='{"users":[{"username":"mqy1","password":"pass1"},{"username":"mqy2","password":"pass2"}]}';
//	console.log("json Obj:"+JSON.parse(str, null));
	var ustr = JSON.stringify(users, null, null);
		$.ajax({
			  type: 'POST',
			  contentType:'application/json;charset=UTF-8',
//			  url: "testcollection/addUser.do",
			  url: ctxPath + "/testcollection/addUser.do",
			  data: JSON.stringify({"users":users}, null, null),
//			  data: {"users":users},
			  success : function(data) {
//			  	alert("modelname: " + data.userm.modelname);
//			  	response.getWriter().write(data);
//				location.href = "index.jsp"
			  }
			});
}

function AjaxForm() {
	

	$('#form').on('submit', function() {
		var divs = $("#root").children();
		var users = [];
		
		for (var i = 0; i < divs.length; i++) {
			var inputs = $(divs[i]).children("input");
			var username = $(inputs[0]).val();
			var password = $(inputs[1]).val();
			var user = {"username": username,
						"password":password};
			users.push(user);
		}
		
        $(this).ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: "testcollection/addUser.do", // 需要提交的 url
            data: JSON.stringify({"users":users}, null, null),
            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
                alert('提交成功！');
            }
//            $(this).resetForm(); // 提交后重置表单
        });
        return false; // 阻止表单自动提交事件
	});

}

function Ajax() {
	
	var divs = $("#root").children();
	var datas = {};
	
	for (var i = 0; i < divs.length; i++) {
		var inputs = $(divs[i]).children("input");
		var username = $(inputs[0]).val();
		var password = $(inputs[1]).val();
		var uName = "users[" + i + "].username";
		var uPass = "users[" + i + "].password";
		datas[uName] = username;
		datas[uPass] = password;
	}
//	console.log("users:" + datas['users[0].username']);
	console.log("users:" + JSON.stringify(datas, null, null));
	$.ajax({
		type: 'POST',
		url: "userAddWithAjax.do",
		data: datas,
		success : function(data) {
			var users = data.users;
			var dLen = users.length;
			var alertStr = "";
			for(var i = 0; i < dLen; i++){
				alertStr += users[i].username + ":" + users[i].password + "\n";
			}
			alert("return data: \n" + alertStr);
			
		}
	});
}