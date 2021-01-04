/**
 * BpelRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.bpel;

public class BBpelRequest  implements java.io.Serializable {
    private java.lang.String destination;
    private java.lang.String messageId;
    private java.lang.String note;
    private java.lang.Long orderId;
    private java.lang.String relatesTo;
    private java.lang.Long serverId;
    private java.lang.String source;
    private java.lang.Long userId;

    public BBpelRequest() {
    }

    public BBpelRequest(
           java.lang.String destination,
           java.lang.String messageId,
           java.lang.String note,
           java.lang.Long orderId,
           java.lang.String relatesTo,
           java.lang.Long serverId,
           java.lang.String source,
           java.lang.Long userId) {
           this.destination = destination;
           this.messageId = messageId;
           this.note = note;
           this.orderId = orderId;
           this.relatesTo = relatesTo;
           this.serverId = serverId;
           this.source = source;
           this.userId = userId;
    }


    /**
     * Gets the destination value for this BpelRequest.
     * 
     * @return destination
     */
    public java.lang.String getDestination() {
        return destination;
    }


    /**
     * Sets the destination value for this BpelRequest.
     * 
     * @param destination
     */
    public void setDestination(java.lang.String destination) {
        this.destination = destination;
    }


    /**
     * Gets the messageId value for this BpelRequest.
     * 
     * @return messageId
     */
    public java.lang.String getMessageId() {
        return messageId;
    }


    /**
     * Sets the messageId value for this BpelRequest.
     * 
     * @param messageId
     */
    public void setMessageId(java.lang.String messageId) {
        this.messageId = messageId;
    }


    /**
     * Gets the note value for this BpelRequest.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this BpelRequest.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the orderId value for this BpelRequest.
     * 
     * @return orderId
     */
    public java.lang.Long getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this BpelRequest.
     * 
     * @param orderId
     */
    public void setOrderId(java.lang.Long orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the relatesTo value for this BpelRequest.
     * 
     * @return relatesTo
     */
    public java.lang.String getRelatesTo() {
        return relatesTo;
    }


    /**
     * Sets the relatesTo value for this BpelRequest.
     * 
     * @param relatesTo
     */
    public void setRelatesTo(java.lang.String relatesTo) {
        this.relatesTo = relatesTo;
    }


    /**
     * Gets the serverId value for this BpelRequest.
     * 
     * @return serverId
     */
    public java.lang.Long getServerId() {
        return serverId;
    }


    /**
     * Sets the serverId value for this BpelRequest.
     * 
     * @param serverId
     */
    public void setServerId(java.lang.Long serverId) {
        this.serverId = serverId;
    }


    /**
     * Gets the source value for this BpelRequest.
     * 
     * @return source
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this BpelRequest.
     * 
     * @param source
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the userId value for this BpelRequest.
     * 
     * @return userId
     */
    public java.lang.Long getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this BpelRequest.
     * 
     * @param userId
     */
    public void setUserId(java.lang.Long userId) {
        this.userId = userId;
    }
    
    public String toString(){
    	return "BBpelRequest[ destination = "+destination+"; messageId = "+messageId+"; note = "+note+"; orderId = "+orderId+"; relatesTo = "+relatesTo+"; server = "+serverId+"; source = "+source+"; userId = "+userId+"]";
    }

}
