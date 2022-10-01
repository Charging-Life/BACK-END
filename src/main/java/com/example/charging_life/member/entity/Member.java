package com.example.charging_life.member.entity;


import com.example.charging_life.car.Car;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member implements UserDetails {
    @Id @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String phone;
    @Enumerated(value = EnumType.STRING)
    private Auth auth;
    @OneToMany(mappedBy = "member")
    private List<Car> cars = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberBusiness> businesses = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberChargingStation> memberChargingStations = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, String phone, Auth auth, Car car) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.auth = auth;
        this.cars.add(car);
    }

    public void addBusiness(MemberBusiness memberBusiness) {
        this.businesses.add(memberBusiness);
    }

    public void addChargingStation(MemberChargingStation memberChargingStation) {
        this.memberChargingStations.add(memberChargingStation);
    }

    @Override
    // 원래 해당 레벨에서 여러개의 인증 권한을 가질 수 있는 경우를 위해 collection 을 사용
    // 우선 그런 경우를 상정하지 않고 TokenService 에서 직접 해당 객체를 만들어 사용하는 것으로 대체
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    //계정의 만료 여부 리턴
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    //계정의 잠금 여부 리턴
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    //비밀번호 만료 여부 리턴
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    //계정 활성화 여부 리턴
    public boolean isEnabled() {
        return false;
    }
}
