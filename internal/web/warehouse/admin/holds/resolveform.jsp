<FORM action="resolvehold.jsp" method=POST>
  
    <INPUT TYPE=HIDDEN NAME=sethold VALUE=1>
    <INPUT TYPE=HIDDEN NAME=owdref VALUE=<%= owdref %>>

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Report Resolution</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Indicate how the problem was resolved</p>
                </td>
            </tr>
            <tr>

                <td colspan=2>

                    <UL>
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Order Edited" <%= "Order Edited".equals(holder.getResolutionType())?"CHECKED":"" %>>
                            Order edited without reprint
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Order Reprinted/Reposted" <%= "Order Reprinted/Reposted".equals(holder.getResolutionType())?"CHECKED":"" %>>
                            Order edited and reprinted/reposted
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Inventory Located" <%= "Inventory Located".equals(holder.getResolutionType())?"CHECKED":"" %>>
                            Inventory was found after further investigation
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Order Cancelled" <%= "Order Cancelled".equals(holder.getResolutionType())?"CHECKED":"" %>>
                            Order cancelled
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="IT Resolved" <%= "IT Resolved".equals(holder.getResolutionType())?"CHECKED":"" %>>
                            IT action resolved problem
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Other" <%= "Other".equals(holder.getResolutionType())?"CHECKED":"" %>> Other
                            (describe fully in notes section)
                        </UL>

                </td>

            </tr>
            <tr><td>Notes:</td><td align=left><TEXTAREA NAME="res_note" rows=5
                                                        cols=40><%= holder.getResNote()%></TEXTAREA>
                <BR>

                <p style="margin: 0px; font-size: 10px; color: #666;">Notes/comments</p>

            </td></tr>
        </table>
    </div>


    <div style="background: #ffffff; border-top: 1px solid #ccc; padding: 10px 15px;
                     margin-top: 25px;">
        <input type="image" src="/internal/images/b-save_changes.gif" alt="Save changes"
               onClick="return validateForm();"/>
    </div>
       </FORM>