//APP基础设置
define(function(require,exports,module){
	var ifun = require('./ifunction');
	var allObj = require('./objConfig');
	
	//初始化一些方法
	var iniAppBasicSet = function(){
		
		//初始化快捷操作区域的内容
		var iniBasicSet = function(){
			
			var $coolapp = $("#coolapp");
			
			//初始判断,如果不存在，就设置个默认值 // 在谷歌低版本下面会报错！使用异常抓取
//			try{
//				_weixinData;
//			}catch (e){  
//				var _weixinData = {
//					name : "coolapp 最强的H5编辑器",
//					describe : "免费，开源，可下载！超级强大，灰常给力！",	
//					img : "#"
//				};
//			}
			
			//高版本谷歌有效
			if( typeof(_weixinData) == "undefined" ){
				console.log("不存在的哦~");
				_weixinData = {
					name : "coolapp 最强的H5编辑器",
					describe : "免费，开源，可下载！超级强大，灰常给力！",	
					img: "/Public/coolapp/images/wx.jpg"
				};
			};
			
			//初始化微信基础参数
			$("#appset_name_val").val(_weixinData.name);
			$("#appset_info_val").html(_weixinData.describe);
			$("#appset_main_pic").attr("src",_weixinData.img);
			
			//初始化音乐链接地址
			var mp3Url = $(".coolappSong_mobile").attr("src");
			if(mp3Url != ""){
				$("#appset_music_val").val(mp3Url);
				$("#coolappSong").attr("src",mp3Url);
				$("#soundbox").css("display","block");
			}
			
			//翻页效果
			//$(".appset_cpage_radio[]")
			var sliderType = $coolapp.attr("data-slider");
			var sliderDire = $coolapp.attr("data-sliderDire");
			var $appset_cpage_ul = $("#appset_cpage_ul");
			$appset_cpage_ul.find("li").removeClass("active");
			$appset_cpage_ul.find("li[data-slider="+sliderDire+"][data-value="+sliderType+"]").addClass("active");
			$("#appset_cpage_dialog").attr({
				"data-sliderType" : sliderType,
				"data-sliderDire" : sliderDire
			});
			
			//整体背景
			var cpageData = {
				color: "inherit",
				repeat : "inherit",
				src : "inherit"
			};
			var $cpageBackgroundRepeat = $("#cpageBackgroundRepeat");
			cpageData.color = $coolapp.css("background-color").colorHex();
			$("#cpageBackgroundColor").val(cpageData.color);
			switch($coolapp.css("background-repeat")){
				case "repeat": { $cpageBackgroundRepeat.find("option[value='repeat']").attr("selected",true); cpageData.repeat = "repeat"; };break;
				case "repeat-x": { $cpageBackgroundRepeat.find("option[value='repeat-x']").attr("selected",true); cpageData.repeat = "repeat-x"; };break;
				case "repeat-y": { $cpageBackgroundRepeat.find("option[value='repeat-y']").attr("selected",true); cpageData.repeat = "repeat-y"; };break;
				case "no-repeat": { $cpageBackgroundRepeat.find("option[value='no-repeat']").attr("selected",true); cpageData.repeat = "no-repeat"; };break;
			};
			cpageData.src = $coolapp.css("background-image").replace("url(","").replace(")","");
			$("#cpageBackgroundImage").attr("src",cpageData.src);
			
			$("#cPhoneEdit").css({
				"background-color":cpageData.color,
				"background-repeat":cpageData.repeat,
				"background-image":"url('"+cpageData.src+"')"
			});
			
			//擦一擦
			if($coolapp.attr("data-cayica") == "true"){
				$("#appset_clear_switch").attr("class","switch on")
			};
			
		};iniBasicSet();
		
		//弹窗可拖动
		$(".setWindowBox h1").dragMt({dragParent:true});

		//点击微信主图设置 打开弹窗
		$("#changeAppMainImage").on("click",function(){
			allObj.obj.selectImgResourcesLock = 1;
			$("#resources_btn").trigger("click");
		});
		
		//背景设置
		$("#changeCpageBackgroundImage").on("click",function(){
			allObj.obj.selectImgResourcesLock = 2;	
			$("#resources_btn").trigger("click");
		});
		//移除背景图
		$("#changeCpageBackgroundImageClear").on("click",function(){
			allObj.obj.$cPhoneEdit.css("background-image","inherit");
			$("#cpageBackgroundImage").attr("src","");
		});
		
		//擦一擦设置
		$("#changeCayicaImage").on("click",function(){
			allObj.obj.selectImgResourcesLock = 3;	
			$("#resources_btn").trigger("click");
		});
		//移除擦一擦
		$("#changeCayicaImageClear").on("click",function(){
			$("#cayicaImage").attr("src","");
		});
		
		//背景音乐,擦一擦开关,
		$(".slideSwitch").on("click",".switch",function(){
			if($(this).attr("class").indexOf("off") != -1){
				$(this).removeClass("off");
			}else{
				$(this).addClass("off");	
			};
		});
		
		//设置背景颜色
		$("#cpageBackgroundColor").on("change",function(){
			allObj.obj.$cPhoneEdit.css("background-color", $(this).val().colorHex());
		});
		
		//清除背景色
		$("#clear_cpageBackgroundColor").on("click",function(){
			allObj.obj.$cPhoneEdit.css("background-color","inherit");
			$("#cpageBackgroundColor").val("");	
		});
		
		//设置背景的重复
		$("#cpageBackgroundRepeat").on("change",function(){
			allObj.obj.$cPhoneEdit.css("background-repeat", $(this).val());
		});
			
		//筛选
		var switchfun = function(num,val){
			var $appset_cpage_dialog = $("#appset_cpage_dialog");
			$appset_cpage_dialog.attr({
				"data-sliderType":num,
				"data-sliderDire":val
			});
		};
		
		//切换 - 换页效果
		var $appset_cpage_ul = $("#appset_cpage_ul");
		$appset_cpage_ul.on("click","li",function(){
			$appset_cpage_ul.find("li").removeClass("active");
			$(this).addClass("active");
			var num = $(this).attr("data-value");
			var val = $(this).attr("data-slider");
			switchfun(num,val);
		});

	};
	
	//初始化擦一擦
	var iniCayica = function(){
		if($("#cabox")[0]){
			var src = $("#cabox").attr("databgimg");
			$("#cayicaImage").attr("src",src);
		};
	};iniCayica();
	
	//初始化音乐
	var iniAppMusic = function(){

		//音乐开启或者关闭
		/**
		*	声音是全局的
		*	
		*/
		var soundPlay = {
			$soundbox : $("#soundbox"),
			$soundinfo : $("#soundinfo"),
			$song : document.getElementById("coolappSong"),
			$coolappSong : $("#coolappSong"),
			
			//声音图标的显示和隐藏
			soundIcoFade : function(str){
				soundPlay.$soundinfo.html(str).fadeIn();
				soundPlay.$soundbox.off("click");//动画结束之前，解除点击事件
				setTimeout(function(){
					soundPlay.$soundinfo.fadeOut(500);
					soundPlay.iniBindToggle();//重新绑定点击事件
				},500);
			},
			
			//开启音乐或者关闭音乐
			offOrOnSound : function($this){
				if($this){ //如果有音乐
					if($this.attr("data") == "on"){
							$this.removeClass("soundbox").addClass("soundbox-close").attr("data","off");
							soundPlay.soundIcoFade("关闭");
							soundPlay.$song.pause();
					}
					else{
						$this.removeClass("soundbox-close").addClass("soundbox").attr("data","on");
						soundPlay.soundIcoFade("开启");	
						soundPlay.$song.play();
					}
				}
				else; //没有音乐
			},
			
			//绑定点击事件
			iniBindToggle : function(){
				if(soundPlay.$coolappSong.attr("src") == ""){
					soundPlay.$soundbox.css("display","none");
					//return false;
				}
				else{
					soundPlay.$soundbox.css("display","block");
				}
				soundPlay.$soundbox.click(function(){
					soundPlay.offOrOnSound($(this));
				});	
			}
		};	

		//解决音乐不自动播放问题
		//点击第一页面时播放音乐
		function playSoundTouchPageOne(){
			var hander = function(){
				soundPlay.$song.play();
				window.removeEventListener('touchstart', hander, false);
			};
			window.addEventListener('touchstart', hander, false);
		};
		
		//图片加载完后，播放音乐
		function playMp3(){
			if(soundPlay.$song){
				soundPlay.$song.play(true);
				setTimeout(function(){
					$("#soundinfo").fadeOut(500);
				},1000);
			}
			else ;//console.log("没上传音乐哦！");
		};
		
		//初始化音乐绑定事件
		soundPlay.iniBindToggle();
		//自动播放音乐
		playMp3();
		//解决音乐不自动播放的问题
		playSoundTouchPageOne();
		
		//音乐设置
		$("#appset_music_switch").on("click",function(){
			console.log($(this).attr("class"));	
			var classN = $(this).attr("class");
			if(classN.indexOf("off") != -1){
				console.log("开启");	
				soundPlay.$soundbox.css("display","block");
				soundPlay.$song.play();
			}else{
				console.log("关闭");
				soundPlay.$soundbox.css("display","none");
				soundPlay.$song.pause();	
			}
		});
		
		//添加音乐链接
		$("#appset_music_val").on("change",function(){
			soundPlay.$coolappSong.attr("src",$(this).val());	
		});
		
		//获取音乐链接
		var getMp3Url = function(){
			$.ajax({
				url:"/app/findMusic",
				type:"post",
				dataType:"json",
				success:function(data){
					console.log("音乐：",data);
					var shtml = '<option value="">选择音乐</option>';
					for(var i=0; i<data.length; i++){
						shtml+='<option value="'+data[i].filePath+'">'+data[i].musicName+'</option>';
					}
					$("#select_appset_music_val").html(shtml);
					
					//选择音乐事件绑定
					$("#select_appset_music_val").on("change",function(){
						var thisVal = $(this).val();
						soundPlay.$coolappSong.attr("src",thisVal);
						$("#appset_music_val").attr("value",thisVal).val(thisVal);
					});
				}
			});
		};getMp3Url();

	};
	
	//暴露接口
	exports.iniAppBasicSet = function(){
        iniAppBasicSet();
		iniAppMusic();
    };
});