<FORM action="resolvehold.jsp" method=POST>
       <INPUT TYPE=HIDDEN NAME=sethold VALUE=1>
    <INPUT TYPE=HIDDEN NAME=owdref VALUE=<%= owdref %>>
        <H1>Confirm Warehouse Hold</H1><HR>

     <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Notification</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Send an email reporting this problem to the
                        following people (select as many as needed)</p>
                </td>
            </tr>
            <tr>

                <td>

                    <UL>
                        <LI><INPUT TYPE=checkbox NAME=notifyAM
                                   VALUE="1" <%= holder.getNotifyAM().length()>0?"CHECKED":""%>> Account Manager
                            (<%= order.getClient().getAmEmail()%>)
                        <LI><INPUT TYPE=checkbox NAME=notifyIT
                                   VALUE="1" <%= holder.getNotifyIT().length()>0?"CHECKED":""%>> IT
                            (casetracker@owd.com)
                        <LI><INPUT TYPE=checkbox NAME=notifyEmail
                                   VALUE="1" <%= holder.getNotifyEmail().length()>0?"CHECKED":""%>> Other Email: <INPUT
                                TYPE=TEXT NAME=notifyEmailAddress VALUE="<%= holder.getNotifyEmail()%>">
                        <LI><INPUT TYPE=checkbox NAME=notifyUser
                                   VALUE="1" <%= holder.getNotifyUser().length()>0?"CHECKED":""%>> Yourself
                            (<%= user.getEmail() %>)
                        </UL>
                </td>

            </tr>
        </table>
    </div>

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Resolution Notification</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Notifications of this problem will include
                        instructions to contact the following people, as indicated, when the issue is resolved.<BR>Email
                        notifications of problem resolution will be sent automatically if requested.</p>
                </td>
            </tr>
            <tr>

                <td>

                    <UL>
                        <LI><INPUT TYPE=checkbox NAME=resNotifyAM
                                   VALUE=1 <%= holder.getResNotifyAM().length()>0?"CHECKED":""%>> Account Manager
                            (<%= order.getClient().getAmEmail()%>)
                        <LI><INPUT TYPE=checkbox NAME=resNotifyPhone
                                   VALUE=1 <%= holder.getResNotifyPhone().length()>0?"CHECKED":""%>> By Phone (type name
                            and number): <INPUT TYPE=TEXT NAME=resNotifyPhoneText
                                                VALUE="<%= holder.getResNotifyPhone()%>">
                        <LI><INPUT TYPE=checkbox NAME=resNotifyEmail
                                   VALUE=1 <%= holder.getResNotifyEmail().length()>0?"CHECKED":""%>> Other Email: <INPUT
                                TYPE=TEXT NAME=resNotifyEmailAddress VALUE="<%= holder.getResNotifyEmail()%>">
                        <LI><INPUT TYPE=checkbox NAME=resNotifySelf
                                   VALUE=1 <%= holder.getResNotifyUser().length()>0?"CHECKED":""%>> Yourself
                            (<%= user.getEmail() %>)
                        </UL>
                </td>

            </tr>
        </table>
    </div>

    <div style="background: #ffffff; border-top: 1px solid #ccc; padding: 10px 15px;
                     margin-top: 25px;">
        <input type="image" src="/internal/images/b-save_changes.gif" alt="Save changes"
               onClick="return validateForm();"/>
    </div>
    </form>