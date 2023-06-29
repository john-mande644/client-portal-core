<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Admin Page</title>

<style type="text/css">
    @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dijit/themes/soria/soria.css";
    @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/resources/dojo.css";
    body, html {
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0
    }

    #header {
        margin: 0;
    }

    .testClass {
        background-color: #00aaee;
    }
    #loader {
                padding:0;
                margin:0;
                position:absolute;
                top:0; left:0;
                width:100%; height:100%;
                background:#ededed;
                z-index:999;
                vertical-align:middle;
            }
            #loaderInner {
                padding:15px;
                position:relative;
                left:0;
                top:0;
                width:225px;
                background:#3c3;
                color:#fff;
            }
   
</style>
<script type="text/javascript" src="http://internal.owd.com/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>


<!-- load the dojo toolkit base -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/dojo.xd.js"
        djConfig="parseOnLoad:true, isDebug:true"></script>

<script type="text/javascript">
    dojo.require("dijit.layout.AccordionContainer");
  //  dojo.require("dijit.layout.AccordionPane");

    dojo.require("dijit.layout.BorderContainer");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.Dialog");
    dojo.require("dijit.TitlePane");
    dojo.require("dojo.parser");
    dojo.require("dijit.form.TextBox");
    dojo.require("dojox.layout.ContentPane");
    dojo.require("dijit._Calendar");
      dojo.require("dijit.Tooltip");


    dojo.addOnLoad(function() {


        dojo.byId('replaceable').innerHTML = ' <iframe id="theframe" src="<s:property value="ccClient.frameUrl"/>" width="98%" height="98%"></iframe>';
       dojo.byId('theframeside').setAttribute('src', dijit.byId('ccClient.sideLinks').value);
        dojo.byId('cal').setAttribute("dojoType", "dijit._Calendar");
        dojo.parser.parse(dojo.byId('cal').parentNode)

        dojo.connect(
                dojo.byId('upd'),
                "onclick",
                function(evt) {
                    console.log('click');
                    dijit.byId('upd')._setLabelAttr('loading');
                    dojo.byId('theframe').setAttribute('src', dijit.byId('ccClient.frameUrl').value);
                    dijit.byId('upd')._setLabelAttr('Update');
                    dojo.byId('update_ccClient_frameUrl').value = dijit.byId('ccClient.frameUrl').value;
                    return (evt);

                });
         dojo.connect(
                dojo.byId('upda'),
                "onclick",
                function(evt) {
                    console.log('click');

                    dojo.byId('theframeside').setAttribute('src', dijit.byId('ccClient.sideLinks').value);
                    

                    return (evt);

                });
        dojo.connect(
                dojo.byId('sub'),
                "onclick",
                function(evt) {
                    dojo.byId('update_ccClient_announceScript').value = tinyMCE.editors.announceScript.getContent();
                    dojo.byId('update_ccClient_sideInfo').value = tinyMCE.editors.sideInfo.getContent();
                    dojo.byId('update_ccClient_sideLinks').value = dojo.byId('ccClient.sideLinks').value;
                    dojo.byId('update_ccClient_frameUrl').value = dojo.byId('ccClient.frameUrl').value;

                    dojo.byId('update').submit();
                }
                );
        dojo.connect(
                dojo.byId('del'),
                "onclick",
                function(evt) {
                    var ok = confirm("Are you sure you want to delete??");
                    if(ok === true){
                        parent.location = '<s:url action="delete"/>?id=<s:property value="ccClient.id"/>';
                    } else{
                        console.log("false");
                    }

                }
                );
         dojo.connect(
                dojo.byId('subdep'),
                "onclick",
                function(evt) {
                    dojo.byId('update_ccClient_announceScript').value = tinyMCE.editors.announceScript.getContent();
                    dojo.byId('update_ccClient_sideInfo').value = tinyMCE.editors.sideInfo.getContent();
                    dojo.byId('update_deploy').value = '1';
                     dojo.byId('update_ccClient_sideLinks').value= dojo.byId('ccClient.sideLinks').value;
                    dojo.byId('update_ccClient_frameUrl').value= dojo.byId('ccClient.frameUrl').value;
                    dojo.byId('update').submit();
                }
                );
        dojo.byId('loaderInner').innerHTML += " done.";
        
        setTimeout(function hideLoader(){
                        var loader = dojo.byId('loader');
                        dojo.fadeOut({ node: loader, duration:500,
                            onEnd: function(){
                                loader.style.display = "none";
                            }
                        }).play();
                    }, 250);
        
    });</script>
<script type="text/javascript">
    tinyMCE.init({
        editor_selector: "topedit",
        theme : "advanced",
        height:"98%",
        theme_advanced_toolbar_location : "top",
        theme_advanced_toolbar_align : "left",
        theme_advanced_statusbar_location : "bottom",

        mode : "textareas",
        plugins : "spellchecker,style,table,advhr,advimage,advlink,iespell,inlinepopups,preview,media,searchreplace,contextmenu,paste,template",
        theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,formatselect,fontselect,fontsizeselect",
        theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,|,link,unlink,anchor,image,cleanup,help,code,|,forecolor,backcolor",
        theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|iespell,advhr",
        theme_advanced_buttons4 : "spellchecker,|,undo,redo,|,agent,|,template",
        template_templates : [
            {
                title : "Greeting Script",
                src : "http://internal.owd.com/tinymce/templates/script.html",
                description: "Insert default greeting script"
            }


        ],

        setup : function(ed) {
            // Add a custom button
            ed.addButton('agent', {
                title : 'Agent Name',
                label : 'Agent Name',
                onclick : function() {
                    // Add you own code to execute something on click
                    ed.focus();
                    ed.selection.setContent('&lt;AGENT_NAME&gt;');
                    
                }

            });
           

        }

    });
    tinyMCE.init({
        editor_selector: "leftedit",
        theme : "advanced",
        theme_advanced_resizing : true,
        theme_advanced_toolbar_location : "top",
        theme_advanced_toolbar_align : "left",
        theme_advanced_statusbar_location : "bottom",
        mode : "textareas",
        plugins : "spellchecker,style,table,advhr,advimage,advlink,iespell,inlinepopups,preview,media,searchreplace,contextmenu,paste,template",
        theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,formatselect,fontselect,fontsizeselect",
        theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,|,link,unlink,anchor,image,cleanup,help,code,|,forecolor,backcolor",
        theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|iespell,advhr",
        theme_advanced_buttons4 : "spellchecker,|,undo,redo,|,agent,|,template",
        template_templates : [
            {
                title : "Greeting Script",
                src : "http://internal.owd.com/tinymce/templates/script.html",
                description: "Insert default greeting script"
            }


        ],

        setup : function(ed) {
            // Add a custom button
            ed.addButton('agent', {
                title : 'Agent Name',
                label : 'Agent Name',
                onclick : function() {
                    // Add you own code to execute something on click
                    ed.focus();
                    ed.selection.setContent('&lt;AGENT_NAME&gt;');
                }

            });
            

           

        }



    });
</script>
</head>
<body class="soria">

<div id="loader"><div id="loaderInner">Loading Screen Pop Admin for <s:property value="ccClient.mlogCampaignName"/> ... </div></div>

<div id="main" dojoType="dijit.layout.BorderContainer" liveSplitters="false" design="sidebar"
     style="width:100%; height:200%">


<div dojoType="dijit.layout.ContentPane" region="top">


<div dojoType="dijit.form.DropDownButton">
    <span>Place Order</span>


    <div dojoType="dijit.TooltipDialog" id="tooltipOrder" title="Edit OrderStatus Link">
        <table>
            <tr>

                <td><label for="order">Url:</label></td>
                <td><input dojoType="dijit.form.TextBox" style="width: 700px;" type="text"
                           id="order" name="ccClient.urlEntry"
                           value="<s:property value="%{ccClient.urlEntry}"/>"/></td>
            </tr>
            <tr>
                <td>
                    <button dojoType="dijit.form.Button" name="Save" onclick="dojo.byId('update_ccClient_urlEntry').value =
                                    dijit.byId('order').value">Save
                    </button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button" name="close" type="submit">close</button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button"
                            onclick="window.open(dijit.byId('order').value)">Test
                    </button>
                </td>

            </tr>
        </table>
    </div>

</div>

<!-- orderstatus link-->
<div dojoType="dijit.form.DropDownButton">
    <span>Order Status</span>


    <div dojoType="dijit.TooltipDialog" id="tooltipOrderStatus" title="Edit OrderStatus Link">
        <table>
            <tr>

                <td><label for="orderstatus">Url:</label></td>
                <td><input dojoType="dijit.form.TextBox" style="width: 400px;" type="text"
                           id="orderstatus" name="ccClient.urlStatus"
                           value="<s:property value="%{ccClient.urlStatus}"/>"/></td>
            </tr>
            <tr>
                <td>
                    <button dojoType="dijit.form.Button" name="Save" onclick="dojo.byId('update_ccClient_urlStatus').value =
                                    dijit.byId('orderstatus').value">Save
                    </button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button" type="submit" name="close">Close</button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button"
                            onclick="window.open(dijit.byId('orderstatus').value)">Test
                    </button>
                </td>
            </tr>
        </table>

    </div>
</div>


<!-- customer service link -->

<div dojoType="dijit.form.DropDownButton">
    <span>Customer Service</span>


    <div dojoType="dijit.TooltipDialog" id="tooltipCustomerService" title="Edit Customer Service Link">
        <table>
            <tr>

                <td><label for="customerservice">Url:</label></td>
                <td><input dojoType="dijit.form.TextBox" style="width: 400px;" type="text"
                           id="customerservice" name="ccClient.urlService"
                           value="<s:property value="%{ccClient.urlService}"/>"/></td>
            </tr>
            <tr>
                <td>
                    <button dojoType="dijit.form.Button" name="Save" onclick="dojo.byId('update_ccClient_urlService').value =
                                    dijit.byId('customerservice').value">Save
                    </button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button" type="submit" name="close">Close</button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button"
                            onclick="window.open(dijit.byId('customerservice').value)">Test
                    </button>
                </td>
            </tr>
        </table>

    </div>
</div>
<div dojoType="dijit.form.DropDownButton">
    <span>Kbase</span>


    <div dojoType="dijit.TooltipDialog" id="tooltipKBase" title="Edit Kbase">
        <table>
            <tr>

                <td><label for="customerservice">Url:</label></td>
                <td><input dojoType="dijit.form.TextBox" style="width: 400px;" type="text"
                           id="kbase" name="ccClient.urlKbBase"
                           value="<s:property value="%{ccClient.urlKbBase}"/>"/></td>
            </tr>
            <tr>
                <td>
                    <button dojoType="dijit.form.Button" name="Save" onclick="dojo.byId('update_ccClient_urlKbBase').value =
                                    dijit.byId('kbase').value">Save
                    </button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button" type="submit" name="close">Close</button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button"
                            onclick="window.open(dijit.byId('kbase').value)">Test
                    </button>
                </td>
            </tr>
        </table>

    </div>
</div>

<div dojoType="dijit.form.DropDownButton">
    <span>AutoShip</span>


    <div dojoType="dijit.TooltipDialog" id="tooltipQuickRef" title="Edit AutoShip Link">
        <table>
            <tr>

                <td><label for="customerservice">Url:</label></td>
                <td><input dojoType="dijit.form.TextBox" style="width: 400px;" type="text"
                           id="quickref" name="ccClient.urlQuickRef"
                           value="<s:property value="%{ccClient.urlQuickRef}"/>"/></td>
            </tr>
            <tr>
                <td>
                    <button dojoType="dijit.form.Button" name="Save" onclick="dojo.byId('update_ccClient_urlQuickRef').value =
                                    dijit.byId('quickref').value">Save
                    </button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button" type="submit" name="close">Close</button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button"
                            onclick="window.open(dijit.byId('quickref').value)">Test
                    </button>
                </td>
            </tr>
        </table>

    </div>
</div>
<div dojoType="dijit.form.DropDownButton">
    <span><s:property value="%{ccClient.topUrlSixName}"/></span>


    <div dojoType="dijit.TooltipDialog" id="tooltipUrlSix" title="Edit <s:property value="%{ccClient.topUrlSixName}"/> Link">
        <table>
            <tr>

                <td><label for="customerservice">Url:</label></td>
                <td><input dojoType="dijit.form.TextBox" style="width: 400px;" type="text"
                           id="topsix" name="ccClient.topUrlSix"
                           value="<s:property value="%{ccClient.topUrlSix}"/>"/></td>
            </tr>
            <tr>
                <td><label for="topsixname">Url Name:</label></td>
                <td><input dojoType="dijit.form.TextBox" style="width:200px;" type="text"
                           id="topsixname" name="ccClient.topUrlSixName"
                value="<s:property value="%{ccClient.topUrlSixName}"/>"/></td>
            </tr>

            <tr>
                <td>
                    <button dojoType="dijit.form.Button" name="Save" onclick="{
                    dojo.byId('update_ccClient_topUrlSix').value = dijit.byId('topsix').value;
                    dojo.byId('update_ccClient_topUrlSixName').value = dijit.byId('topsixname').value;
                            }">Save
                    </button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button" type="submit" name="close">Close</button>
                </td>
                <td>
                    <button dojoType="dijit.form.Button"
                            onclick="window.open(dijit.byId('topsix').value)">Test
                    </button>
                </td>
            </tr>
        </table>

    </div>
</div>
<div>&lt;AGENT_ID&gt; , &lt;AGENT_NAME&gt;, &lt;CALL_ID&gt; can be used in any url.</div>


</div>
<div dojoType="dijit.layout.ContentPane"
     style="width: 200px;" id="leftAccordion" region="leading" splitter="false">
    <s:form action="update" theme="simple">
        <s:hidden name="ccClient.id" theme="simple"/>
        <s:hidden name="id" theme="simple"/>
        <s:hidden name="ccClient.urlEntry"/>
        <s:hidden name="ccClient.urlStatus"/>
        <s:hidden name="ccClient.urlService"/>
        <s:hidden name="ccClient.urlKbBase"/>
        <s:hidden name="ccClient.urlQuickRef"/>
        <s:hidden name="ccClient.topUrlSixName"/>
        <s:hidden name="ccClient.topUrlSix"/>
        <s:hidden name="ccClient.frameUrl"/>
        <s:hidden name="ccClient.announceScript"/>
        <s:hidden name="ccClient.sideInfo"/>
        <s:hidden name="ccClient.sideLinks"/>

        <s:hidden name="deploy" value="0"/>
        Owd Client:
        <s:select list="clientList" listKey="action" listValue="display" name="ccClient.clientFkey" theme="simple"/>
        <br>
        <center><b>Campaign Name: <s:textfield name="ccClient.mlogCampaignName"/> </b></center>
        <hr>
        TicketBox: <input type=text name="ccClient.ticketbox" value="<s:property value="ccClient.ticketbox"/>"/><br>
        EmailDomain: <input type=text name="ccClient.emailDomain" value="<s:property value="ccClient.emailDomain"/>"/>
        <br>
        Order Ref Entry: <s:checkbox name="orderRef" fieldValue="1"/>
        <br>
        Average Call Time: <s:textfield name="ccClient.callThreshold"/>
        <center>
            <button id="sub" dojoType="dijit.form.Button">Save Changes</button>
            <button id="subdep" dojoType="dijit.form.Button">Save & Deploy</button>

        </s:form>
         <button id="back" dojoType="dijit.form.Button" onclick="parent.location='<s:url action="list"/>'">Cancel go Back</button>
        <button id="del" dojoType="dijit.form.Button">Delete</button>
             </center>
    <hr>

        <s:actionerror/>


    <hr>
    <div id="cal"></div>
    <br><br>
    <b>SEND:</b><br>
    <center>
        <a href="http://internal.owd.com:8080/callcenter/problemForm.do?client=<s:property value="ccClient.mlogCampaignName"/> &amp;callId="
           target="_blank"
           title="send problem form">Escalation Form</a>

        <p><a href="http://internal.owd.com:8080/callcenter/caseSearch.do" target="_blank">Escalation Form Search</a>

        <p>
         <a href="http://internal.owd.com/machform/view.php?id=10" target="_blank">Supervisor Escalation Form</a>
        </p></center>
          <input name="ccClient.sideLinks" id="ccClient.sideLinks" dojoType="dijit.form.TextBox" style="width: 80%;"
               type="text" value="<s:property value="ccClient.sideLinks"/>">
        <button id="upda" dojoType="dijit.form.Button">Update</button>
    <iframe id="theframeside" src="" width="98%" height="900px"></iframe>

</div>
<div dojoType="dijit.layout.BorderContainer" liveSplitters="true" region="center" splitter="false" s>

    <div dojoType="dijit.layout.ContentPane" id="topscreen" region="top" style="height:300px">
        <textarea style="width:97%" class="topedit" id="announceScript">
            <s:property value="%{ccClient.announceScript}"/>


        </textarea>
    </div>
    <div dojoType="dijit.layout.ContentPane" id="leftscreen" region="left" style="width:48%">

        <textarea id="sideInfo" style="width:80%" class="leftedit">
            <s:property value="%{ccClient.sideInfo}"/>
        </textarea>
    </div>
    <div dojoType="dojox.layout.ContentPane" id="rightscreen" region="right" style="width:48%">
        <input name="ccClient.frameUrl" id="ccClient.frameUrl" dojoType="dijit.form.TextBox" style="width: 80%;"
               type="text" value="<s:property value="ccClient.frameUrl"/>">
        <button id="upd" dojoType="dijit.form.Button">update</button>
        <div id="replaceable" style="height:98%; width:97%">
            
        </div>

    </div>


</div>


</div>
<!--end Border Container -->


</body>
</html>
