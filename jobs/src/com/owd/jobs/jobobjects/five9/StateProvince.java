
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stateProvince.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stateProvince">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ANY"/>
 *     &lt;enumeration value="US_ALABAMA"/>
 *     &lt;enumeration value="US_ALASKA"/>
 *     &lt;enumeration value="US_ARIZONA"/>
 *     &lt;enumeration value="US_ARKANSAS"/>
 *     &lt;enumeration value="US_CALIFORNIA"/>
 *     &lt;enumeration value="US_COLORADO"/>
 *     &lt;enumeration value="US_CONNECTICUT"/>
 *     &lt;enumeration value="US_DELAWARE"/>
 *     &lt;enumeration value="US_FLORIDA"/>
 *     &lt;enumeration value="US_GEORGIA"/>
 *     &lt;enumeration value="US_HAWAII"/>
 *     &lt;enumeration value="US_IDAHO"/>
 *     &lt;enumeration value="US_ILLINOIS"/>
 *     &lt;enumeration value="US_INDIANA"/>
 *     &lt;enumeration value="US_IOWA"/>
 *     &lt;enumeration value="US_KANSAS"/>
 *     &lt;enumeration value="US_KENTUCKY"/>
 *     &lt;enumeration value="US_LOUISIANA"/>
 *     &lt;enumeration value="US_MAINE"/>
 *     &lt;enumeration value="US_MARYLAND"/>
 *     &lt;enumeration value="US_MASSACHUSETTS"/>
 *     &lt;enumeration value="US_MICHIGAN"/>
 *     &lt;enumeration value="US_MINNESOTA"/>
 *     &lt;enumeration value="US_MISSISSIPPI"/>
 *     &lt;enumeration value="US_MISSOURI"/>
 *     &lt;enumeration value="US_MONTANA"/>
 *     &lt;enumeration value="US_NEBRASKA"/>
 *     &lt;enumeration value="US_NEVADA"/>
 *     &lt;enumeration value="US_NEW_HAMPSHIRE"/>
 *     &lt;enumeration value="US_NEW_JERSEY"/>
 *     &lt;enumeration value="US_NEW_MEXICO"/>
 *     &lt;enumeration value="US_NEW_YORK"/>
 *     &lt;enumeration value="US_NORTH_CAROLINA"/>
 *     &lt;enumeration value="US_NORTH_DAKOTA"/>
 *     &lt;enumeration value="US_OHIO"/>
 *     &lt;enumeration value="US_OKLAHOMA"/>
 *     &lt;enumeration value="US_OREGON"/>
 *     &lt;enumeration value="US_PENNSYLVANIA"/>
 *     &lt;enumeration value="US_RHODE_ISLAND"/>
 *     &lt;enumeration value="US_SOUTH_CAROLINA"/>
 *     &lt;enumeration value="US_SOUTH_DAKOTA"/>
 *     &lt;enumeration value="US_TENNESSEE"/>
 *     &lt;enumeration value="US_TEXAS"/>
 *     &lt;enumeration value="US_UTAH"/>
 *     &lt;enumeration value="US_VERMONT"/>
 *     &lt;enumeration value="US_VIRGINIA"/>
 *     &lt;enumeration value="US_WASHINGTON"/>
 *     &lt;enumeration value="US_WEST_VIRGINIA"/>
 *     &lt;enumeration value="US_WISCONSIN"/>
 *     &lt;enumeration value="US_WYOMING"/>
 *     &lt;enumeration value="CA_ALBERTA"/>
 *     &lt;enumeration value="CA_BRITISH_COLUMBIA"/>
 *     &lt;enumeration value="CA_MANITOBA"/>
 *     &lt;enumeration value="CA_NEW_BRUNSWICK"/>
 *     &lt;enumeration value="CA_NEWFOUNDLAND_AND_LABRADOR"/>
 *     &lt;enumeration value="CA_NOVA_SCOTIA"/>
 *     &lt;enumeration value="CA_ONTARIO"/>
 *     &lt;enumeration value="CA_PRINCE_EDWARD_ISLAND"/>
 *     &lt;enumeration value="CA_QUEBEC"/>
 *     &lt;enumeration value="CA_SASKATCHEWAN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stateProvince")
@XmlEnum
public enum StateProvince {

    ANY,
    US_ALABAMA,
    US_ALASKA,
    US_ARIZONA,
    US_ARKANSAS,
    US_CALIFORNIA,
    US_COLORADO,
    US_CONNECTICUT,
    US_DELAWARE,
    US_FLORIDA,
    US_GEORGIA,
    US_HAWAII,
    US_IDAHO,
    US_ILLINOIS,
    US_INDIANA,
    US_IOWA,
    US_KANSAS,
    US_KENTUCKY,
    US_LOUISIANA,
    US_MAINE,
    US_MARYLAND,
    US_MASSACHUSETTS,
    US_MICHIGAN,
    US_MINNESOTA,
    US_MISSISSIPPI,
    US_MISSOURI,
    US_MONTANA,
    US_NEBRASKA,
    US_NEVADA,
    US_NEW_HAMPSHIRE,
    US_NEW_JERSEY,
    US_NEW_MEXICO,
    US_NEW_YORK,
    US_NORTH_CAROLINA,
    US_NORTH_DAKOTA,
    US_OHIO,
    US_OKLAHOMA,
    US_OREGON,
    US_PENNSYLVANIA,
    US_RHODE_ISLAND,
    US_SOUTH_CAROLINA,
    US_SOUTH_DAKOTA,
    US_TENNESSEE,
    US_TEXAS,
    US_UTAH,
    US_VERMONT,
    US_VIRGINIA,
    US_WASHINGTON,
    US_WEST_VIRGINIA,
    US_WISCONSIN,
    US_WYOMING,
    CA_ALBERTA,
    CA_BRITISH_COLUMBIA,
    CA_MANITOBA,
    CA_NEW_BRUNSWICK,
    CA_NEWFOUNDLAND_AND_LABRADOR,
    CA_NOVA_SCOTIA,
    CA_ONTARIO,
    CA_PRINCE_EDWARD_ISLAND,
    CA_QUEBEC,
    CA_SASKATCHEWAN;

    public String value() {
        return name();
    }

    public static StateProvince fromValue(String v) {
        return valueOf(v);
    }

}
