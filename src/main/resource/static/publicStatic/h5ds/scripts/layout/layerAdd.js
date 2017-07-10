//页面整体参数，暴露出去
define(function(require,exports,module){
	var allObj = require('./objConfig');
	var ifun = require('./ifunction');
	
	//添加图层操作
	var layerAdd = function(){
		
		//自动选中添加的图层
		var clickLast = function(){
			$("#picListUl").find("li").last().trigger("click");
		};
		
		//添加图片图层
		$("#addPicButton").on("click",function(){
			var html = allObj.obj._picListUlli;
			html = html.replace("{{typeico}}",'<i class="fa fa-file-picture-o"></i>');	
			html = html.replace("{{name}}",'图片图层');	
			$("#picListUl").append(html);
			$("#cPhoneEdit .wrap").append(
			'<div class="layer layer-pic" name="图片图层" realsize="100,100" style=" border:0; top:10px; left:10px; width:100px; height:100px; z-index:'+(allObj.obj.maxZIndex - $("#picListUl li").length)+';">'+
			'   <img class="element" src="">'+
			'</div>');
			clickLast();
		});
		
		//添加文本图层
		$("#addTextButton").on("click",function(){
			var html = allObj.obj._picListUlli;
			html = html.replace("{{typeico}}",'<i class="fa fa-font"></i>');	
			html = html.replace("{{name}}",'文字图层');	
			$("#picListUl").append(html);
			$("#cPhoneEdit .wrap").append(
			'<div class="layer layer-text" name="文字图层" style="text-align: left; font-size:24px; top: 10px; left: 10px; width: 200px; height: 100px; z-index: '+(allObj.obj.maxZIndex - $("#picListUl li").length)+';">'+
			'	<div class="element"><div class="el-text">文字内容</div></div>'+
			'</div>');
			clickLast();
		});
		
		//添加特效图层
		$("#addEffectButton").on("click",function(){
			var html = allObj.obj._picListUlli;
			html = html.replace("{{typeico}}",'<i class="fa fa-magic"></i>');	
			html = html.replace("{{name}}",'特效图层');	
			$("#picListUl").append(html);
			$("#cPhoneEdit .wrap").append('<div class="layer layer-effect" name="特效图层" style="top: 0; left: 0; z-index: '+(allObj.obj.maxZIndex - $("#picListUl li").length)+';"></div>');
			clickLast();
		});

		//添加表单
		$("#addFormButton").on("click",function(){
			var html = allObj.obj._picListUlli;
			html = html.replace("{{typeico}}",'<i class="iconfont icon-biaodan"></i>');	
			html = html.replace("{{name}}",'表单图层');	
			$("#picListUl").append(html);
			$("#cPhoneEdit .wrap").append('<div class="layer layer-form" name="表单图层" style="top: 20px; left: 20px; width:600px; height:60px; background:#fff; z-index: '+(allObj.obj.maxZIndex - $("#picListUl li").length)+';">'+
			'	<input class="element g-input" type="text" placeholder="姓名"/>'+
			'</div>');
			clickLast();
		});
		
		//添加幻灯片图层
		$("#addSliderButton").on("click",function(){
			var html = allObj.obj._picListUlli;
			html = html.replace("{{typeico}}",'<i class="fa fa-stack-overflow"></i>');	
			html = html.replace("{{name}}",'幻灯片图层');	
			$("#picListUl").append(html);
			$("#cPhoneEdit .wrap").append(
			'<div class="layer layer-slider" name="幻灯片图层" style="top: 0; left: 0; width:640px; height:400px; background-color:#eee; z-index: '+(allObj.obj.maxZIndex - $("#picListUl li").length)+';">'+
			'<ul></ul></div>');
			clickLast();
		});

		//添加iframe
		$("#addIframeButton").on("click",function(){
			var html = allObj.obj._picListUlli;
			html = html.replace("{{typeico}}",'<i class="iconfont icon-xinjian"></i>');	
			html = html.replace("{{name}}",'iframe图层');	
			$("#picListUl").append(html);
			$("#cPhoneEdit .wrap").append(
			'<div class="layer layer-iframe" name="iframe图层" style="top: 10px; left: 10px; width: 200px; height: 100px; z-index: '+(allObj.obj.maxZIndex - $("#picListUl li").length)+';">'+
			'	<div class="element"><div class="iframe-hide"></div><iframe src="" style="width:100%; height:100%;" frameborder="0"></iframe></div>'+
			'</div>');
			clickLast();
		});
		
		//复制图层
		$("#copyLayerButton").on("click",function(){
			if(allObj.obj.$layer[0] != undefined){
				
				//如果是背景图层。不可复制
				if( allObj.obj.$layer.selector == "#cPhoneEdit .wrap"){
					alert("背景图层不可复制！");	
				}
				else{
					var $picListUl =  $("#picListUl");
					$picListUl.append($picListUl.find(".active").clone().removeClass("active"));
					$("#cPhoneEdit .wrap").append(allObj.obj.$layer.clone().css("z-index", (allObj.obj.maxZIndex - $("#picListUl li").length)));		
					clickLast();
				};
				
			}else{
				alert("请先选择图层！");	
			};	
		});
	
	}
	
	//布局参数 - 入口
	exports.layerAdd = function(){
		layerAdd();
	};
	
});