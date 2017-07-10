//页面整体参数，暴露出去
define(function(require,exports,module){
	var allObj = require('./objConfig');
	var ifun = require('./ifunction');
	var viewLock = 0 ; //如果是0，就不促发预览
	
	//添加图层操作
	var appSave = function(){
		
			//保存APP
			$("#saveCoolApp,#publishCoolApp").on("click",function(){

				//获取 微信参数 - 设置的参数
				//app参数
				var data = {
					name : "icoolapp.cn",
					describe : "最强大的HTML5在线编辑器",
					img : "",
					music : "",
					slideDirection : "updown", //默认上下滑动
					backgroundColor :　"inherit" ,//背景颜色，默认是无
					backgroundRepeat : "inherit" ,//背景平铺，默认
					backgroundImg : "inherit" ,//背景图片，默认
					cayica : ""
				};
				
				//设置名称
				data.name = $("#appset_name_val").val();	
				
				//设置描述
				data.describe = $("#appset_info_val").val();	
				
				//图片设置
				data.img = $("#appset_main_pic").attr("src");
				
				//音乐
				if($("#appset_music_switch").attr("class").indexOf("off") == -1){
					data.music = $("#appset_music_val").val();
				};
				
				//滑动方向
				var $appset_cpage_dialog = $("#appset_cpage_dialog");
				data.slideDirection = $appset_cpage_dialog.attr("data-sliderDire");
				if(data.slideDirection == "updown"){ //上下滑动
					//var arrowV ="";
					var sliderJS = '<script>var sideWipeType = "updown";</script><script src="/publicStatic/appRes/js/index_updown.js"></script>';
					var sliderDire = 'updown';
				}
				else{
					//上下切换
					//var arrowV = '-webkit-transform: rotate(-90deg)';
					var sliderJS = '<script>var sideWipeType = "leftRight";</script><script src="/publicStatic/appRes/js/index_leftright.js"></script>';
					var sliderDire = 'leftright';
				};
				
				//发布的时候，根据设置，重置参数
				switch($appset_cpage_dialog.attr("data-sliderType")){
					case "1": { allObj.obj.slide = allObj.obj.slider.slide1; allObj.obj.slideNo = 1;};break;
					case "2": { allObj.obj.slide = allObj.obj.slider.slide2; allObj.obj.slideNo = 2;};break;
					case "3": { allObj.obj.slide = allObj.obj.slider.slide3; allObj.obj.slideNo = 3;};break;
					case "4": { allObj.obj.slide = allObj.obj.slider.slide4; allObj.obj.slideNo = 4;};break;
					case "5": { allObj.obj.slide = allObj.obj.slider.slide5; allObj.obj.slideNo = 5;};break;
					case "6": { allObj.obj.slide = allObj.obj.slider.slide6; allObj.obj.slideNo = 6;};break;
					case "7": { allObj.obj.slide = allObj.obj.slider.slide7; allObj.obj.slideNo = 7;};break;
					case "8": { allObj.obj.slide = allObj.obj.slider.slide8; allObj.obj.slideNo = 8;};break;
					case "9": { allObj.obj.slide = allObj.obj.slider.slide9; allObj.obj.slideNo = 9;};break;
				};	
				
				console.log(allObj.obj.slide);
				
				//app背景色
				var $cPhoneEdit = $("#cPhoneEdit");
				data.backgroundColor = $cPhoneEdit.css("background-color");
				
				//app背景重复
				data.backgroundRepeat = $cPhoneEdit.css("background-repeat");
				
				//背景图片
				//console.log($cPhoneEdit.css("background-image"));
				if($cPhoneEdit.css("background-image") != "none"){
					data.backgroundImg = $cPhoneEdit.css("background-image").split("(")[1].split(")")[0];
				};
				
				//擦一擦
				console.log($("#appset_clear_switch").attr("class"));
				if($("#appset_clear_switch").attr("class").indexOf("off") == -1){
					data.cayica = $("#cayicaImage").attr("src");
				};
				
				//擦一擦
				var caImgHtml = "";
				var caImgHtml_data = "false";
				if(data.cayica != ""){
					caImgHtml = '<div class="cabox" id="cabox" dataBgImg="'+data.cayica+'">'+
								'	<canvas id="cas"></canvas>'+
								'</div>'+
								'<script src="/publicStatic/appRes/js/caimg.js"></script>';	
					caImgHtml_data = "true";
				};
				//音乐
				var musicHtml = "";
				var musicHtml_data = "false";
				if(data.music != ""){
					musicHtml = '<div class="soundbox" id="soundbox" data="on">'+
								'    <em class="c1"></em>'+
								'    <em class="c2"></em>'+
								'    <div class="soundico"></div>'+
								'    <audio id="coolappSong" class="coolappSong_mobile" style="display:none; visibility:hidden;" src="'+data.music+'" loop></audio>'+
								'</div>'+
								'<div class="soundinfo" id="soundinfo">关闭</div>'	
					musicHtml_data = "true";
				};
				
				var html = "";
				html+='<!--H5DS在线编辑平台：www.h5ds.cn ，请勿删掉该代码！谢谢支持！--><!DOCTYPE html>';
				html+='<html lang="zh-cn" class="no-js">';
				html+='		<head>';
				html+='			<meta http-equiv="Content-Type">';
				html+='			<meta content="text/html; charset=utf-8">';
				html+='			<meta charset="utf-8">';
				html+='			<title>'+data.name+'</title>';
//				html+='			<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">';
				html+='			<meta name="format-detection" content="telephone=no">';
				html+='			<meta name="format-detection" content="email=no">';
				html+='			<link rel="stylesheet" type="text/css" href="/publicStatic/appRes/css/effect/style.css">	';
				html+='			<link rel="stylesheet" type="text/css" href="/publicStatic/appRes/css/animations.css" />';
				html+='			<link rel="stylesheet" type="text/css" href="/publicStatic/appRes/css/animate.css">';
				html+='		    <script src="/publicStatic/appRes/js/zepto.min.js"></script>';
				html+='			<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>';
				html+='			<script src="/publicStatic/appRes/js/qrcode.min.js"></script>';
				html+='			<script src="/publicStatic/appRes/js/touch.js"></script>';
				html+='			<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>';
				html+='		</head>';
				html+='<body>';
				html+='<div class="loading" id="loading"><div class="load2"></div>已加载:<span id="loadnum"></span></div>';
				html+='<div id="coolapp" onselectstart="return true;" ondragstart="return false;" data-sliderDire="'+sliderDire+'" data-music="'+musicHtml_data+'" data-slider="'+allObj.obj.slideNo+'" data-cayica="'+caImgHtml_data+'" style="background-image:url('+data.backgroundImg+'); background-repeat:'+data.backgroundRepeat+'; background-color:'+data.backgroundColor.colorHex()+';" >';  
						$("#pageListUl li").each(function(index, element) {
							var $input = $(this).find("input");
							if( index == 0){
								html+='<div class="page page-'+(index+1)+'-1 page-current" pageName="'+$input.val()+'">'+$input.attr("data-html")+'</div>';
							}else{
								html+='<div class="page page-'+(index+1)+'-1 hide" pageName="'+$input.val()+'">'+$input.attr("data-html")+'</div>';	
							}
						});
				html+='</div>';
				//箭头
		//		html+='<div id="arrowV" class="arrow_v" style="'+arrowV+'"><p class="move"></p></div>'+
		//			    '<div id="arrowH" class="arrow_h f-hide">'+
		//			    '    <span class="arrow_l"></span>'+
		//			    '    <span class="arrow_r"></span>'+
		//			    '</div>';
				//音乐
				html+= musicHtml;
				html+='	<script>var _weixinData = { name:"'+ data.name+'", describe:"'+data.describe+'", img:"'+data.img+'"};</script>'; //给微信传递参数 
				html+='	<div class="coolapp_map"><a class="coolapp_map_close" href="javascript:;">关闭</a><div class="coolapp_map_title"><i class="ico"></i><span class="title">地点名称</span><span class="bg"></span></div><div id="coolapp_map"></div></div>';
				//擦一擦
				html+= caImgHtml;
				html+='</body>';
				html+='	<script>'+ allObj.obj.slide +'</script>' + sliderJS;//滑动方向的js
				html+='</html>';
				
				//console.log("weixin:",data);
				//console.log(html);
				//return false;

				//$("#html").html('<pre>'+html+'</pre>');
				var $fixedBox = $("#fixedBox");
				var $infoBox = $("#infoBox");
				$fixedBox.fadeIn(1000);
				
				var app = $(this).attr("data-app");
				////////////////////////////
				if(app == "save"){
					$infoBox.html("保存中...").fadeIn(1000);
					//保存APP
					$.ajax({
						url:"/app/saveHtml",
						data:{"appId":allObj.obj.userAppId,"html":html},//"title":data.name,"describe":data.describe,"mainImg":data.img},
						dataType:"json",
						type:"post",
						success:function(data){
							//console.log(data);
							if(data){
								$infoBox.html("保存成功！");
								$fixedBox.fadeOut(500);
								$infoBox.fadeOut(1000);
								viewLock = 1;
							}else{
								$infoBox.html("保存失败！");
								$fixedBox.fadeOut(500);
								$infoBox.fadeOut(1000);
							}
						}
					});
				}else if(app == "publish"){
					$infoBox.html("发布中...").show();
					//发布APP
					$.ajax({
						url:"/app/publishApp",
						data:{"appId":allObj.obj.userAppId,"html":html},
						dataType:"json",
						type:"post",
						success:function(data){
							//console.log(data);
							if(data == true){
								$infoBox.html("发布成功！");
								$fixedBox.fadeOut(500);
								$infoBox.fadeOut(1000);
								viewLock = 1;
							}else{
								$infoBox.html("发布失败！");
								$fixedBox.fadeOut(500);
								$infoBox.fadeOut(1000);	
							}
						}
					});	
				}
				
				
			});
	};
	
	//预览APP
	var viewApp = function(){
		
		$("#viewCoolApp").showWindow({id:"previewApp"});
		
		//预览APP
		$("#viewCoolApp").on("click",function(){	
			$.ajax({
				url:"/app/previewApp",
				type:"post",
				async:false,
				data: {appId:allObj.obj.userAppId},
				dataType:"json",
				success:function(data){
					console.log("==>",data);
					//var tempwindow = window.open('about:blank');
					//tempwindow.location=data.app_path;
					//$("#fixedBox").fadeIn(500);
					$("#previewAppIframe").attr("src",data.appHtml);
					
					//绑定事件
					$("#fixedBox").off("click.previewApp").on("click.previewApp",function(){
						$("#previewApp .close").trigger("click");
					});
					
				}
			});	
			//$("#saveCoolApp").trigger("click");
			//进入循环队列
//			var set = setInterval(function(){
//				if(viewLock == 1){
//					clearInterval(set);
//					viewLock = 0;
//					console.log("只执行一次");
//					$.ajax({
//						url:"/UserApp/previewApp",
//						type:"post",
//						async:false,
//						data: {id:allObj.obj.userAppId},
//						dataType:"json",
//						success:function(data){
//							console.log("==>",data);
//							//var tempwindow = window.open('about:blank');
//							//tempwindow.location=data.app_path;
//							$fixedBox.fadeIn(500);
//							$("#previewAppIframe").attr("src",data.app_path);
//							
//						}
//					});	
//				};	
//			},500);
		});	
	};
	
	//布局参数 - 入口
	exports.appSave = function(){
		appSave();
		viewApp();
	};
	
});