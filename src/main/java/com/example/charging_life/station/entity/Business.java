package com.example.charging_life.station.entity;

import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberBusiness;
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
    private String businessCall;
    private String businessImg;
    @OneToMany(mappedBy = "business")
    private List<MemberBusiness> memberBusinesses = new ArrayList<>();

    public Business(String businessId, String business, String businessCall, String businessImg) {
        this.businessId = businessId;
        this.business = business;
        this.businessCall = businessCall;
        this.businessImg = businessImg;
    }

    public void addMember(MemberBusiness memberBusiness) {
        this.memberBusinesses.add(memberBusiness);
    }
}
