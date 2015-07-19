// $(document).ready(function(){
// 	var h = $(".images img").height();
// 	alert(h);
// 	// $(".sel").
// })

window.onload=function(){              //not DOM

	// $(".sel").hide();

	//addSel();				// 添加选项
}

function addSel(){

	// 如果你要写死，这一段就不用写，写在html里面
	var sel = "<div class='sel'></div>";
	var answerA = "<div id='A' class='answer'>A. 今天好天气</div>";
	var answerB = "<div id='B' class='answer'>B. 今天下雨了</div>";
	$(".images").append(sel);
	$(".sel").append(answerA);
	$(".sel").append(answerB);
	//到这里

	// 写死的，用hide和show来显示
	// $(".sel").show();

	var h = $(".images img").height();
	$(".sel").height(h);

	$(".answer").click(function(e){
		var ev=e || event;
		var id=ev.target.id;//获取鼠标按下对应的对象的id
		if (id == "A") {
			$(".sel").remove();
		}
		else{
			$(".section").remove();
			addGameOver();
		}
	})
}

function addGameOver(){
	var bg = "<div class='section game_over'></div>";
	var over_text = "<h1>Game Over</h1>";
	var reset = "<div class='reset'>重新开始</div>"

	$("#fullpage").prepend(bg);
	$(".game_over").prepend(over_text);
	$(".game_over").append(reset);

	$(".reset").click(function(){
		location.reload();
	})

}