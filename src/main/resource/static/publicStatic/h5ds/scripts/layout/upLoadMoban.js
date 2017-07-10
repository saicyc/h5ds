//获取图片素材
define(function(require,exports,module){
	var upLoadMoban = function(){
		
		//绑定切换事件
		//加载模板库
		var $addNewOthermoban = $("#addNewOthermoban");
		//默认点击第一个
		
		$("#allMobanType").on("click","li",function(){
			var $this = $(this);
			$("#allMobanType li").removeClass("active");
			$this.addClass("active");
			var id = $(this).attr("data-id");
			
			//右边区域
			var $mobanUl = $(".mobanUl");
			$mobanUl.css("display","none");
			$mobanUl.eq($this.index()).css("display","block");
			
			if($(this).attr("data-lock") == undefined){
				$mobanUl.css("display","none");
				$(this).attr("data-lock","lock");
				//刷新数据
				$.ajax({
					url:"/app/findSimpleTemplateByType",
					data:{"typeId":id},
					dataType:"json",
					type:"post",
					success:function(data){
						console.log(data);
						if(data.length == 0){ 
							var xhtml = '<li data-html="blank"><i class="fa fa-plus-square"></i><span>空白页面</span></li>';
						}else {
							var xhtml = '<li data-html="blank"><i class="fa fa-plus-square"></i><span>空白页面</span></li>';
							for(i in data){
								xhtml+= "<li data-html='"+data[i].html+"'><img src='"+data[i].fileUrl+"' /></li>";	
							}
						}
						$addNewOthermoban.append('<ul class="mobanUl">'+xhtml+'</ul>');
					}
				}); //end ajax
			}
		});
		
		//第一次进来的时候获取模板分类
		$.ajax({
			url:"/app/findSimpleType",
			type:"post",
			async:false,
			dataType:"json",
			success:function(data){
				console.log(data);
				var html="";
				if(data!=null)
					$.each(data,function(i){
						if(i == 0){
							html+="<li class='active' data-id='"+data[i].id+"'>"+data[i].typeName+"</li>";
						}else{
							html+="<li data-id='"+data[i].id+"'>"+data[i].typeName+"</li>";
						}
					});
				$("#allMobanType").html(html);
				$("#allMobanType .active").trigger("click");
			}
		});

	}
	
	//暴露接口
	exports.upLoadMoban = function(){
		upLoadMoban();
    }
	
});