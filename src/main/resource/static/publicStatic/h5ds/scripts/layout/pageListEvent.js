//页面整体参数，暴露出去
define(function(require,exports,module){
	var allObj = require('./objConfig');
	var ifun = require('./ifunction');
	
	//页面事件绑定
	function pageListEvent(){
		
		//删除页面
		var delPage = function(){
			$("#pageListUl").on("click","li a.del",function(){
				$(this).parent().remove();
			});
		};
		
		//选择页面
		var selectPage = function(){
			
			//点击页面列表 - 点击后，自动选中背景图层
			allObj.obj.$pageListUl.on("click.selectPageListUl","li",function(){

				//操作记录滞空
				allObj.obj.$historyRecords.html("");

				$("#pageListUl li").removeClass("active");
				$(this).addClass("active");
				allObj.obj.$page = $(this); //选中的页面对象
				//allObj.obj.$layer = null; //切换页面后，清空$layer 对象
				allObj.obj.sameIndex = -1;
				
				//隐藏操作区 - 选项卡
				//初始选项卡
				allObj.obj.$moreSet.css("display","none");
				allObj.obj.$animateSet.css("display","none");
				allObj.obj.$functionSet.css("display","none");
				
				//初始化 可视区域
				var data = $(this).find("input").attr("data-html");	
				allObj.obj.$cPhoneEdit.html(data);
				
				//初始化 图层列表
				//console.log(allObj.obj.$cPhoneEdit.html());
				var iniPicListUlli = function(){
					//初始化个数
					var $layers = allObj.obj.$cPhoneEdit.find(".layer");
					var arr = [];
					$layers.each(function(){
						var $this = $(this);
						var layer = {
							name : $this.attr("name"),
							type : $this.attr("class"),
							onClick : $this.attr("onClick"),
							display : $this.css("display"),
							zIndex : $this.css("z-index")
						};
						
						//console.log(layer);
						
						//判断类型
						if(layer.type.indexOf("layer-pic") != -1){
							layer.type = '<i class="fa fa-file-picture-o"></i>';
						}
						else if(layer.type.indexOf("layer-text") != -1){
							layer.type = '<i class="fa fa-font"></i>';
						}
						else if(layer.type.indexOf("layer-effect") != -1){
							layer.type = '<i class="fa fa-magic"></i>';	
						}
						else if(layer.type.indexOf("layer-slider") != -1){
							layer.type = '<i class="fa fa-stack-overflow"></i>';	
						}
						else if(layer.type.indexOf("layer-iframe") != -1){
							layer.type = '<i class="iconfont icon-xinjian"></i>';	
						}
						else if(layer.type.indexOf("layer-form") != -1){
							layer.type = '<i class="fa fa-stack-overflow"></i>';	
						}
						else alert("什么鬼类型？没找到！");
						
						//是否显示
						if(layer.display == "none"){
							layer.display = "none";
						}else{
							layer.display = "inline-block";	
						}
						
						//初始参数
						var _picListUlli = allObj.obj._picListUlli;
						_picListUlli = _picListUlli.replace("{{name}}",layer.name);
						_picListUlli = _picListUlli.replace("{{typeico}}",layer.type);
						_picListUlli = _picListUlli.replace("{{display}}",layer.display);
						arr[(allObj.obj.maxZIndex - layer.zIndex - 1)] = _picListUlli;
						
					});
					
					//渲染页面
					allObj.obj.$picListUl.html(arr);
					allObj.obj.$selectBox.css('display','none'); //点击的不是div或其子元素
					
				};iniPicListUlli();
				
				//初始化 该页参数
				allObj.obj.$layer = $("#cPhoneEdit .warp");
				$("#layerBg").trigger("click");
				
			});
			
		};
		
		//复制页面
		var clonePage = function(){
			$("#clonePageButton").on("click",function(){
				var $pageListUl = $("#pageListUl");
				if($pageListUl.find(".active")[0] != undefined){
					$pageListUl.append($pageListUl.find(".active").clone().removeClass("active"));
					$("#pageListUl").find("li").last().trigger("click");
				}else{
					alert("请先选中页面，再复制！");	
				}
			});
		};

		//添加页面 - 该方法只执行一次
		var addPage = function(){
			
			//打开模板库
			var $addMbPageButton = $("#addMbPageButton");
			$addMbPageButton.showWindow({id:"pageTemplate",center:false});
			
			//加载模板分类,模板
			require('./upLoadMoban').upLoadMoban();
			
			//点击li事件
			$(".addNewPageBoxUl").on("click","li",function(){
				var $this = $(this);
				var pHtml = $this.attr("data-html");
				//添加空白页
				if(pHtml == "blank"){
					$("#pageListUl").append('<li><div class="dragdiv"><input type="text" value="未命名" class="name" disabled data-html="<div class=\'wrap\' style=\'background-color:#fff;\'></div>"></div><a href="javascript:void(0)" class="del"><i class="fa fa-times"></i></a></li>');
				}
				else{
					$("#pageListUl").append("<li><div class='dragdiv'><input type='text' value='未命名' class='name' disabled data-html='"+pHtml+"'></div><a href='javascript:void(0)' class='del'><i class='fa fa-times'></i></a></li>");	
				}
				$("#pageListUl").find("li").last().trigger("click");
				$("#pageTemplate a.close").trigger("click"); //关闭弹窗
			});	
			
			$("#fixedBox").on("click",function(){
				$("#pageTemplate a.close").trigger("click"); //关闭弹窗	
			});
		};
		
		//修改页面名称
		var changePageName = function(){
			//修改页面名称
			$("#pageListUl").off("dblclick.changePageName").on("dblclick.changePageName",".name",function(){
				var $input = $(this);
				$input.removeAttr("disabled").css({
					"background":"#FFF",
					"color":"#222430",
					"border": "1px dashed #03a9f4"
				});
			});
			
			//修改名称后
			$("#pageListUl").off("focusout.changePageName").on("focusout.changePageName",".name",function(){
				$(this).attr("disabled","disabled").removeAttr("style");
				$(this).attr("value",$(this).val());
			});	
		};changePageName();
		
		//ini方法
		var iniEvent = function(){
			//绑定方法
			delPage();
			selectPage();
			addPage();
			clonePage();
		};iniEvent();
	}
		
	//暴露接口
	exports.pageListEvent = function(){
        pageListEvent();
    }
});