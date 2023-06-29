package com.owd.dc.inventorycount;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 27, 2006
 * Time: 4:55:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class listOfCountEditForms extends ActionForm {

            // --------------------------------------------------------- Instance Variables

            /** FormItems property */
            private countEditBean[] formItems;
           private String id;
    private String location;
    private String invId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }
            // --------------------------------------------------------- Methods



            /**
             * Returns the FormItems.
             * @return Product
             */
            public countEditBean[] getFormItems() {
                return formItems;
            }

            /**
             * Set the FormItems.
             * @param FormItems The FormItems to set
             */
            public void setFormItems(countEditBean[] FormItems) {

                this.formItems = FormItems;
            }

        /**
         * Pattern to match request parameters
         */
        private Pattern itemPattern = Pattern.compile("formItems\\[(\\d+)\\].*");

        /**
         * Method reset
         * Dynamically creates the appropriate product array based on the request
         *
         * @param mapping		The Struts Action mapping
         * @param request		The incoming request
         */
        public void reset(ActionMapping mapping, HttpServletRequest request) {
              System.out.println("inreset");
            Enumeration paramNames = request.getParameterNames();
            int maxSize = 0;
            while (paramNames.hasMoreElements())
            {
                String paramName = (String) paramNames.nextElement();
                Matcher itemMatcher = itemPattern.matcher(paramName);
                if (itemMatcher.matches())
                {
                    String index = itemMatcher.group(1);
                    if (Integer.parseInt(index) > maxSize)
                    {
                        maxSize = Integer.parseInt(index);
                    }
                }
            }
            System.out.println("inreset 1");
            formItems = new countEditBean[maxSize + 1];
            System.out.println("inreset 2");
            for (int i = 0; i <= maxSize; i++)
            {
                formItems[i] = new countEditBean();
            }

        }
    }



