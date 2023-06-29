<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="t" uri="/struts-tags" %>

<html>

<head>


    <link href="/wms/warehouse/statusScroller/status.css" rel="stylesheet" type="text/css">
    <link href="/css/wms/jquery-letterfx.min.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="/css/wms/layout.css">

    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>
    <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/smoothness/jquery-ui.css" />
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
    <!-- DataTables -->
    <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="/js/jquery.vticker.min.js"></script>
    <script src="/js/jquery-letterfx.min.js"></script>
    <script src="/js/mf/core/Core.js"></script>
    <script src="/js/jquery/jquery.splitflap.js"></script>
    <script src="/js/application/main.js"></script>


  <!--  <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"> -->


    <script type="text/javascript">
        var i = 2;
        var coreInt = 1;
        var lastText = 0;
        function theTest(){

        }

        function hideFlips(){
            $('.flip1').hide();
            $('.flip2').hide();
            $('.flip3').hide();
            $('.core').removeClass("splitflap");
            $('.theCore').show();

        }



        function flipCore(){
            switch(coreInt){
                case 1:
                    $('.core').text("To enable greatness in our clients,  our community, our company, and ourselves.")
                    break;
                case 2:
                    $('.core').text("Clarity")
                    break;
                case 3:
                    $('.core').text("Forward Thinking")
                    break;
                case 4:
                    $('.core').text("Nimble")
                    break;
                case 5:
                    $('.core').text("Positive Impact")
                    break;
                case 6:
                    $('.core').text("Innovation")
                    break;
                case 7:
                    $('.core').text("Integrity")
                    break;
            }
            if(coreInt==7){
                coreInt=1;
            }else{
                coreInt = coreInt+1;
            }

           coreLayout();

          var theEffect = Math.floor((Math.random() * 10) + 1);
            if(theEffect == lastText){

                theEffect = Math.floor((Math.random() * 10) + 1);
            }
            lastText = theEffect;
              hideFlips();
            if(coreInt==2 & (theEffect ==8 || theEffect ==9 || theEffect ==10)){
                theEffect=4;
            }
            switch(theEffect){
                case 1:
                    $('.core').letterfx({"fx":"fall","timing":200})
                    break;
                case 2:
                    $('.core').letterfx({"fx":"swirl"})
                    break ;
                case 3:
                    $('.core').letterfx({"fx":"wave","duration":"5000ms"})
                    break;
                case 4:
                    $('.core').letterfx({"fx":"smear","letter_end":"rewind",fx_duration:'3s'})
                    break;
                case 5:
                    $('.core').letterfx({ fx:'spin fade', pattern:/([aeiouy])/ig , fx_duration:'3s' })
                    break;
                case 6:
                    $('.core').letterfx({"fx":"fly-right fly-bottom spin",fx_duration:'4s'})
                    break;
                case 7:
                    $('.core').letterfx({"fx":"fly-right fly-bottom spin","backwards":true})
                    break;
                case 8:
                        $('.core').addClass("splitflap");
                          $('.core').splitFlap();
                    break;
                case 9:
                    $('.core').addClass("splitflap");
                    $('.core').splitFlap();

                    break;
                case 10:
                    $('.core').addClass("splitflap");
                    $('.core').splitFlap();
                    break;

            }


            setTimeout(function(){flipData()},10000);



        }

         function coreLayout(){
             $('.core').attr('class','core');

             var position = Math.floor((Math.random() * 10) + 1);
             switch(position){
                 case 1:
                 case 2:

                         $('.core').addClass('bottom');
                     break;
                 case 3:
                 case 4:

                         $('.core').addClass('quarters');
                       break;
                 case 5:
                 case 6:

                     $('.core').addClass('middle');
                     break;
                 case 7:
                 case 8:

                     $('.core').addClass('quarter');
                     break;


             }
             var align = Math.floor((Math.random() * 10) + 1);
             switch (align){
                 case 1:
                 case 2:
                 case 3:
                 case 4:
                     $('.core').addClass('center');
                     break;
                 case 5:
                 case 6:
                 case 7:
                       $('.core').addClass('right');
                     break;

             }
         }
         function doReload() {
           location.reload(true);
         }
        function flipData(){
            var theEffect = Math.floor((Math.random() * 10) + 1);
            var effect;
            $('.theCore').hide();
            switch (theEffect){
                case 1:
                    effect="blind";
                    break;
                case 2:
                     effect="clip";

                    break;
                case 3:
                    effect="drop";
                    break;
                case 4:
                    effect="explode";
                    break;
                case 5:
                    effect="fold";
                    break;
                case 6:
                    effect="highlight";
                    break;
                case 7:
                    effect="puff";
                    break;
                case 8:
                    effect="scale";
                    break;
                case 9:
                    effect="size";
                    break;
                case 10:
                    effect="slide";
                    break;
            }
            $('.flip' + i).show(effect,1000);
            if (i==3){
                i=1;
            } else{
                i= i+1;
            }

            setTimeout(function(){flipCore()},20000);
        }
        function doMusic(){
            var theEffect = Math.floor((Math.random() * 10) + 1);
            switch (theEffect) {
                case 1:
                    $(".themusic").html("<audio autoplay> <source src='/music/BlueRhoda.mp3'></audio>");
                    break;
                case 2:
                    $(".themusic").html("<audio autoplay> <source src='/music/ByeBye.mp3'></audio>");
                    break;
                case 3:
                    $(".themusic").html("<audio autoplay> <source src='/music/deaconBlues.mp3'></audio>");
                    break;
                case 4:
                    $(".themusic").html("<audio autoplay> <source src='/music/dontStop.mp3'></audio>");
                    break;
                case 5:
                    $(".themusic").html("<audio autoplay> <source src='/music/holdTheLine.mp3'></audio>");
                    break;
                case 6:
                    $(".themusic").html("<audio autoplay> <source src='/music/Lowdown.mp3'></audio>");
                    break;
                case 7:
                    $(".themusic").html("<audio autoplay> <source src='/music/million.mp3'></audio>");
                    break;
                case 8:
                    $(".themusic").html("<audio autoplay> <source src='/music/tom.mp3'></audio>");
                    break;
                case 9:
                    $(".themusic").html("<audio autoplay> <source src='/music/deaconBlues.mp3'></audio>");
                    break;
                case 10:

                     $(".themusic").html("<audio autoplay> <source src='/music/dontStop.mp3'></audio>");
                    break;

            }

        }
        $(document).ready( function(){
            $('#clientTable').dataTable({
                "order":[[2,"desc"]]
            });
            $('#futureClientTable').dataTable({
                "order":[[2,"desc"]]
            });
            $('#methodTable').DataTable({
                "order":[[1,"asc"]]
            });
           $('#messages').vTicker('init',{speed:1100,pause:10000

           });

       //hide divs then start flip
         $('.flip2').hide();
            $('.flip3').hide();
            $('.theCore').hide();

               setTimeout(function(){flipCore()},20000);
          //play music
            doMusic();
            setTimeout(function(){doReload()},300000);
        })   ;



    </script>
</head>


<body>
      <div id="header">
      <img src="/images/wms/owdlogo2.jpg"/>
          <div class="right">
              Refreshed @ <s:property value="data.loadedDate"/>
          </div>
      </div>
 <div id="contentWrapper">
<div id="clientSummary" class="flip1">
    <div class="dataHeader">   <h1> Summary By Client:&nbsp;&nbsp; <s:property value="data.SumClientCount"/> orders left to ship.</h1> </div>
    <table id="clientTable" class="stripe info">
        <thead>
           <th>Client</th>
        <th>Sub-Client</th>
        <th>Left To Ship</th>
        <th>Left to Pick</th>
        <th>Unshipped Packed</th>
        </thead>


   <s:iterator value="data.orderSummaryByClient">
        <tr>
            <td>
                <s:property value="itemGroup1"/>
            </td>
            <td>
                <s:property value="itemGroup2"/>

            </td>
            <td class="center leftToShip">
                <s:property value="ordersLeftToShip"/>

            </td>
            <td class="center">
                <s:property value="unpickedOrders"/>
            </td>
            <td class="center">
                <s:property value="packedUnshippedOrders"/>
            </td>
        </tr>


   </s:iterator>
    </table>

</div>

<div id="methodSummary" class="flip2">
 <div class="dataHeader">   <h1> Summary By SLA/Method:&nbsp;&nbsp;  <s:property value="data.SumMethodCount"/> orders left to ship.</h1>  </div>
    <table id="methodTable" class="stripe info">
        <thead>
        <th>Method</th>
        <th>SLA</th>
        <th>Left To Ship</th>
        <th>Left to Pick</th>
        <th>Unshipped Packed</th>
        </thead>


        <s:iterator value="data.orderSummaryByMethod">
            <tr>
                <td>
                    <s:property value="itemGroup1"/>
                </td>
                <td>
                    <s:property value="itemGroup2"/>

                </td>
                <td class="center  leftToShip">
                    <s:property value="ordersLeftToShip"/>

                </td>
                <td class="center">
                    <s:property value="unpickedOrders"/>
                </td>
                <td class="center">
                    <s:property value="packedUnshippedOrders"/>
                </td>
            </tr>


        </s:iterator>
    </table>


</div>

<div id="futureClientSummary" class="flip3">
    <div class="dataHeader">   <h1> Future Summary By Client:&nbsp;&nbsp; <s:property value="data.futureSumClientCount"/> orders in system.</h1> </div>
    <table id="futureClientTable" class="stripe info">
        <thead>
        <th>Client</th>
        <th>Sub-Client</th>
        <th>Left To Ship</th>
        <th>Left to Pick</th>
        <th>Unshipped Packed</th>
        </thead>


        <s:iterator value="data.futureOrderSummaryByClient">
            <tr>
                <td>
                    <s:property value="itemGroup1"/>
                </td>
                <td>
                    <s:property value="itemGroup2"/>

                </td>
                <td class="center leftToShip">
                    <s:property value="ordersLeftToShip"/>

                </td>
                <td class="center">
                    <s:property value="unpickedOrders"/>
                </td>
                <td class="center">
                    <s:property value="packedUnshippedOrders"/>
                </td>
            </tr>


        </s:iterator>
    </table>

</div>
     <div id="coreValues" class="theCore">
          <p class="core"></p>

     </div>
 </div>

<div id="footer">
<div id="messages">

    <ul>
        <s:iterator value="data.announceList">
            <li><s:property/></li>
        </s:iterator>
    </ul>

</div>
</div>
<div class="themusic">

</div>
</body>
</html>