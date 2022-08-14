package com.example.charging_life.member.entity;

import com.example.charging_life.station.entity.Business;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MemberBusiness {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne @JoinColumn(name = "business_id")
    private Business business;

    public MemberBusiness(Member member, Business business) {
        this.member = member;
        this.business = business;
        member.addBusiness(this);
        business.addMember(this);
    }
}
