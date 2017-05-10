window.onload = function(){
    //初始化图片区
    var images = document.getElementById("images").getElementsByTagName("img");
    var ul = document.getElementById("ul");
    var description = document.getElementById("description");
    var order = document.getElementById("order");
    var image_list = document.getElementById("image_list");
    if(images.length > 0) {
        ul.style.width = images.length * 105 + 'px';
        var i = 0;
        for(i; i < images.length; i++){
            li = document.createElement("li");
            img = document.createElement("img");
            img = images[i].cloneNode(false);
            img.className = "NSPicChange_" + i;
            li.appendChild(img);
            ul.appendChild(li);
            if(i == 0){
                images[i].style.display = 'block';
                li.className = "select";
            }
        }
        order.innerHTML =  '<span><em>1</em>/' + images.length + '</span>';
        description.innerHTML = images[0].alt;
        description.style.display = 'block';
        order.style.display = 'block';
        image_list.style.display = 'block';
    }
    $(document).bind('click', function(event){
    //document.addEventListener('click',function(event){
        var element = event.srcElement ? event.srcElement : event.target ;
        if(element.className == "left" || element.parentNode.className == "left") {
            scrollRight();
        }
        if(element.className == "right" || element.parentNode.className == "right"){
            scrollLeft();
        }
        //切换图片
        if(element.tagName.toLowerCase() == "img" && element.parentNode.parentNode.id == "ul"){
            picChange(element);
        }

        //前一个
        if(element.className == "pre" || element.parentNode.className == "pre"){
            pre();
        }
        //后一个
        if(element.className == "next" || element.parentNode.className == "next"){
            next();
        }
    })
}

//向左滚动
function scrollLeft(){
    var ul = document.getElementById("ul")
    var li = ul.getElementsByTagName("li");
    var marginleft = ul.style.marginLeft;
    if(!marginleft){
        marginleft = '0px';
    }
    marginleft = parseInt(marginleft.replace("px",""));
    if(parseInt(ul.style.width.replace("px","")) > 528 && (marginleft + li.length * 105) > 528){
        marginleft -= 105;
        ul.style.marginLeft = marginleft + "px";
    }
}

//向右滚动
function scrollRight(){
    var ul = document.getElementById("ul")
    var li = ul.getElementsByTagName("li");
    var marginleft = ul.style.marginLeft;
    if(!marginleft){
        marginleft = '0px';
    }
    marginleft = parseInt(marginleft.replace("px",""));
    if(parseInt(ul.style.width.replace("px","")) > 528 && marginleft < 0){
        marginleft += 105;
        ul.style.marginLeft = marginleft + "px";
    }
}

//切换图片
function picChange(element){
    var images = document.getElementById("images").getElementsByTagName("img");
    var ul = document.getElementById("ul");
    var description = document.getElementById("description");
    var orderDiv = document.getElementById("order");
    var order = element.className.replace("NSPicChange_","");
    for(i = 0; i < images.length; i++){
        if(i == order){
            images[i].style.display = "block";
        }else{
            images[i].style.display = "none";
        }
    }
    var lis = ul.getElementsByTagName("li");
    for(i = 0; i < lis.length; i++){
        if(i == order){
            lis[i].className = "select";
        }else{
            lis[i].className = "";
        }
    }
    description.innerHTML = element.alt;
    orderDiv.innerHTML = '<span><em>' + (parseInt(order) + 1) + '</em>/' + images.length + '</span>';
}

function pre(){
    var images = document.getElementById("images").getElementsByTagName("img");
    var ul = document.getElementById("ul");
    var li = ul.getElementsByTagName("li");
    var description = document.getElementById("description");
    var orderDiv = document.getElementById("order");
    var i = 0;
    for(i; i < images.length; i++){
        if(images[i].style.display == "block"){
            if(i == 0){
                break;
            }else{
                images[i].style.display = "none";
                images[i-1].style.display = "block";
                li[i].className = "";
                li[i-1].className = "select";
                description.innerHTML = images[i-1].alt;
                orderDiv.innerHTML = '<span><em>' + i + '</em>/' + images.length + '</span>';
                break;
            }
        }
    }
    if(i > 0){
        var marginleft = ul.style.marginLeft;
        if(!marginleft){
            marginleft = '0px';
        }
        marginleft = parseInt(marginleft.replace("px",""));
        if(parseInt(ul.style.width.replace("px","")) > 528){
            marginleft = - (105 * ( i - 1 ));
            if(parseInt(ul.style.width.replace("px","")) + marginleft < 528){
                marginleft = 528 - parseInt(ul.style.width.replace("px",""));
            }
            ul.style.marginLeft = marginleft + "px";
        }
    }
}

function next(){
    var images = document.getElementById("images").getElementsByTagName("img");
    var ul = document.getElementById("ul");
    var li = ul.getElementsByTagName("li");
    var description = document.getElementById("description");
    var orderDiv = document.getElementById("order");
    var i = 0;
    for(i; i < images.length; i++){
        if(images[i].style.display == "block"){
            if(i == images.length - 1){
                break;
            }
            images[i].style.display = "none";
            images[i+1].style.display = "block";
            li[i].className = "";
            li[i+1].className = "select";
            description.innerHTML = images[i+1].alt;
            orderDiv.innerHTML = '<span><em>' + (i + 2) + '</em>/' + images.length + '</span>';
            break;
        }
    }
    if(i < images.length - 1){
        var marginleft = ul.style.marginLeft;
        if(!marginleft){
            marginleft = '0px';
        }
        marginleft = parseInt(marginleft.replace("px",""));
        if(parseInt(ul.style.width.replace("px","")) > 528){
            marginleft = - (105 * (i + 1));
            if(parseInt(ul.style.width.replace("px","")) + marginleft < 528){
                marginleft = 528 - parseInt(ul.style.width.replace("px",""));
            }
            ul.style.marginLeft = marginleft + "px";
        }
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