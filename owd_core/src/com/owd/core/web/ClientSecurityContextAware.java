package com.owd.core.web;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jun 6, 2008
 * Time: 12:08:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientSecurityContextAware {

    public void setClientSecurityContext(ClientSecurityContext csc);

}
