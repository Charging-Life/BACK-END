package com.example.charging_life.member.entity;

import com.example.charging_life.station.entity.Business;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MemberBusiness {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id") @JsonIgnore
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "business_id") @JsonIgnore
    private Business business;

    public MemberBusiness(Member member, Business business) {
        this.member = member;
        this.business = business;
        member.addBusiness(this);
        business.addMember(this);
    }
}
