
var http = require("http");
var mysql      = require('mysql');
var value;
var length;
var counts;
var newValue = [];
var ptr = 0;
var dateFormat = require('dateformat');

var port = 3000;
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'rohit',
  database : 'mail_info'
});

connection.connect();

function checkupdate() {
connection.query('SELECT * from mail_info1', function(err, rows, fields) {
  if (err) throw err;

 
  value = rows;
  length = rows.length;
});
}

function callQuery(fn){ 

    connection.query('SELECT * from mail_info1 order by sender', function(err, rows, fields) {
  if (err) throw err;

  value = rows;
length = rows.length;

var newValue = [];

var ptr = 0;


newValue.push([]);
newValue[ptr].push(value[0].sender);
newValue[ptr].push(value[0].subject);
newValue[ptr].push(value[0].date);
newValue[ptr].push(value[0].checked);
for (i=1; i<length; i++){

    if(value[i].sender == value[i-1].sender) {

        newValue[ptr].push(value[i].subject);

newValue[ptr].push(value[i].date);

newValue[ptr].push(value[i].checked);

    } else {

        ptr++;
		newValue.push([]);
		newValue[ptr].push(value[i].sender);
		
 newValue[ptr].push(value[i].subject);

newValue[ptr].push(value[i].date);

newValue[ptr].push(value[i].checked);

}

    }




        fn(value,length,newValue);

});
}
function updatedatabase(sub) {
console.log(sub);
connection.query("update mail_info1 set checked = checked + 1 where subject like '" + sub + "'", function(err, rows, fields) {
  if (err) throw err;
});
}







setInterval(checkupdate,30000);

var server = http.createServer(function(req, res) {


  var url = req.url;

  console.log(url)
  
 function sendHtml(value,length,newValue){

var html = '<head><script>';
html +='function myFunction(){setInterval(function(){location.reload();},30000);}'
html +='</script>';
html += '<style type="text/css">';
html += '.tg  {border-collapse:collapse;border-spacing:0;border-color:#bbb;}';
html += '.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#bbb;color:# 594F4F;background-color:#E0FFEB;}';
html += '.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#bbb;color:#493F3F;background-color:#9DE0AD;}';
html += '.tg .tg-icde{font-weight:bold;color:#036400;text-align:center}';
html += '.tg .tg-5bzz{font-weight:bold;font-size:14px;font-family:Arial, Helvetica, sans-serif !important;;color:#036400;text-align:center}';
html += '.tg .tg-lrzf{font-size:14px;font-family:Arial, Helvetica, sans-serif !important;;text-align:center}';
html += '.tg .tg-kr94{font-size:12px;text-align:center}';
html += '.tg .tg-mwnp{font-weight:bold;font-size:16px;background-color:#4bd946;color:#ffffff;text-align:center}';
html += '.tg .tg-pz9v{font-size:12px;font-family:Arial, Helvetica, sans-serif !important;;text-align:center}';
html += '.tg .tg-6iqf{font-size:16px;font-family:Arial, Helvetica, sans-serif !important;;text-align:center}';

  html +='</style></head>';
  html +='<body onload="myFunction();"><table class="tg" style="width:100%">';
  html += '<tr> <th class="tg-mwnp" colspan="4">WHAT ARE WE READING</th></tr><tr><td class="tg-5bzz">SENDER</td><td class="tg-icde">SUBJECT</td><td class="tg-5bzz">DATE</td>';
   html += '<td class="tg-5bzz">CLICKED</td></tr>';
  
   
    
    
    
  
    
 for (var i=0;i<newValue.length;i++)
{
var currentSender = value[i].sender ;





html = html + '<tr><td  style="text-align: center" rowspan="'+Math.floor(newValue[i].length/3)+' ">';;

html = html + newValue[i][0].substring(1, newValue[i][0].indexOf("\"",2)); 

html = html + '</td>';

for(j=1;j<newValue[i].length; j=j+1) {

if(j%3==1)
{
html += '<td >';
html += '<a href="http://10.140.6.226:3000/'+ newValue[i][j]+'" target="_blank" style="text-decoration: none">' + newValue[i][j] +'</a>';
}
if(j%3==2)
{
html += '<td style="text-align: center">';
html +=  dateFormat(newValue[i][j],"dS mmmm, yyyy") ;
}
if(j%3==0)
{
html += '<td style="text-align: center">';
html += newValue[i][j];
html += '</td></tr><tr>';
}

}

html+= "</tr>";












 }
  html = html + "</table></body>" ;
  res.writeHead('Cache-Control', 'no-cache');
  res.end(html);
}


if (url=='/') {

    callQuery(sendHtml); 
	
} 

else {

    var newSub = url.slice(1,url.length);

 //   console.log(newSub);
	updatedatabase(newSub);
	res.writeHead(301,
  {Location: newSub,'Cache-Control' : 'no-cache'});
res.end();

  
}









});

server.listen(3000,'10.140.6.226');
