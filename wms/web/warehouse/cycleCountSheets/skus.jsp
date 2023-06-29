<%@ taglib prefix="s" uri="/struts-tags" %>


<div class="advanced">
    <s:if test="groupList.size()>1">
    <div class="group">
<s:form action="load.action" theme="simple">
      <s:hidden name="facility"/>
    <s:hidden name="clientId"/>
    <s:select list="groupList" name="group" label="group" headerKey="-1" headerValue="Select Group" cssClass="group"/>

   <s:submit value="Load Group Sku's"></s:submit>
</s:form>
    </div>
    </s:if>


    <div id="accordion">
        <h3>Load By Inventory Num</h3>
        <div>
            Enter a coma seperated List or one on each line
           <div class="typelist">
            <s:form action="load.action" theme="simple">
                <s:hidden name="facility"/>
                <s:hidden name="clientId"/>
              <s:textarea name="inventoryNumList" cssClass="numArea"/>
                <br>
                <s:submit value="Load List of Inventory Num's"></s:submit>
            </s:form>
           </div>
            <div class="dotheselect">
                <div class="wrap">
                    <table>
                        <tr>
                            <td>
                          <!--    Select and move them over  <br>
                             <s:select list="skuList" listKey="invNumber" listValue="invNumber" multiple="true" id="lstBox1" theme="simple"/>
                              -->
                            </td>
                            <td style='width:50px;text-align:center;vertical-align:middle;'>
                                <input type='button' id='btnRight' value ='  >  '/>
                                <br/><input type='button' id='btnLeft' value ='  <  '/>
                            </td>
                            <td>
                                Sku's over here will be used  <br>
                                <s:form action="load.action" theme="simple" id="manualFilter">
                                    <s:hidden name="facility"/>
                                    <s:hidden name="clientId"/>



                                <select multiple="multiple" id='lstBox2' name="inventoryNumMulti">
                                    </select>
                                    <br>
                                    <button id="load" class="load" onClick="selectAndSubmit(); return false;" >Load These Sku's</button>
                                </s:form>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                            Search and add.
                            <input  class="selector" name="options"/>
                                <br>
                                <button id="search" class="search" onClick="lookupAndMove(); return false;" >Use Sku</button>
                            </td>
                        </tr>
                    </table>
                </div>


            </div>

        </div>
        <h3>Load By Inventory Id</h3>
        <div>
            Enter a coma seperated List or one on each line
            <s:form action="load.action" theme="simple">
                <s:hidden name="facility"/>
                <s:hidden name="clientId"/>
                <s:textarea name="inventoryIdList" cssClass="numArea"/>
               <br>
                <s:submit value="Load List of Inventory Id's"></s:submit>
            </s:form>
        </div>

    </div>
</div>


<script>
    $( "#accordion" ).accordion();

    $('.selector').autocomplete({
       source:function( request, response ) {
           $.ajax({
               url: "/wms/cycleCountSheets/searchSku.action",
               dataType: "json",
               data: {
                   searchString: request.term,
                   clientId:<s:property value="clientId"/>,
                   facility:'<s:property value="facility"/>'
               },
               success: function( data ) {
                   response( data );
               }
           });
       },
        minLength: 3,
        select: function( event, ui ) {
           $('#search').click();
        }
    });
    $('#lstBox1').dblclick(function(){$('#btnRight').click();});
    $('#lstBox2').dblclick(function(){$('#btnLeft').click();});

    $('#btnRight').click(function(e) {
        var selectedOpts = $('#lstBox1 option:selected');
        if (selectedOpts.length == 0) {
           // alert("Nothing to move.");
            e.preventDefault();
        }

        $('#lstBox2').append($(selectedOpts).clone());
        $(selectedOpts).remove();
        e.preventDefault();
    });

    $('#btnLeft').click(function(e) {
        var selectedOpts = $('#lstBox2 option:selected');
        if (selectedOpts.length == 0) {
          //  alert("Nothing to move.");
            e.preventDefault();
        }

        $('#lstBox1').append($(selectedOpts).clone());
        $(selectedOpts).remove();
        e.preventDefault();
    });
    function lookupAndMove(){
        var s = $('.selector').val();
        $('#lstBox2')[0].options.add(new Option(s,s));
       // $('#btnRight').click();
        $('.selector').select();
        $('.selector').focus();
    }
    function selectAndSubmit(){
        $('#lstBox2 option').prop('selected', true);
        $('#manualFilter').submit();
    }
</script>
