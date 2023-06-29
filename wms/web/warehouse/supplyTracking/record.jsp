<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>


<html>
<head>
    <link href="/wms/warehouse/supplyTracking/supplies.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="wrapper">


    <button class="backbutton" onclick="history.back()">Back</button>
    Tracking Supplies for <s:property value="currentFacility"/>
    <span class="messages"><s:actionmessage /></span>
         <span class="errors"><s:property value="theError"/> </span>
    <s:if test="selectedGroup==null && getQty==false">



        <div class="header">
            Select the Category of what you are using:
        </div>
        <div class="goupSelect">
            <table class="groupTable">
                <tr>
                    <s:iterator value="groupTypes" status="rowstatus" id="var">
                    <td>
                        <s:form action="selectGroup.action">
                            <s:hidden name="currentFacility"/>
                            <s:hidden name="name" id="theName"/>
                            <s:hidden name="selectedGroup" value="%{var}"> </s:hidden>
                            <s:submit value="%{var}" cssClass="groupButtons"></s:submit>
                        </s:form>
                    </td>
                    <s:if test="#rowstatus.even == true">
                </tr>
                <tr>
                    </s:if>

                    </s:iterator>
                </tr>
            </table>

        </div>


    </s:if>
    <s:if test="selectedGroup!=null">
        <div class="ItemSelect">
            <table class="itemTable">
                <tr>
                    <s:iterator value="trackedItems" status="rowstatus">
                    <td>
                        <s:form action="selectItem.action">
                            <s:hidden name="currentFacility"/>
                            <s:hidden name="name" id="theName"/>
                            <s:hidden name="invId" value="%{inventoryId}"> </s:hidden>
                            <s:submit value="%{inventoryNum}" cssClass="itemButtons"></s:submit>

                        </s:form>

                        <s:property value="description"/>
                    </td>
                    <s:if test="#rowstatus.even == true">
                </tr>
                <tr>
                    </s:if>

                    </s:iterator>
                </tr>
            </table>

        </div>


    </s:if>
    <s:if test="getQty">
        <div class="quantity">
            Please enter in the number of <span class="bold"><s:property value="stb.countType"/></span> of <span class="bold"><s:property value="stb.inventoryNum"/> </span>
            you are taking.<br>
             <br>
            <div class="quick">
                Once Touch Entry <br>
              <table class="quicktable">
                  <tr><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="name" id="theName"/>
                    <s:hidden name="qty" value="1" ></s:hidden>
                    <s:submit value="1" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','1')"/>
                </s:form>
                      </td><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="name" id="theName"/>
                    <s:hidden name="qty" value="2" ></s:hidden>
                    <s:submit value="2" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','2')"/>
                </s:form>
                  </td></tr>
                  <tr><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="name" id="theName"/>
                    <s:hidden name="qty" value="3" ></s:hidden>
                    <s:submit value="3" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','3')"/>
                </s:form>
                  </td><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="name" id="theName"/>
                    <s:hidden name="qty" value="4" ></s:hidden>
                    <s:submit value="4" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','4')"/>
                </s:form>
                  </td></tr>
                                  <tr><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="qty" value="5" ></s:hidden>
                    <s:submit value="5" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','5')"/>
                </s:form>
                                  </td><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="name" id="theName"/>
                    <s:hidden name="qty" value="6" ></s:hidden>
                    <s:submit value="6" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','6')"/>
                </s:form>
                                  </td></tr>
                              <tr><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="name" id="theName"/>
                    <s:hidden name="qty" value="7" ></s:hidden>
                    <s:submit value="7" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','7')"/>
                </s:form>
                              </td><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="qty" value="8" ></s:hidden>
                    <s:submit value="8" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','8')"/>
                </s:form>
                              </td></tr>
                          <tr><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="name" id="theName"/>
                    <s:hidden name="qty" value="9" ></s:hidden>
                    <s:submit value="9" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','9')"/>
                </s:form>
                          </td><td>
                <s:form action="endRecord.action">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}" />
                    <s:hidden name="name" id="theName"/>
                    <s:hidden name="qty" value="10" ></s:hidden>
                    <s:submit value="10" cssClass="quickButton" onclick="return confirmTrack('%{stb.inventoryNum}','10')"/>
                </s:form>
                  </td></tr>
              </table>
            </div>
            <div class="type">
                Type in Your qty.
                <br>
                <s:form action="endRecord.action" theme="simple">
                    <s:hidden name="currentFacility"/>
                    <s:hidden name="invId" value="%{invId}"/>
                    <s:hidden name="name" id="theName"/>
                    Qty: <s:textfield name="qty" id="qqty"/>
                    <s:submit onclick="return confirmTrack('%{stb.inventoryNum}',document.getElementById('qqty').value)"/>
                </s:form>
                <table id="numberpad">
                    <tr class="numberpad">
                        <td class="numbertd">
                            <button onclick="key(1)" class="padButton">1</button>
                        </td>
                        <td class="numbertd">
                            <button onclick="key(2)" class="padButton">2</button>
                        </td>
                        <td class="numbertd">
                            <button onclick="key(3)" class="padButton">3</button>
                        </td>
                    </tr>
                    <tr class="numberpad">
                        <td class="numbertd">
                            <button onclick="key(4)" class="padButton">4</button>
                        </td>
                        <td class="numbertd">
                            <button onclick="key(5)" class="padButton">5</button>
                        </td>
                        <td class="numbertd">
                            <button onclick="key(6)" class="padButton">6</button>
                        </td>
                    </tr>
                    <tr class="numberpad">
                        <td class="numbertd">
                            <button onclick="key(7)" class="padButton">7</button>
                        </td>
                        <td class="numbertd">
                            <button onclick="key(8)" class="padButton">8</button>
                        </td>
                        <td class="numbertd">
                            <button onclick="key(9)" class="padButton">9</button>
                        </td>
                    </tr>
                    <tr class="numberpad">
                        <td class="numbertd">

                        </td>
                        <td class="numbertd">
                            <button onclick="key(0)" class="padButton">0</button>
                        </td>
                        <td class="numbertd">

                        </td>
                    </tr>
                    <tr class="numberpad">
                        <td class="numbertd">
                            <button onclick="clr()" class="optionbutton">Clear</button>
                        </td>
                        <td class="numbertd">

                        </td>
                        <td class="numbertd">
                            <button onclick="del()" class="optionbutton">Del</button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

    </s:if>
</div>

<script type="text/javascript">
    function key(number) {
        if (document.getElementById('qqty').value == 0) {
            document.getElementById('qqty').value = number;
        } else {
            document.getElementById('qqty').value = document.getElementById('qqty').value + number;
        }
    }
    function clr() {
        document.getElementById('qqty').value = 0;
    }
    function del() {
        if (document.getElementById('qqty').value.length > 0) {
            document.getElementById('qqty').value = document.getElementById('qqty').value.substring(0, document.getElementById('qqty').value.length - 1)
        }
    }
    function confirmTrack(inventoryNum, qty){
        var agree = confirm("You are Using "+qty+ " of "+inventoryNum);
        if(agree){
            return true;
        } else{
            return false;
        }

    }

</script>

<s:if test="name==null && selectedGroup==null && getQty==false">
    <script type="text/javascript">
        if(document.getElementById('theName').value.length==0){
               var newName = prompt("We need to know who you are :","Please enter you name");
               document.getElementById('theName').value = newName;
           }
    </script>
</s:if>
</body>

</html>