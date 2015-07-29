
window.onload=function(){              //not DOM

	var h = $(".images img").height();
	$(".sel").height(h);
	$(".treasure").height(h);
	$(".treasure").width(h);

	hasGame();

}

function hasGame(){
	if(hasSel()){
		return;
	}
	else if(hasTreasure()){
		return;
	}
	else if(hasPuzzle()){
		return;
	}
}


function hasSel(){

	if (!$(".active").hasClass("hasSel")) {
		$(".active").click(function(){
			$.fn.fullpage.moveSectionDown();
		})
		return false;
	}
	else{
		// 禁止向下滑
		forbidScolling();
	}

	$(".hasSel").click(function(){
		showSelector(".active .sel");
		jump();
		$(".hasSel").unbind("click");
	})

	return true;

}

function jump(){
	$(".active .answer").click(function(){
			//var ev=e || event;
			//var id=ev.target.id;//获取鼠标按下对应的对象的id
			if($(this).hasClass("correct")){
				allowScrolling();
				$(".active").removeClass("hasSel");
				$(".active .sel").animate({opacity:'0.0'},function(){
					$(".active .sel").remove();
					$.fn.fullpage.moveSectionDown();
				});	
				
			}
			else{
				addGameOver();
			}
		})
}

function addGameOver(){
	var bg = "<div class='game_over'><div class='reset_form'></div></div>";
	var over_text = "<h1>Game Over</h1>";
	var reset = "<div class='reset'>重新开始</div>";

	$("body").prepend(bg);
	$(".reset_form").prepend(over_text);
	$(".reset_form").append(reset);

	$(".reset").click(function(){
		location.reload();
	})

}

function hasTreasure(){

	$(".active").unbind("click");
	if (!$(".active").hasClass("hasTreasure")) {
		$(".active").click(function(){
			$.fn.fullpage.moveSectionDown();
		})
		return false;
	}
	else{
		forbidScolling();
	}

	$(".hasTreasure").click(function(){
		var treasure_num = $(".active .treasure img").length;
		$(".active .treasure span i:first-child").text(treasure_num);
		showSelector(".active .treasure span");
		$(this).unbind("click");
		setTip()

	})

	return true;

}

function resetTreasure(){
	var treasure_num = $(".active .treasure img").length;
	var halfH = $(".active .images .treasure img").height()/2;
	for(var i=0; i<treasure_num; i++){
		var setLeft = $(".active .images .treasure img").eq(i).offset().left;
		var setTop = $(".active .images .treasure img").eq(i).offset().top;

		var imgLeft = $(".active .images img").offset().left;
		var imgTop = $(".active .images img").offset().top;

		$(".active .images .treasure img").eq(i).css({"left":setLeft-imgLeft-halfH, "top":setTop-imgTop-halfH});
	}
}

function setTip(){

	var treasure_num = $(".active .treasure img").length;

	var treasureH = $(".active .images .treasure img").height();

	resetTreasure();

	var treasureLeft = new Array();
	var treasureTop = new Array();

	for(var i=0; i<treasure_num; i++){
		treasureLeft.push($(".active .images .treasure img").eq(i).offset().left);
		treasureTop.push($(".active .images .treasure img").eq(i).offset().top);
	}

	// splice(i,i);

	var remain = parseInt($(".active .treasure span i:last-child").text());
	var find = 0;



	$(".active .treasure").click(function(ev){
		e = event || ev;

		var end = false;
		var isFind = false;

		// find

		for(var i=0; i<treasure_num; i++){
			var scaleL = e.clientX - treasureLeft[i];
			var scaleT = e.clientY - treasureTop[i];

			if((scaleL <= treasureH)&&(scaleT <= treasureH)&&(scaleL>0) &&(scaleT > 0) ) {
				var id = e.target.id;
				showSelector("#"+id);
				find++;
				remain+=2;
				$(".active .treasure span i").eq(1).text(find);
				$(".active .treasure span i").eq(2).text(remain);
				isFind = true;

				if(find == treasure_num){
					$(".active .treasure span").text("找到了所有的宝藏！");
					end = true;
				}

				treasureLeft.splice(i,i);
				treasureTop.splice(i,i);
				break;

			}
		}


		if(!isFind){ // not find
			remain--;
			if(remain != 0){
				$(".active .treasure span i:last-child").text(remain);
			}
			else{
				$(".active .treasure span").text("机会用完啦");
				end = true;

			}
		}

		if(end){
			$(".active .treasure").unbind("click");
			$(".active").removeClass("hasTreasure");
			allowScrolling();
			setTimeout("hideSelector('.active .images .treasure span')", 1000);
			setTimeout("bindActiveClick()", 1000);
		}


	})
}


function hasPuzzle(){
	$(".active").unbind("click");
	if (!$(".active").hasClass("hasPuzzle")) {
		$(".active").click(function(){
			$.fn.fullpage.moveSectionDown();
		})
		return false;
	}
	else{
		forbidScolling();
	}

	return true;
}


function showSelector(selector){
	var $e = $(selector);
	$e.css({"z-index":"100", "display": "block", "opacity": "0"})
		.stop().animate({opacity: "1"});
}

// 关闭某个选择器
function hideSelector(selector){
	var $e = $(selector)
	$e.stop().animate({opacity: "0"}, 400, function(){
		$(this).css("display", "none");
	});
}

function allowScrolling(){
	$.fn.fullpage.setAllowScrolling(!0);
}

function forbidScolling(){
	$.fn.fullpage.setAllowScrolling(!1);
}

function bindActiveClick(){
	$(".active").unbind("click");
	$(".active").click(function(){
		$.fn.fullpage.moveSectionDown();
	})
}