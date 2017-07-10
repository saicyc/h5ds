//布局
define(function(require,exports,module){
	var c = require('./sizeConfig');
	
	//设置初始参数
    var setCenterDiv = function(){
		$("#center").width(c.size.wind_width - c.size.left_width - c.size.right_width).height(c.size.wind_height - c.size.top_height);
		$("#left").height(c.size.wind_height - c.size.top_height);
		$("#pageList").height(c.size.wind_height - c.size.top_height - c.size.addPage_height);
		$("#right").height(c.size.wind_height - c.size.top_height);
		$("#cPhone").center2({top: c.size.cPhone_top+'px'});
		$("#picList").height(c.size.wind_height - c.size.dataSetHeight - c.size.top_height - c.size.addPage_height - c.size.cutOffRule + 10);
	};
	
	//当浏览器大小变化时
	$(window).resize(function () {
		c.size.wind_width = $(window).width(),
		c.size.wind_height = $(window).height()
		setCenterDiv();
	});
	
	//暴露接口
	exports.initPage = function(){
        setCenterDiv();
    }
	
});