package com.stub.api.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractModelDTO implements Serializable {


    private static final long serialVersionUID = 1L;
    private Date entryDt;
    private Date lastModifiedDate;
    
    
}
