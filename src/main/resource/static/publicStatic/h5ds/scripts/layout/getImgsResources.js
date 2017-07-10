//获取图片素材
define(function(require,exports,module){

	var allObj = require('./objConfig');
	//上传图片初始化
	var $imgResourcesBox = $(".imgResourcesBox");
	
	//初始化的用户的APP素材
	var iniUserAppPics = function(){
		$.ajax({
			url:"/app/findSources",
			type:"post",
			//data:{id:$("#coolAppAllData").attr("data-userappid")},
			data:{appId:allObj.obj.userAppId,sourceTypeId:1},
			success:function(data){
				data = data.sources;
				console.log(data);
				var html="";
				if(data.returnList != null){
					$.each(data.returnList,function(i){
						html+="<li><a class='del' data-id='"+data.returnList[i].id+"'><i class='fa fa-close'></i></a><img _src='"+data.returnList[i].sourcePath+"' src='"+data.returnList[i].cutPath+"' realsize='"+data.returnList[i].width+","+data.returnList[i].height+"' data-id='"+data.returnList[i].id+"'/></li>";
					});
				}
				$imgResourcesBox.eq(0).html(html);
			}
		});
	};
	
	//删除素材
	var iniUserAppDelPic = function(){
		$imgResourcesBox.on("click.delSucai",".del",function(e){
			e.stopPropagation();
			var $this = $(this);
			var id = $this.data("id");
			console.log(id);
			$.ajax({
				url:"/app/deleteSource",
				dataType:"json",
				type:"post",
				data:{"sourceId":id},
				success:function(data){
					console.log(data);
					if(data.status == 1){
						$this.parent().remove();	
					}else{
						alert("图片路径不存在，删除失败！");	
					}
				}
			});	
		});
	};
	
	//获取素材分类
	var getResourcesType = function(){
		var $resourcesUlNav = $("#resourcesUlNav");
		$.ajax({
			url:"/app/findSourcesType",
			type:"post",
			async:false,
			dataType:"json",
			success:function(data){
				var html='<li data-id="my" class="active">我的上传</li>';
				data = data.sourceType;
				if(data!=null){
					$.each(data,function(i){
						html+="<li data-id='"+data[i].id+"'>"+data[i].sourceTypeName+"</li>";
					});
				}
				$resourcesUlNav.html(html);
			}
		});	
		
		//事件绑定
		var $resourcesPublic = $("#resourcesPublicBox");
		var $resourcesMe = $("#resourcesMe");
		$resourcesUlNav.on("click","li",function(){
			var $this = $(this);
			$resourcesUlNav.find("li").removeClass("active");
			$this.addClass("active");
			var id = $this.data("id");
			//切换到我自己的素材
			if(id == "my"){
				$resourcesPublic.css("display","none");
				$resourcesMe.css("display","block");
			}else { //切换到其他素材 - 切换后，缓存图片
				
				//如果已经加载，就不去加载了
				if($this.attr("data-mark") == "1"){
					$resourcesMe.css("display","none");
					var $li = $("#resourcesPublicBox").css("display","block").find("li");
					$li.css("display","none");
					$li.each(function(index, element) {
                       if($(this).attr("data-id") == id){
							$(this).css("display","block");   
					   } 
                    });
					return false;	
				};
				
				$.ajax({
					url:"/app/findSources",
					data:{appId:allObj.obj.userAppId,sourceTypeId:id,pageSize:100},
					dataType:"json",
					type:"post",
					success:function(msg){
						console.log(msg);
						var html="";
						if(msg.sources.returnList.length != 0){
							$.each(msg.sources.returnList,function(i){
								html+="<li data-id='"+id+"'><img _src='"+msg.sources.returnList[i].sourcePath+"' src='"+msg.sources.returnList[i].cutPath+"' realsize='"+msg.sources.returnList[i].width+","+msg.sources.returnList[i].height+"' data-id='"+msg.sources.returnList[i].id+"'/></li>";
							});
							$resourcesMe.css("display","none");
							$resourcesPublic.css("display","block");
							$resourcesPublic.find("li").css("display","none");
							
							$resourcesPublic.append(html);
							
							$this.attr("data-mark","1"); //1表示已经加载过的
							
//							$.ajax({
//								  url:"/Material/getMaterial",
//									type:"post",
//									data:{"p":num,"tid":id},
//									dataType:"json",
//									success:function(data){
//										var html="";
//										$.each(data.data,function(i){
//											html+="编号:"+data.data[i].id+"<img  width='100px' height='100px' src='"+data.data[i].url+"'/>";
//										});
//										$("#content").append(html);
//									}
//							  });//end ajax
//							$.jqPaginator('#page', {
//								totalPages: data.totalPages,
//								visiblePages: 5,
//								currentPage: 1,
//								wrapper:'<ul class="pagination"></ul>',
//								prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
//								next: '<li class="next"><a href="javascript:;">下一页</a></li>',
//								page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
//								onPageChange: function (num, type) {
//									if(type!="init"){
//										  $("#content").html("");
//										  $.ajax({
//											  url:"/Material/getMaterial",
//												type:"post",
//												data:{"p":num,"tid":id},
//												dataType:"json",
//												success:function(data){
//													var html="";
//													$.each(data.data,function(i){
//														html+="编号:"+data.data[i].id+"<img  width='100px' height='100px' src='"+data.data[i].url+"'/>";
//													});
//													$("#content").append(html);
//												}
//										  });//end ajax
//									} //end if
//								} //end onPageChange
//							});//end jqPaginator

						}//end if
						else{
							$this.attr("data-mark","1"); //1表示已经加载过的	
						}
					}//end success
				});	//end ajax
			}//end else
		});//end click
		
	};
	
	//暴露接口
	exports.getImgsResources = function(){
		iniUserAppPics(); //初始化用户的APP素材
		iniUserAppDelPic(); //删除素材方法
        getResourcesType(); //获取系统的素材
    }
	
});