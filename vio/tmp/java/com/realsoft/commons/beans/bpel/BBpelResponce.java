/**
 * BpelResponce.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.bpel;

public class BBpelResponce  implements java.io.Serializable {
    private java.lang.String errCode;
    private java.lang.String message;
    private java.lang.Long packId;
    private java.lang.Long serviceCenterId;
    private java.lang.Long serviceCenterTypeId;

    public BBpelResponce() {
    }

    public BBpelResponce(
           java.lang.String errCode,
           java.lang.String message,
           java.lang.Long packId,
           java.lang.Long serviceCenterId,
           java.lang.Long serviceCenterTypeId) {
           this.errCode = errCode;
           this.message = message;
           this.packId = packId;
           this.serviceCenterId = serviceCenterId;
           this.serviceCenterTypeId = serviceCenterTypeId;
    }


    /**
     * Gets the errCode value for this BpelResponce.
     * 
     * @return errCode
     */
    public java.lang.String getErrCode() {
        return errCode;
    }


    /**
     * Sets the errCode value for this BpelResponce.
     * 
     * @param errCode
     */
    public void setErrCode(java.lang.String errCode) {
        this.errCode = errCode;
    }


    /**
     * Gets the message value for this BpelResponce.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this BpelResponce.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the packId value for this BpelResponce.
     * 
     * @return packId
     */
    public java.lang.Long getPackId() {
        return packId;
    }


    /**
     * Sets the packId value for this BpelResponce.
     * 
     * @param packId
     */
    public void setPackId(java.lang.Long packId) {
        this.packId = packId;
    }


    /**
     * Gets the serviceCenterId value for this BpelResponce.
     * 
     * @return serviceCenterId
     */
    public java.lang.Long getServiceCenterId() {
        return serviceCenterId;
    }


    /**
     * Sets the serviceCenterId value for this BpelResponce.
     * 
     * @param serviceCenterId
     */
    public void setServiceCenterId(java.lang.Long serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }


    /**
     * Gets the serviceCenterTypeId value for this BpelResponce.
     * 
     * @return serviceCenterTypeId
     */
    public java.lang.Long getServiceCenterTypeId() {
        return serviceCenterTypeId;
    }


    /**
     * Sets the serviceCenterTypeId value for this BpelResponce.
     * 
     * @param serviceCenterTypeId
     */
    public void setServiceCenterTypeId(java.lang.Long serviceCenterTypeId) {
        this.serviceCenterTypeId = serviceCenterTypeId;
    }
    
    public String toString(){
    	return "BBpelResponce[errCode = "+errCode+"; message = "+message+"; packId = "+packId+"; serviceCenterId = "+serviceCenterId+"; serviceCenterTypeId = "+serviceCenterTypeId+"]";
    }

}
