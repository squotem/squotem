package com.leanlogistics.squotem.webapp.screenobjects;

public class BaseAjaxResponse {
    
    private boolean success;
    private String errorMessage;
    
    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }    

}
