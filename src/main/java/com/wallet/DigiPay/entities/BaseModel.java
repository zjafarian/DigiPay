package com.wallet.DigiPay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

    @JsonIgnore
    @Column(name = "date_modified")
    private Timestamp dateModified;




}
