package com.example.charging_life.station.entity;


import com.example.charging_life.member.entity.MemberBusiness;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Business {

    @Id @GeneratedValue
    private Long id;
    private String businessId;
    private String business;
    private String operator;
    private String businessCall;
    @OneToMany(mappedBy = "business")
    private List<MemberBusiness> memberBusinesses = new ArrayList<>();

    @Builder
    public Business(String businessId, String business, String operator, String businessCall) {
        this.businessId = businessId;
        this.business = business;
        this.operator = operator;
        this.businessCall = businessCall;
    }

    public void addMember(MemberBusiness memberBusiness) {
        this.memberBusinesses.add(memberBusiness);
    }
}
