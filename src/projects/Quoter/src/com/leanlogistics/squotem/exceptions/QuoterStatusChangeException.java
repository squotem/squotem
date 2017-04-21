package com.leanlogistics.squotem.exceptions;

import java.util.List;

public class QuoterStatusChangeException extends Exception {
       
    public QuoterStatusChangeException(String errorMsg) {
        super(errorMsg);
    }

    private List<String> detailMesssages;

    public List<String> getDetailMesssages() {
        return detailMesssages;
    }

    public void setDetailMesssages(List<String> detailMesssages) {
        this.detailMesssages = detailMesssages;
    }
    

}
