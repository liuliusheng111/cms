/*获取当前日期*/
function getCurrentDateTime() { 
    var d = new Date(); 
    var year = d.getFullYear(); 
    var month = d.getMonth() + 1; 
    var date = d.getDate(); 
    var week = d.getDay(); 
    var curDateTime = year; 
    if (month > 9) 
    curDateTime = curDateTime + "年" + month; 
    else
    curDateTime = curDateTime + "年0" + month; 
    if (date > 9) 
    curDateTime = curDateTime + "月" + date + "日"; 
    else
    curDateTime = curDateTime + "月0" + date + "日"; 
    var weekday = ""; 
    if (week == 0) 
    weekday = "星期日"; 
    else if (week == 1) 
    weekday = "星期一"; 
    else if (week == 2) 
    weekday = "星期二"; 
    else if (week == 3) 
    weekday = "星期三"; 
    else if (week == 4) 
    weekday = "星期四"; 
    else if (week == 5) 
    weekday = "星期五"; 
    else if (week == 6) 
    weekday = "星期六"; 
    curDateTime = curDateTime + " " + weekday; 
    return curDateTime; 
}
$(function(){
    var date = getCurrentDateTime();
    $("#currentDate").text("今天是: " + date); 
})