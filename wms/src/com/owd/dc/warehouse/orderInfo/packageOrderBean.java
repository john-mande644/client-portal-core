package com.owd.dc.warehouse.orderInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Dec 29, 2010
 * Time: 9:41:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class packageOrderBean {
    private String id;
    private String packerRef;
    private String packerName;
    private String packStart;
    private String packEnd;
    private boolean isVoid;
    private String voidTime;
    private String voidBy;
    private String numberOfPackages;
    private String packagesShipped;
    private String packType;
    private List<packageBean> packages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackerRef() {
        return packerRef;
    }

    public void setPackerRef(String packerRef) {
        this.packerRef = packerRef;
    }

    public String getPackerName() {
        return packerName;
    }

    public void setPackerName(String packerName) {
        this.packerName = packerName;
    }

    public String getPackStart() {
        return packStart;
    }

    public void setPackStart(String packStart) {
        this.packStart = packStart;
    }

    public String getPackEnd() {
        return packEnd;
    }

    public void setPackEnd(String packEnd) {
        this.packEnd = packEnd;
    }

    public boolean isVoid() {
        return isVoid;
    }

    public void setVoid(boolean aVoid) {
        isVoid = aVoid;
    }

    public String getVoidTime() {
        return voidTime;
    }

    public void setVoidTime(String voidTime) {
        this.voidTime = voidTime;
    }

    public String getVoidBy() {
        return voidBy;
    }

    public void setVoidBy(String voidBy) {
        this.voidBy = voidBy;
    }

    public String getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(String numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public String getPackagesShipped() {
        return packagesShipped;
    }

    public void setPackagesShipped(String packagesShipped) {
        this.packagesShipped = packagesShipped;
    }

    public List<packageBean> getPackages() {
        return packages;
    }

    public void setPackages(List<packageBean> packages) {
        this.packages = packages;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }
}
