package com.wallet.DigiPay.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
@Setter


public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @JsonIgnore
    private Long id;


    @JsonIgnore
    @Column(name = "is_deleted")
    private boolean deleted;

    @JsonIgnore
    @Column(name = "date_created")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @JsonIgnore
    @Column(name = "date_modified")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;




}
