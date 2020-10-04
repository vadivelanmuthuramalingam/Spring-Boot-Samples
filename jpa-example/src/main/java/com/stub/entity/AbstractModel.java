package com.stub.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


//reference https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/
@Getter
@Setter
@Data

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
/*
 * @JsonIgnoreProperties( value = {"entryDt", "lastModifiedDate"}, allowGetters
 * = true )
 */public abstract class AbstractModel implements Serializable {


	private static final long serialVersionUID = 1L;
	
	//@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTRY_DT", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=true, updatable=false)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date entryDt;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFY_DT", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=true)
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastModifiedDate;
}
