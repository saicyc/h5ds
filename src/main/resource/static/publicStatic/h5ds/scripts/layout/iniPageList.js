//获取图片素材
define(function(require,exports,module){
	
	var allObj = require('./objConfig');
	
	//初始化页面列表区域
	var iniPageList = function(){
		var $returnData = $("#returnData");
		var $coolapp = $returnData.find("#coolapp");
		//pages 对象
		var pages = {
			$pages : $coolapp.find(".page"), //所有的page对象
			num : null, //总共页
			arr : [],
			music : $coolapp.attr("data-music"),//如果没音乐，就是false
			slider : $coolapp.attr("data-slider"),//滑动样式，数字 1 ~ 7
			cayica : $coolapp.attr("data-cayica") //擦一擦 ，没有就是false
		};
		//遍历对象
		pages.$pages.each(function(){
			var page = {
				name : $(this).attr("pageName"), //名称、
				content : $(this).html() //内容
			};
			//赋值
			pages.arr.push( '<li>'+
							'	<a href="javascript:void(0)" class="del"><i class="iconfont icon-close"></i></a>'+
							'	<div class="dragdiv"><input type="text" value="'+page.name+'" class="name" disabled="" data-html='+"'"+page.content+"'"+'></div>'+
							'</li>');
		});
		//渲染
		allObj.obj.$pageListUl.html(pages.arr);
		
		//初始添加音乐的参数
		if(pages.music == "true"){
			var url = $("#returnData .coolappSong").attr("src");	
			$("#appset_music_val").val(url);
			$("#coolappSong").attr("src",url);
			//$("#appset_music_switch").addClass("off");
		}else{
			$("#coolappSong")[0].pause();
			$("#soundbox").css("display","none");
			$("#appset_music_switch").addClass("off");	
		}
		
		
	};
	
	//暴露接口
	exports.iniPageList = function(){
		iniPageList();
    }
	
});