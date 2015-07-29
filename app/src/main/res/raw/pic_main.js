/**
 * Created with JetBrains WebStorm.
 * User: Predaking
 * Date: 13-10-24
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */

var picWid = ($(window).width()) * 0.93 * 0.92;
init(40,"mytest",picWid,picWid,main);

/**层变量*/
//显示进度条所用层
var loadingLayer;
//游戏最底层
var backLayer;

var loadIndex = 0;
//图片path数组
var imgData = new Array();
//读取完的图片数组
var imglist = {};
var imgLength = 6;

var curIndex;
var buferIndex;
var gameLevel;

var state;
var bmpData;
var gameBmp;
var bmpBack;
//var bmpTiles;
//var bmpTileBack;

var teamTiles;
var gamePoint;


var backLayer;
var frameLayer = null;

var offsetX = 0;
var offsetY = 0;
var tileWH;

var txt;


function main(){
    LGlobal.setDebug(true);

    imgLength = parseInt($(".section").length);

    var pathName = "../../project/" + $(".file-name").text();

    //准备读取图片
    for(var i = 0; i < imgLength; i++)
    {
            var a = i+1;
            imgData.push({name:"pic"+i, path: pathName + "/picture_"+a+".jpg"});
    }
    imgData.push({type:"js",path:"../../js/glb.js"});
    imgData.push({type:"js",path:"../../js/show_tile.js"});



    loadingLayer = new LoadingSample1();
    addChild(loadingLayer); 
    LLoadManage.load(
        imgData,
        function(progress){
            loadingLayer.setProgress(progress);
        },
        function(result){
            imglist = result;
            removeChild(loadingLayer);
            loadingLayer = null;
            gameInit();
        }
    );
}


var ls1;
function gameInit(){
    backLayer = new LSprite();
    addChild(backLayer);
//    frameLayer = new LSprite();
//    addChild(frameLayer);

    bmpData = new Array();
    for(var i = 0; i < imgLength; i++)
    {
        var lbd = new LBitmapData(imglist["pic"+i]);
        var wid = lbd.width;

        bmpData.push(lbd);
    }
    bmpBack = new LBitmap(new LBitmapData(imglist["back"]));


    gameLevel = 3;
//    newGame();
    setState(Global.STATE_RUN);

    //添加贞事件，开始游戏循环
    backLayer.addEventListener(LEvent.ENTER_FRAME,updateFrame);
    //添加控制事件
    backLayer.addEventListener(LMouseEvent.MOUSE_DOWN,ondown);
    backLayer.addEventListener(LMouseEvent.MOUSE_UP,onup);
    trace("5");
}



function newGame(){

    backLayer.removeAllChild();
    backLayer.addChild(bmpBack);
//    frameLayer.removeAllChild();

    if(frameLayer != null){
        removeChild(frameLayer);
    }
    frameLayer = new LSprite();
    addChild(frameLayer);


    gamePoint = gameLevel * gameLevel;

    var tempArys = new Array();
    for(var i = 0; i < imgLength; i++)
    {
        tempArys.push(i);
    }
    // 得到图片数组
    var ids = Global.getRandomArray(tempArys,imgLength);

    // 把当前图片设为正确图片
    var currentNum = $(".active .current-num").text();
    gameBmp = new LBitmap(bmpData[currentNum]);

    teamTiles = new Array();
    //把用到的图块拆出来，放到2维数组bmpTiles里
    var x = 0;
    var y = 0;
    var len = Global.getInt(gameBmp.getWidth()/gameLevel);
    var imgWid = gameBmp.getWidth();

    var imgScale = picWid/imgWid;

    //框子的宽高信息
    tileWH = getSideSize();
//    touchRects = new Array();
    // 位置信息
    var xfrm = offsetX;
    var yfrm = offsetY;

//    trace("2");
    for(var i = 0; i < gameLevel; i++){
        for(var j = 0; j < gameLevel; j++){
            //tile
            var tileTeam = new ShowTile(getMaxWaitCount());
            for(var id = 0; id < ids.length; id++){
                var tempBmpData = new LBitmapData(imglist["pic"+id],x,y,len,len);

                var tempBmp = new LBitmap(tempBmpData);
                var tempSpr = new LSprite();
                tempSpr.addChild(tempBmp);

                tempSpr.scaleX = imgScale;
                tempSpr.scaleY = imgScale;
                Global.TILE_ZOOM = imgScale;
                tileTeam.addTile(tempSpr);
            }
            tileTeam.randomTiles();
            tileTeam.setRect(xfrm ,yfrm,tileWH,tileWH);
            teamTiles.push(tileTeam);
            backLayer.addChild(tileTeam);

            x += len;
            //框子的边和填充色
            //frameLayer.graphics.drawRect(10,"#000",[xfrm ,yfrm,tileWH,tileWH],false);
            //frameLayer.graphics.drawRect(2,"#777",[xfrm ,yfrm,tileWH,tileWH],false);
            xfrm += tileWH;
        }
        x = 0;
        y += len;
        xfrm = offsetX;
        yfrm += tileWH;
    }

}

function setState(ste){
    state = ste;
    switch (state){
        case Global.STATE_RUN:
            newGame();
            break;
        case Global.STATE_END:
            endGame();
            break;
    }
}

function endGame(){


    setTimeout("showSelector('.active .correct-res');", 400)

    setTimeout("$('.active .images #mytest').remove();", 600);
    setTimeout("showSelector('.active .right-img');", 700);

    $(".active").removeClass("hasPuzzle");
    allowScrolling();

    setTimeout("bindActiveClick()", 1000);

}

function getMaxWaitCount(){
    return 25;
}

function getSideSize(){
    return Global.getInt(picWid/gameLevel);
}

function updateFrame(){
//    trace("5");
//    txt.text = "请按照上面的图选择图块" + gamePoint;
    switch (state){
        case Global.STATE_RUN:
            updateGame();
            break;
        case Global.STATE_END:
            updateGameEnd();
            break;
    }
}
function updateGameEnd(){
    if(isTouch){
        setState(Global.STATE_END);
        //isTouch = false;
    }
}

function updateGame(){
    var point = 0;
    for(var i = 0; i < teamTiles.length; i++){
        teamTiles[i].update();
        if(isTouch){
            teamTiles[i].onClicked(LGlobal.offsetX,LGlobal.offsetY);
        }
        if(teamTiles[i].isRight){
            point++;
        }
    }
    isTouch = false;
    if(point == gamePoint){
        setState(Global.STATE_END);
    }
}

function onup(event){
    isTouch = false;
}
var isTouch = false;
function ondown(event){
    isTouch = true;
}

























