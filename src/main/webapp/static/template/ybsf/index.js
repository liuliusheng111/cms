var speedwidth = 1;//每单位时间移动距离
var pSecond = 30;//单位时间毫秒数
var ulWidth = 0;//行宽
window.onload = function(){
    var lis = document.getElementById("marq").getElementsByTagName("li");
    var ul = document.getElementById("marq").getElementsByTagName("ul");
    lisNumber = lis.length;
    for(var i =0; i < lisNumber; i++){
        ulWidth += lis[i].clientWidth + 10;
    }
    if(parseInt(document.getElementById("marq").offsetWidth) < ulWidth){
        for(i =0; i < lisNumber; i++){
            ul[0].appendChild(lis[i].cloneNode(true));
        }
        ul[0].style.width = 2 * ulWidth + 'px';
        var id = setInterval("scrollY()",pSecond);
    }else{
        ul[0].style.width = ulWidth + 'px';
    }
}
//滚动
function scrollY(){
    document.getElementById("marq").scrollLeft += speedwidth;
    if(document.getElementById("marq").scrollLeft >= ulWidth){
        document.getElementById("marq").scrollLeft = 0;
    }
}

//滑动门
function hover(object,id){
    var parent = object.parentNode;
    var spanNodes = parent.getElementsByTagName("span");
    var divNodes = document.getElementById(id).getElementsByTagName("div");
    
    var n = 0;
    var sheetNodes = new Array();
    var j = 0;
    for(var j in divNodes){
        if(divNodes[j].className == "sheet"){
            sheetNodes[n] = divNodes[j];
            n++;
        }
    }
    
    for(var i=0; i < sheetNodes.length; i++){
        if(spanNodes[i] === object){
            spanNodes[i].className = "hover";
            sheetNodes[i].style.display = "block";
        }else{
            spanNodes[i].className = "";
            sheetNodes[i].style.display = "none";
        }
    }
}
$(function(){
    //焦点图
    $("#KinSlideshow").KinSlideshow({
        mouseEvent:"mouseover",//注掉这行代码，首页焦点图的切换方式就会改成点击切换
        titleBar:{titleBar_height:40}
    });
    $("#KinSlideshow2").KinSlideshow({
        mouseEvent:"mouseover",//注掉这行代码，首页焦点图的切换方式就会改成点击切换
        titleBar:{titleBar_height:40}
    });
    //人物滚动
    var speedL=40; //数字越大速度越慢
    var tabL=document.getElementById("demoL");
    var tabL1=document.getElementById("demoL1");
    var tabL2=document.getElementById("demoL2");
    tabL2.innerHTML=tabL1.innerHTML;
    function MarqueeL(){
    if(tabL2.offsetWidth-tabL.scrollLeft<=0)
    tabL.scrollLeft-=tabL1.offsetWidth
    else{
    tabL.scrollLeft++;
    }
    }
    var MyMarL=setInterval(MarqueeL,speedL);
    tabL.onmouseover=function() {clearInterval(MyMarL)};
    tabL.onmouseout=function() {MyMarL=setInterval(MarqueeL,speedL)};

    var speed=50; //数字越大速度越慢
    var tabLD=document.getElementById("demoLD");
    var tabLD1=document.getElementById("demoLD1");
    var tabLD2=document.getElementById("demoLD2");
    tabLD2.innerHTML=tabLD1.innerHTML;
    function Marquee(){
    if(tabLD2.offsetWidth-tabLD.scrollLeft<=0)
    tabLD.scrollLeft-=tabLD1.offsetWidth
    else{
    tabLD.scrollLeft++;
    }
    }
    var MyMar=setInterval(Marquee,speed);
    tabLD.onmouseover=function() {clearInterval(MyMar)};
    tabLD.onmouseout=function() {MyMar=setInterval(Marquee,speed)};
})