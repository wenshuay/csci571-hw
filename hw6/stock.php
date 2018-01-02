<!DOCTYPE HTML>
<html> 
<head>
  
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
   
</head>
 
   
<body>
<style>
    #clicktext:hover {
        cursor: pointer;
    }
    #dianwoya:hover {
        cursor: pointer;
    }
    hr {
        margin-left: 20px;
        margin-right: 20px;
        margin-bottom: 20px;
    }
    form {
        margin-left: 10px;
    }
    #scb{
        margin-top: 5px;
        margin-left: 214px;
        margin-bottom: 10px;
    }
    a:hover {
        color: blueviolet;
        cursor: pointer;
    }
    a {
        color: blue;
        text-decoration: none;
    }
    td {
        text-align:center;
        border: 1px solid black;
        height: 30px;
        background-color: #FCFCFC;
    } 
    th {
        
        text-align:left;
        border: 1px solid black;
        background-color: #f2f2f2;
        
    }
    table {
        margin: 0px auto; 
        border: 1px solid black;
        border-collapse:collapse;
        width: 900px;
        margin-bottom: 10px
        
            
    }
    #myfo {
        width: 500px;
        height: 200px;
    
        margin: 0px auto;
        border: 1px solid black;
        background-color: #f2f2f2;
        margin-bottom: 25px;
    }
    #myfo h2 {
        margin: 10px;
    }
    #newstable td {
        text-align:left;
        height: 40px;
        background-color: #FCFCFC;
    }
    
</style>

<?php
    function arrayToString ($myarray) {
        $mystr = "[";
        $arrlen = count($myarray);
        for($x = 0; $x < $arrlen; $x++) {
            $mystr = $mystr .  $myarray[$x] . ", ";
           
        }
        
        $len = strlen($mystr);
        $res = substr($mystr, 0, $len - 2);
        $res = $res . "]";
        return $res;
    }
    function arrayToStringst ($myarray) {
        $mystr = "[";
        $arrlen = count($myarray);
        for($x = 0; $x < $arrlen; $x++) {
            $mystr = $mystr . "\"" . $myarray[$x] . "\"". ", ";
           
        }
        
        $len = strlen($mystr);
        $res = substr($mystr, 0, $len - 2);
        $res = $res . "]";
        return $res;
    }
    $butt ="";
    $con = array();
    $url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
    $jsonContent = "";
    $table= "";
    $symbol = "";
    $phpObject = "";
    $html = "";
    $priceChart = "";
    $example = "";
    $img = "";
    if ($_SERVER["REQUEST_METHOD"] == "GET") {
        if (!empty($_GET["symbol"])) {
           
            $symbol = $_GET["symbol"];
            $url .= $_GET["symbol"];
            $url .= "&outputsize=full&apikey=15SSOMJA1UEITCD7";
            $jsonContent = file_get_contents($url);
            $phpArray = json_decode($jsonContent, TRUE);
            if (count($phpArray) == 1) {
                $html = "<table><tr><th style = 'width :350px;'>Error<th/> <td>Error:No recored has been found, please enter a valid symbol</td></tr></table>";
            } else {
            $days = $phpArray["Time Series (Daily)"];
            $count = 0;
            $min = 2147483647;
           // $max = -10;
            $colmax = - 10;
            $xl = array();
            $price = array();
            $volume = array();
            foreach ($days as $day => $dayvalue) {
                $count = $count + 1;
                if ($count > 131) {
                    break;
                }
                if ($count == 1) {
                    $firstday = $day;
                    $firstdayvalue = $dayvalue;
                } else if ($count == 2) {
                    $secondday = $day;
                    $seconddayvalue = $dayvalue;
                    
                }
                $s = explode('-', $day);
                $l = $s[1] . '/'.$s[2];
                array_push($xl, $l);
                array_push($price, floatval($dayvalue['4. close']));
                array_push($volume, floatval($dayvalue["5. volume"]));
                if ($dayvalue['4. close'] < $min) {
                    $min = $dayvalue['4. close'];
                }
               /* if ($dayvalue['4. close'] > $max) {
                    $max = $dayvalue['4. close'];
                }*/
                if ($dayvalue["5. volume"] > $colmax) {
                    $colmax = $dayvalue["5. volume"] ;
                }
            }
            $min = $min - 5;
        
            $colmax = $colmax * 5;
            $todaytime = $phpArray["Meta Data"]["3. Last Refreshed"];
            $todaytime = substr($todaytime,0,10);
               
            $todayele = explode("-", $todaytime);
               
            $pricetime = $todayele[1]. '/'.$todayele[2].'/'.$todayele[0];
            $xl = array_reverse($xl);
           $price = array_reverse($price);
            $volume = array_reverse($volume);
            $changde = ($firstdayvalue["4. close"] - $seconddayvalue["4. close"]) / $seconddayvalue["4. close"];
            $pic = "";
            if (($firstdayvalue["4. close"] - $seconddayvalue["4. close"]) > 0) {
                $pic = 'http://cs-server.usc.edu:45678/hw/hw6/images/Green_Arrow_Up.png';
            } else {
                $pic = 'http://cs-server.usc.edu:45678/hw/hw6/images/Red_Arrow_Down.png';
            }
            $cper = round($changde * 100, 2);
            $html = $html . "<table> <tr> <th>Stock Ticker Symbol</th> <tD>" . $phpArray["Meta Data"]["2. Symbol"] . "</td></tr>";
            $html = $html . "<tr> <th>Close</th> <td>" . $firstdayvalue["4. close"] . "</td></tr>";
            $html = $html . "<tr> <th>Open</th> <td>" . $firstdayvalue["1. open"] . "</td></tr>";
            $html = $html . "<tr> <th>Previous Close</th> <td>" . $seconddayvalue["4. close"] . "</td></tr>";
            $html = $html . "<tr> <th>Change</th> <td>" . ($firstdayvalue["4. close"] - $seconddayvalue["4. close"]) . "<img src = '". $pic. "'height='15' width='15'></td></tr>";
            $html = $html . "<tr> <th>Change Percent</th> <td>" . $cper  . "%<img src = '". $pic. "'height='15' width='15'></td></tr>";
            $html = $html . "<tr> <th>Day's Range</th> <td>" . $firstdayvalue["3. low"]. "-" . $firstdayvalue ["2. high"] . "</td></tr>";
            $html = $html . "<tr> <th>Volume</th> <td>" . number_format($firstdayvalue ["5. volume"]) . "</td></tr>";
            $html = $html . "<tr> <th>Timestamp</th> <td>" . $todaytime . "</td></tr>";
            $html = $html . "<tr> <th>Indicators</th> <td><a  onClick = 'showPrice()'>Price</a> &nbsp&nbsp
            <a  onClick = 'indicatorShow (\"SMA\",\"" . $phpArray["Meta Data"]["2. Symbol"]. "\")'>SMA</a> &nbsp&nbsp
            <a  onClick = 'indicatorShow (\"EMA\",\"" . $phpArray["Meta Data"]["2. Symbol"]. "\")'>EMA</a> &nbsp&nbsp
            <a  onClick = 'indicatorShow1 (\"STOCH\",\"" . $phpArray["Meta Data"]["2. Symbol"]. "\")'>STOCH</a> &nbsp&nbsp
             <a  onClick = 'indicatorShow (\"RSI\",\"" . $phpArray["Meta Data"]["2. Symbol"]. "\")'>RSI</a> &nbsp&nbsp
             <a  onClick = 'indicatorShow (\"ADX\",\"" . $phpArray["Meta Data"]["2. Symbol"]. "\")'>ADX</a> &nbsp&nbsp
            <a  onClick = 'indicatorShow (\"CCI\",\"" . $phpArray["Meta Data"]["2. Symbol"]. "\")'>CCI</a>&nbsp&nbsp
            <a  onClick = 'indicatorShow2 (\"BBANDS\",\"" . $phpArray["Meta Data"]["2. Symbol"]. "\")'>BBANDS</a>&nbsp&nbsp
            <a  onClick = 'indicatorShow3 (\"MACD\",\"" . $phpArray["Meta Data"]["2. Symbol"]. "\")'>MACD</a>
            </td></tr></table>";
            
            
      $priceChart =  "<script type='text/javascript'>
      var mycontain = document.getElementById('container');
      mycontain.style.border= '1px solid black';
      Highcharts.chart('container', {  title: { text: 'Stock Price ('+\"".$pricetime."\"+')'},
           
            subtitle: {  useHTML: true,text: '<a href=\"https://www.alphavantage.co/\"  target=\"_blank\" style=\" color:blue\">Source:Alpha Vantage</a>'},
            xAxis: {
                 categories: " . arrayToStringst($xl) . ",
                 tickInterval: 5
            },
        yAxis: [{ // Primary yAxis
        labels: {
        },
        title: {
            text: 'Stock Price',
        }, 
       
        min: " .$min."
    }, { // Secondary yAxis
        title: {
            text: 'Volume',
        },
        
        max: ".$colmax .",
        opposite: true
    }],
            legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle'
    },
            plotOptions: {

            },

            series: [{
                name: \"". $phpArray["Meta Data"]["2. Symbol"] ."\",
                type: 'area',
               
                color: '#ff6666',
                   marker: {
                        enabled: false
                    },
                data: " . arrayToString($price) . "
            },
            {
                  name:\"". $phpArray["Meta Data"]["2. Symbol"] ."\" + ' Volume',
                  type: 'column',
                  data: " . arrayToString($volume) . ",
                  yAxis: 1,
                  color: '#FFFFFF',
                  pointWidth:1,
            }
            ]
        });
        
         function showPrice() {
            Highcharts.chart('container', {  title: { text: 'Stock Price ('+\"".$pricetime."\"+')'},
            subtitle: {  useHTML: true,text: '<a href=\"https://www.alphavantage.co/\" target=\"_blank\" style=\" color:blue\">Source:Alpha Vantage</a>'},
            xAxis: {
                 categories: " . arrayToStringst($xl) . ",
                 tickInterval: 5
            },
        yAxis: [{ // Primary yAxis
        labels: {
        },
        title: {
            text: 'Stock Price',
        }, 
       
        min: " .$min."
    }, { // Secondary yAxis
        title: {
            text: 'Volume',
        },
        max: ".$colmax .",
       
        opposite: true
    }],
            legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle'
    },
            plotOptions: {

            },

            series: [{
                name: \"". $phpArray["Meta Data"]["2. Symbol"] ."\",
                type: 'area',
               
                color: '#ff6666',
                   marker: {
                        enabled: false
                    },
                data: " . arrayToString($price) . "
            },
            {
                  name:\"". $phpArray["Meta Data"]["2. Symbol"] ."\" + ' Volume',
                  type: 'column',
                  data: " . arrayToString($volume) . ",
                  yAxis: 1,
                  color: '#FFFFFF',
                   pointWidth:1,
            }
            ]
        });
         }
        
        
        </script>";
             
             
              $xmlurl =   "https://seekingalpha.com/api/sa/combined/" . $symbol .".xml";
              $xmlContent = file_get_contents($xmlurl);
              $xml=simplexml_load_string($xmlContent) or die("Error: Cannot create object");
                
             /*
                for ($i = 0; $i <= 4; $i++) {
                    $eachar = array();
                    $eachitem = $xml->channel->item[$i];
                    array_push($eachar, $eachitem->title);
                    array_push($eachar, $eachitem->link);
                    array_push($eachar, $eachitem->pubDate);
                   
                    array_push($con, $eachar);
                }
                
            */
            $artnum = 0;
             foreach ($xml->channel->item as $eachitem) {
                 if (strpos($eachitem->link,'article')) {
                    $eachar = array();
                    array_push($eachar, $eachitem->title);
                    array_push($eachar, $eachitem->link);
                    array_push($eachar, $eachitem->pubDate);
                    array_push($con, $eachar);
                     $artnum++;
                     if ($artnum == 5) {
                         break;
                     }
                     
                 }
             }
              $butt = "<p id ='clicktext' style = 'text-align: center; color : grey' onClick = 'dip()'>click to show stock news</p>
              <img src = 'http://cs-server.usc.edu:45678/hw/hw6/images/Gray_Arrow_Down.png' style = ' height: 20px;
    ;   padding-bottom: 10px;margin: 0px auto;display:block' id='dianwoya' onClick = 'dip()' >
              ";
        
            }
            
        } 
       
    }

    
?>






   

<div id = "myfo">

<form  action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="get">
<h2 style = "text-align: center;">Stock Search </h2><hr>
Enter Stock Ticker Symblo:* <input type="text" name="symbol" id="symbol" value="<?php echo $symbol?>">
<div id="scb"><input type="submit" name="submit" onClick = "ifEmpty(this.form)" >
    <button onClick = 'resetxiaoshi ()' type="button">Clear</button></div>

*-Mandatory fields.
</form>

</div>


<div id="xiaoshi">
<?php   
    echo $html;
?>

<div id="container" style="width:900px; height: 500px; margin: 0 auto"></div>


<?php   
   
    echo $priceChart;

?>



<?php   
   
    echo $butt;

?>

<table id = 'newstable' border = '1'>
    <tr><td id="d0"></td></tr>
    <tr><td id="d1"></td></tr>
    <tr><td id="d2"></td></tr>
    <tr><td id="d3"></td></tr>
    <tr><td id="d4"></td></tr>
   
</table>
  
</div>

<script type="text/javascript">
   
  var ntb = document.getElementById('newstable');
    ntb.style.display = "none";
    var newsjson = <?php echo json_encode($con) ?> ;
    
    if (newsjson.length != 0) {
        
         for (var i = 0; i <= newsjson.length - 1; i++) {
        var idd = "d" + i;
        var myda = newsjson[i][2][0];
        
        myda = myda.substring(0, myda.length -5);
        var nei = "<a href = \'" + newsjson[i][1][0] + "\'target='_blank'>" + newsjson[i][0][0] + "</a> &nbsp&nbsp&nbsp&nbsp Publicated Time: " + myda;
        document.getElementById(idd).innerHTML = nei;
         }
    }
        
  
    function dip() {
        if (ntb.style.display == "table") {
            ntb.style.display = "none";
            document.getElementById('clicktext').innerHTML = "click to show stock news";
            document.getElementById('dianwoya').src = "http://cs-server.usc.edu:45678/hw/hw6/images/Gray_Arrow_Down.png";
        } else {
            ntb.style.display = "table";
            document.getElementById('clicktext').innerHTML = "click to hide stock news";
            document.getElementById('dianwoya').src = " http://cs-server.usc.edu:45678/hw/hw6/images/Gray_Arrow_Up.png";
           
        }
    }     
    
        function ifEmpty(form) {
                
                var symbol = form.symbol.value;
                if (symbol =="") {
                    alert("Please enter a symbol");
                    throw "Please enter a symbol";
                }
            }
            
        function indicatorShow (indicator ,symbol) {
            var url = "https://www.alphavantage.co/query?function=" + indicator + "&symbol=" + symbol +  "&interval=daily&time_period=10&series_type=close&apikey=15SSOMJA1UEITCD7";
           
             if (window.XMLHttpRequest) {
                // code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
             } else {
                // code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp.open("GET", url, true);
            xmlhttp.send();
            xmlhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                        var jsonFile = xmlhttp.responseText;
            var jsFile = JSON.parse(jsonFile);
            var data = new Array();
            var keyname = "Technical Analysis: " + indicator;
            var h = jsFile[keyname];
            var name = jsFile["Meta Data"]["2: Indicator"];
            var n = 0;
            for (var key in h) {
                var each = new Array();
               
            
                var s = key.split("-");
                var d = "";
                d = s[1] + "/" + s[2].substring(0,2);
                each.push(d);
         
           
                each.push(Number(h[key][indicator]));
                data.push(each);
                n++;
                if (n == 131) {
                    break;
                }
             
            }
           
            data.reverse();   
             
            
            
   
        Highcharts.chart('container', {
    chart: {
        type: 'line'
    },
    title: {
        text: name
    },
    subtitle: {
        useHTML: true,
        text: '<a href="https://www.alphavantage.co/" target=\"_blank\" style=\" color:blue\">' +
            'Source: Alpha Vantage</a>'
    },
    xAxis: {
   
        type: 'category',
        tickInterval: 5
        
    },
    yAxis: {
        title: {
            text: indicator,
        }
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle'
    },
    plotOptions: {
        series: {
            marker: {
                symbol: 'square',
                enabled: true,
                 radius: 2
            },
            lineWidth:1,
        },
        line: {

            color: '#FF0000'
       
        }
    },
    series: [{
        name:symbol,
        data: data
    }, ]
});
                 }
             };
            
      
        }     
            
        function indicatorShow1 (indicator ,symbol) {
            var url = "https://www.alphavantage.co/query?function=" + indicator + "&symbol=" + symbol +  "&interval=daily&time_period=10&series_type=close&slowkmatype=1&slowdmatype=1&apikey=15SSOMJA1UEITCD7";
            
             if (window.XMLHttpRequest) {
                // code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
             } else {
                // code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp.open("GET", url, true);
            xmlhttp.send();
            xmlhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                         var jsonFile = xmlhttp.responseText;
            var jsFile = JSON.parse(jsonFile);
            var keyname = "Technical Analysis: STOCH";
            var h = jsFile[keyname];
            var xlabel = [];
            var name = jsFile["Meta Data"]["2: Indicator"];
            var sk =[];
            var sd = [];
            var n = 0;
            for (var key in h) {
                
                var s = key.split("-");
                var d = "";
                d = s[1] + "/" + s[2].substring(0,2);
                xlabel.push(d);
         
                sk.push(Number(h[key]["SlowK"]));
                sd.push(Number(h[key]["SlowD"]));
            
                n++;
                if (n == 131) {
                    break;
                }
             
            }
           
             xlabel.reverse();   
             sk.reverse(); 
             sd.reverse(); 
             
             Highcharts.chart('container', {
               
                title: {
                    text: name
                },
                subtitle: {
                     useHTML: true,
                    text: '<a href="https://www.alphavantage.co/" target=\"_blank\" style=\" color:blue\">' +
                        'Source: Alpha Vantage</a>'
                },
                xAxis: {

                    categories: xlabel,
                    tickInterval: 5

                },
                yAxis: {
                    title: {
                        text: indicator,
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle'
                },
                plotOptions: {
                    series: {
            marker: {
                symbol: 'square',
                enabled: true,
                 radius: 2
            },
            lineWidth:1,
        },
                },
                series: [{
                    type: 'line',
                    name:symbol + " SlowK",
                    data:sk
                }, 
                {
                    type: 'line',
                    name:symbol + " SlowD",
                    data:sd
                }   
                   ]
            });
                }
            };
            
           
            
         }
            
        function indicatorShow2 (indicator ,symbol) {
            var url = "https://www.alphavantage.co/query?function=" + indicator + "&symbol=" + symbol +  "&interval=daily&time_period=5&series_type=close&nbdevup=3&nbdevdn=3&apikey=15SSOMJA1UEITCD7";
        
            
             if (window.XMLHttpRequest) {
                // code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
             } else {
                // code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp.open("GET", url, true);
            xmlhttp.send();
            
            
            xmlhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                     var jsonFile = xmlhttp.responseText;
            var jsFile = JSON.parse(jsonFile);
            var keyname = "Technical Analysis: BBANDS";
            var h = jsFile[keyname];
            var xlabel = [];
            var name = jsFile["Meta Data"]["2: Indicator"];
            var u =[];
            var l = [];
            var m = [];
            var n = 0;
            for (var key in h) {
                
                var s = key.split("-");
                var d = "";
                d = s[1] + "/" + s[2].substring(0,2);
                xlabel.push(d);
         
                u.push(Number(h[key]["Real Upper Band"]));
                l.push(Number(h[key]["Real Lower Band"]));
                m.push(Number(h[key]["Real Middle Band"]));
                n++;
                if (n == 131) {
                    break;
                }
             
            }
           
             xlabel.reverse();   
             u.reverse(); 
             l.reverse(); 
              m.reverse();
             Highcharts.chart('container', {
               
                title: {
                    text: name
                },
                subtitle: {
                     useHTML: true,
                    text: '<a href="https://www.alphavantage.co/" target=\"_blank\" style=\" color:blue\">' +
                        'Source: Alpha Vantage</a>'
                },
                xAxis: {

                    categories: xlabel,
                    tickInterval: 5

                },
                yAxis: {
                    title: {
                        text: indicator,
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle'
                },
                plotOptions: {
                    series: {
            marker: {
                symbol: 'square',
                enabled: true,
                 radius: 2
            },
            lineWidth:1,
        },
                },
                series: [{
                    type: 'line',
                    name:symbol + " Real Upper Band",
                    data:u
                }, 
                {
                    type: 'line',
                    name:symbol + " Real Lower Band",
                    data:l
                },
                {
                    type: 'line',
                    name:symbol + " Real Middle Band",
                    data:m
                }   
                   ]
            });
                    
                }
            };
            
           
            
            }
    
         function indicatorShow3 (indicator ,symbol) {
            var url = "https://www.alphavantage.co/query?function=" + indicator + "&symbol=" + symbol +  "&interval=daily&time_period=10&series_type=close&apikey=15SSOMJA1UEITCD7";
        
            
             if (window.XMLHttpRequest) {
                // code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
             } else {
                // code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp.open("GET", url, true);
            xmlhttp.send();
             
            xmlhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var jsonFile = xmlhttp.responseText;
            var jsFile = JSON.parse(jsonFile);
            var keyname = "Technical Analysis: MACD";
            var h = jsFile[keyname];
            var xlabel = [];
            var name = jsFile["Meta Data"]["2: Indicator"];
            var hi =[];
            var m = [];
            var si = [];
            var n = 0;
            for (var key in h) {
                
                var s = key.split("-");
                var d = "";
                d = s[1] + "/" + s[2].substring(0,2);
                xlabel.push(d);
         
                hi.push(Number(h[key]["MACD_Hist"]));
                m.push(Number(h[key]["MACD"]));
                si.push(Number(h[key]["MACD_Signal"]));
                n++;
                if (n == 131) {
                    break;
                }
             
            }
           
             xlabel.reverse();   
             hi.reverse(); 
             m.reverse(); 
              si.reverse();
             Highcharts.chart('container', {
               
                title: {
                    text: name
                },
                subtitle: {
                     useHTML: true,
                    text: '<a href="https://www.alphavantage.co/" target=\"_blank\" style=\" color:blue\">' +
                        'Source: Alpha Vantage</a>'
                },
                xAxis: {

                    categories: xlabel,
                    tickInterval: 5

                },
                yAxis: {
                    title: {
                        text: indicator,
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle'
                },
                plotOptions: {
                    series: {
            marker: {
                symbol: 'square',
                enabled: true,
                 radius: 2
            },
            lineWidth:1,
        },
                },
                series: [{
                    type: 'line',
                    name:symbol + " MACD_Hist",
                    data:hi
                }, 
                {
                    type: 'line',
                    name:symbol + " MACD",
                    data:m
                },
                {
                    type: 'line',
                    name:symbol + " MACD_Signal",
                    data:si
                }   
                   ]
            });
                }
            };
            
            
            }
    
         function resetxiaoshi () {
             document.getElementById("xiaoshi").innerHTML = "";
             document.getElementById("symbol").value = "";
            
         }
        
</script>

</body>
</html>