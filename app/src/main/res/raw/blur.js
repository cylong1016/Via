window.onload=function(){              //not DOM

	// $(".sel").hide();

	var h = $(".images img").height();
	$(".sel").height(h);

	addSel();				// 添加选项
	setAnswer();
}

function setAnswer () {
	var answer_num = $(".active .sel").children().length;
	var answer_length = $(".answer").height();
	// alert(answer_length);
}

function addSel(){

	if (!$(".active").hasClass("hasSel")) {
		$(".active").click(function(){
			$.fn.fullpage.moveSectionDown();
		})
		return;
	}
	else{
		// 禁止向下滑
		$.fn.fullpage.setAllowScrolling(!1);
	}

	$(".hasSel").click(function(){
		showSelector(".active .sel");
		jump();
		$(".hasSel").unbind("click");
	})

}

function jump(){
	$(".active .answer").click(function(e){
			var ev=e || event;  
			var id=ev.target.id;//获取鼠标按下对应的对象的id   
			if (id == "right") {
				$.fn.fullpage.setAllowScrolling(!0);
				$(".active").removeClass("hasSel");
				$(".active .sel").animate({opacity:'0.0'},function(){
					$(".active .sel").remove();
					$.fn.fullpage.moveSectionDown();
				});	
				
			}
			else{
				// $(".section").remove();
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

function showSelector(selector){
	var $e = $(selector);
	$e.css({"display": "block", "opacity": "0"})
		.stop().animate({opacity: "1"});
}