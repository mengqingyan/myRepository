seajs.use([ 'bootstrap', '$', 'widget', 'underscore', 'validator' ],function(bootstrap, $, Widget, _, Validator) {
					'use strict';
					$('.help-popover').popover();

					var setting = {
						view : {
							showIcon : false,
							showLine : false
						},
						data : {
							simpleData : {
								enable : true,
								idKey : "id",
								pIdKey : "pId",
								rootPId : ""
							}
						},
						callback : {
							onNodeCreated : insertIcon,
							onClick : zTreeOnClick
						}
					};

					$(document).ready(function() {
						// 初始化节点数据
						var zNodes = initNodes();
						var obj1=$.fn.zTree.init($("#tree"), setting, zNodes);
						var obj2=$.fn.zTree.init($("#tree1"), setting, zNodes);
						
						$('#checkAll').on('click', function() {
							if ($(this).prop('checked')) {
								$('input[name=cId]').prop("checked", true);
							} else {
								$('input[name=cId]').prop("checked", false);
							}
						});
					});
		
					// 点击查询当前网站下的广告位
					function zTreeOnClick(event, treeId, treeNode) {
						var firstFlag = treeNode.id.charAt(0);
						var eqChannelId = '';
						var eqPageId = "";
						var eqSiteId = "";
						if (firstFlag == 0) {
							eqSiteId = treeNode.id.substring(2,
									treeNode.id.length);
						} else if (firstFlag == 1) {
							eqChannelId = treeNode.id.substring(2,
									treeNode.id.length);
							var treeNode1 = treeNode.getParentNode();
							eqSiteId = treeNode1.id.substring(2,
									treeNode1.id.length);
						} else if (firstFlag == 2) {
							eqPageId = treeNode.id.substring(2,
									treeNode.id.length);
							var treeNode1 = treeNode.getParentNode();
							eqChannelId = treeNode1.id.substring(2,
									treeNode1.id.length);
							var treeNode2 = treeNode1.getParentNode();
							eqSiteId = treeNode2.id.substring(2,
									treeNode2.id.length);
						}
						var oSettings = "searchManager.do?eqSiteId=" + eqSiteId
								+ "&eqPageId=" + eqPageId + "&eqChannelId="
								+ eqChannelId;
    					tabl.fnReloadAjax( oSettings);
					};
	
					var searchsiteid= '';
					
					function onchangeTest(){
						searchsiteid=$("#zsiteId").val();
						initDrop("zchannelId","http://admp.e.189.cn/media/adChannel/searchDropData.do?eqSiteId="+searchsiteid);
					}
					
					
					function editModal(arg) {
						var template = _.template($('#j-modal-tmpl').html());
						$('body').append(template({
							domId : arg.domId,
							title : arg.title,
							content : arg.content,
							cancelText : arg.cancelText,
							confirmId : arg.confirmId,
							confirmText : arg.confirmText
						}));
						Widget.autoRenderAll();
						var $modal = $('#' + arg.domId);
						$modal.find('.modal-lg').removeClass('modal-lg');
						$modal.modal('show');
						$modal.on('hidden.bs.modal', function(e) {
							$modal.remove();
						});
						
						initDrop("zsiteId","http://admp.e.189.cn/media/adSite/searchDropData.do",onchangeTest);
						initDrop("zchannelId","http://admp.e.189.cn/media/adChannel/searchDropData.do?eqSiteId="+$("#zsiteId").val());

						Validator.query('#' + arg.domId + ' form').on(
								'formValidated',
								function(error, message, elem) {

									if (!error) {
										// 通过验证
										var $formValue = elem.serializeArray();

										$modal.modal('hide');

										// 回调函数
										arg.callback($formValue);

										$.ajax({
											url : '',
											type : 'POST',
											data : elem.serializeArray(),
											dataType : 'json',
											success : function(data) {

											},
											error : function(err) {

											}
										});
									}
								});
					}
					
					function initNodes() {
						var node = new Array();
						$.ajax({
									url : 'http://admp.e.189.cn/media/adSite/getAdSiteAndChannelPage.do',
									type : 'POST',
									dataType : 'json',
									async : false,
									success : function(data) {
										var i = 0;
										for (i; i < data.adSiteList.length; i++) {
											node[i] = new nodeunit("0-"+ data.adSiteList[i].siteId,0, data.adSiteList[i].name,false);
										}
										var j = 0;
										for (j; j < data.adChannelList.length; j++) {
											node[i] = new nodeunit("1-"+ data.adChannelList[j].channelId,"0-"+ data.adChannelList[j].siteId,data.adChannelList[j].name,false);
											i++;
										}
										j = 0;
										for (j; j < data.adPageList.length; j++) {
											node[i] = new nodeunit("2-"+ data.adPageList[j].pageId,"1-"+ data.adPageList[j].channelId,data.adPageList[j].name,false);
											i++;
										}
									},
									error : function(err) {
										alert('error系统错误!');
									}
								});
						return node;
					}
					
					// 新建网站
					$('#j-add-site').click(function(){
						dropNewsite("site",0,"网站");
					});
					$('#j-add-channel').click(function(){
						dropNewsite("channel",1,"频道");
					});
					$('#j-add-page').click(function(){
						dropNewsite("page",2,"页面");
					});
					
					function dropNewsite(type,idflag,title) {
						var value='{';
						var treeObj = $.fn.zTree.getZTreeObj("tree");
						//获取当前被选中的树形节点
						var nodes = treeObj.getSelectedNodes();
						
						var node=nodes[nodes.length-1];
						
						while(node!=null){
//							console.log(node.name);
							var firstFlag = node.id.charAt(0);
							if(firstFlag<idflag){
								if (firstFlag == 0) {
									value=value+'\"zsiteId\":'+'\"'+node.id.substring(2, node.id.length)+'\",';

								} else if (firstFlag == 1) {
									value=value+'\"zchannelId\":'+'\"'+node.id.substring(2, node.id.length)+'\",';

								} 
							}
							node=node.getParentNode();
						}
						if(value.length>1){
							value=value.substring(0, value.length-1);
						}
						value=value+"}";
						//value为当前选中的节点的数据（以及父节点的数据）
						var obj = JSON.parse(value);
						var tpl = _.template($('#j-add-'+type+'-form').html());
						editModal({
							domId : 'j-add-modal',
							title : '新建'+title,
							content : tpl(obj),
							cancelText : '取消',
							confirmId : 'j-confirm',
							confirmText : '提交',
							callback : function(data) {
								var createURL = "http://admp.e.189.cn/media/ad"+ type.substring(0,1).toUpperCase()+ type.substring(1) + "/save.do";		
								console.log(createURL);
								var treeObj = $.fn.zTree.getZTreeObj("tree");			
								var name = '';
								var newdata = "{";
								for ( var i = 0; i < data.length; i++) {
									name = data[i].name;
									if (name == 'zsiteName') {
										newdata = newdata+ "\"name\":\""+ data[i].value+ "\",";					
										continue;
									}
									if (name == 'zurl') {
										newdata = newdata+ "\"url\":\""+ data[i].value+ "\",";
										continue;
									}
									if (name == 'zdescp') {
										newdata = newdata+ "\"descp\":\""+ data[i].value+ "\",";
										continue;
									}
									if (name == 'zsiteId'&&idflag>0) {
											newdata = newdata+ "\"siteId\":\""+ data[i].value+ "\",";
											continue;
										
									}
									if (name == 'zchannelId'&&idflag>1) {
										newdata = newdata+ "\"channelId\":\""+ data[i].value+ "\",";
										continue;
									}
								}
								
								newdata = newdata.substring(0, newdata.length-1) + "}";
								
								var insertobj = JSON.parse(newdata);
//								console.log(insertobj.siteId);
								// 提交数据库
								$.ajax({
									url : createURL,
									type : 'POST',
									dataType : 'json',
									data : insertobj,
									success : function(data) {
										var parentid='';
										var newid='';
										if(type=="page"){
											parentid='1-'+insertobj.channelId;
											newid='2-'+data.id;
										}
										else if(type=="channel"){
											parentid='0-'+insertobj.siteId;
											newid='1-'+data.id;
										}
										else if(type=="site"){
											parentid='0';
											newid='0-'+data.id;
										}
									  
										var node = treeObj.getNodeByParam("id", parentid, null);
										var newNode = new nodeunit(newid,parentid, insertobj.name,false);
										treeObj.addNodes(node, newNode);
									},
									error : function(err) {
										alert('添加失败');
									}
								});
								
							}
						});
					}
					
					
					
					
					// 树节点单元
					function nodeunit(id, pId, name, open) {
						this.id = id;
						this.pId = pId;
						this.name = name;
						this.open = open;
						return this;
					}

					$(document).on('click', '#j-confirm', function() {
						$('.modal .form-horizontal').trigger('submit');
					});

					function insertIcon(event, treeId, treeNode) {
						if(treeId=='tree'){
							var editStr = "<div id='icon_"+ treeNode.tId
							+ "' class='linkfour hidden'><a class='j-action-icon delete' href='javascript:;'></a> "
							+ "<a class='j-action-icon edit' href='javascript:;'></a> "
							+ "<a class='yu' href='javascript:;'></a> "
							+ "<a class='tou' href='javascript:;'></a></div>";

					var $dom = $("#" + treeNode.tId + " a");
					$dom.append(editStr).hover(function() {
						$("#icon_" + treeNode.tId).removeClass('hidden');
					}, function() {
						$("#icon_" + treeNode.tId).addClass('hidden');
					});
					// 删除、编辑
					$("#icon_" + treeNode.tId).on('click','.j-action-icon',function(e) {
										var $label = $(e.target);
										var firstFlag = treeNode.id.charAt(0);
										var id = treeNode.id.substring(2,treeNode.id.length);
										if ($label.hasClass('delete')) { // 删除
											if (confirm('是否删除？')) {
												//如果该节点含有叶子节点，不能删除
												var childrenNodes = treeNode.children;
												if(childrenNodes==null){
													//如果该网站、频道、页面下含有广告位不能删除
													var idflag = '';
													if (firstFlag == 0) {
														firstFlag = "adSite";
														idflag = "eqSiteId";

													} else if (firstFlag == 1) {
														firstFlag = "adChannel";
														idflag = "eqChannelId";

													} else if (firstFlag == 2) {
														firstFlag = "adPage";
														idflag = "eqPageId";
													}
													//查询是否有广告位的存在
													var searchAdPositionURL = "/media/adposition/search.do?"+ idflag + "=" + id;
													
													$.ajax({	
														url : searchAdPositionURL,
														type : 'POST',
														dataType : 'json',
														success : function(data) {
															if(data.aaData.length==0){
															
																var deteleURL = 'http://admp.e.189.cn/media/'+ firstFlag+ "/updateStatus.do?"+ idflag + "=" + id+"&status=3";
																// 提交数据库
																$.ajax({	url : deteleURL,
																			type : 'POST',
																			dataType : 'json',
																			success : function(data) {
																				$.fn.zTree.getZTreeObj("tree").removeNode(treeNode);
																			},
																			error : function(err) {
																				alert('删除失败');
																			}
																		});
															}
															else{
																alert("有广告位，不能删除");
															}
															
														},
														error : function(err) {
															alert('系统错误');
														}
													});
													
													
												}
												else{
													alert("有频道或页面，不能删除！");
												}
											}
										} else { // 编辑
											var siteName = '';
											var channelName = '';
											var pageName = '';
											var data = '';
											if (firstFlag == 0) {
												firstFlag = "adSite";
												siteName = treeNode.name;
											} else if (firstFlag == 1) {
												firstFlag = "adChannel";
												channelName = treeNode.name;
												siteName = treeNode.getParentNode().name;

											} else if (firstFlag == 2) {
												firstFlag = "adPage";
												pageName = treeNode.name;
												var treeNode1 = treeNode.getParentNode();
												channelName = treeNode1.name;
												siteName = treeNode1.getParentNode().name;
											}
											var url = 'http://admp.e.189.cn/media/'+ firstFlag+ "/getById.do?id="+ id;
											data = getDetail(url);
											var tpl = _.template($('#j-edit-form').html());
											var $title = $('#'+ treeNode.tId+ '_span');
											editModal({
												domId : 'j-edit-modal',
												title : '编辑网站',
												content : tpl({
													zsiteName : siteName,
													zchannelName : channelName,
													zpageName : pageName,
													zurl : data.url,
													zdescp : data.descp,
													flag : firstFlag
												}),
												cancelText : '取消',
												confirmId : 'j-confirm',
												confirmText : '修改',
												callback : function(data) {
												
													var updataURL = "http://admp.e.189.cn/media/"+ firstFlag+ "/updateById.do";
													var sid = firstFlag.substring(2,firstFlag.length).toLowerCase()+ "Id";
													var treeObj = $.fn.zTree.getZTreeObj("tree");
												
													var name = '';
													var newdata = "{\""+ sid + "\":\""+ id + "\"";
													for ( var i = 0; i < data.length; i++) {
														name = data[i].name;
														if (name.substring(1,name.length - 4) == firstFlag
																.substring(2,firstFlag.length).toLowerCase()) {
															newdata = newdata+ ",\"name\":\""+ data[i].value+ "\"";
															
															// 修改节点
															treeNode.name = data[i].value;
															treeObj.updateNode(treeNode);
//															$title.html(data[i].value);
															continue;
														}
														if (name == 'zurl') {
															newdata = newdata+ ",\"url\":\""+ data[i].value+ "\"";
															continue;
														}
														if (name == 'zdescp') {
															newdata = newdata+ ",\"descp\":\""+ data[i].value+ "\"";
															continue;
														}
													}
													newdata = newdata + "}";
													var obj = JSON.parse(newdata);
													// 提交数据库
													$.ajax({
														url : updataURL,
														type : 'POST',
														dataType : 'json',
														data : obj,
														success : function(data) {
														},
														error : function(err) {
															alert('编辑失败');
														}
													});
												}
											});
										}
									});
						}
						
					}
					// 获得相关网站、页面、频道的详细信息
					function getDetail(URL) {
						var editString = '';
						$.ajax({
							url : URL,
							type : 'POST',
							dataType : 'json',
							async : false,
							success : function(data) {
								editString = data;
							},
							error : function(err) {
								alert('error系统错误!');
							}
						});
						return editString;

					}

				});