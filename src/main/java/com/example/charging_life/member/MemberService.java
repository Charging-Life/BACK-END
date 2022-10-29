package com.example.charging_life.member;

import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.member.dto.StationReqDto;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.member.entity.MemberDestination;
import com.example.charging_life.member.repo.JpaMemberDestinationRepo;
import com.example.charging_life.member.repo.JpaMemberRepo;
import com.example.charging_life.member.repo.JpaMemberStationRepo;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.repository.ChargingStationRepository;
import com.example.charging_life.station.repository.JpaStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final JpaMemberRepo jpaMemberRepo;
    private final ChargingStationRepository stationRepo;
    private final JpaMemberStationRepo jpaMemberStationRepo;
    private final JpaStationRepository jpaStationRepository;
    private final JpaMemberDestinationRepo jpaMemberDestinationRepo;

    public void join(Member member) {
        jpaMemberRepo.save(member);
    }

    public Member findMemberByEmail(String email) {
        Member member = jpaMemberRepo.findByEmail(email)
                .orElseThrow(()->new CustomException(ExceptionEnum.EmailNotMatched));
        return member;
    }

    @Override // UserDetailsService 를 상속하여 해당 메소드를 구현해야지만 사용가능하다.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return jpaMemberRepo.findByEmail(email)
                .orElseThrow(()->new CustomException(ExceptionEnum.EmailNotMatched));
    }

    @Transactional
    public void enrollStation(Member member, StationReqDto stationReqDto) {
        List<ChargingStation> chargingStations = stationRepo.findAllByStatId(stationReqDto.getStatId());
        List<MemberChargingStation> memberChargingStations = member.getMemberChargingStations();
        List<ChargingStation> filterChargingStation = removeDuplicate(chargingStations, memberChargingStations);
        for (ChargingStation chargingStation : filterChargingStation) {
            jpaMemberStationRepo.save(new MemberChargingStation(member, chargingStation));
        }
    }

    @Transactional
    public void enrollDestination(Member member, StationReqDto stationReqDto) {
        ChargingStation station = jpaStationRepository.findByStatId(stationReqDto.getStatId().get(0));
        if (jpaMemberDestinationRepo.existsByChargingStation_idAndMember(station.getId(), member)) {
            throw new CustomException(ExceptionEnum.MemberDestinationDuplicated);
        }
        MemberDestination memberDestination = new MemberDestination(member, station);
        jpaMemberDestinationRepo.save(memberDestination);
    }

    public List<ChargingStation> removeDuplicate(List<ChargingStation> chargingStations,
                                                 List<MemberChargingStation> memberChargingStations) {
        List<ChargingStation> filterStations = new ArrayList<>();
        for (ChargingStation chargingStation : chargingStations) {
            boolean dup = false;
            for (MemberChargingStation memberChargingStation : memberChargingStations) {
                if (chargingStation.getStatId() == memberChargingStation.getChargingStation().getStatId()) {
                    dup = true;
                }
            }
            if(!dup) filterStations.add(chargingStation);
        }
        return filterStations;
    }

    public boolean checkFavorite(Member member, String statId) {
        ChargingStation station = jpaStationRepository.findByStatId(statId);
        return jpaMemberStationRepo.existsByMemberAndChargingStation(member, station);
    }

    @Transactional
    public void deleteStation(Member member, Long statId) {
        ChargingStation station = jpaStationRepository.findById(statId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.StationDoesNotExist));
        jpaMemberStationRepo.deleteByMemberAndChargingStation(member,station);
    }

    @Transactional
    public void removeDestination(Member member, String statId) {
        ChargingStation station = jpaStationRepository.findByStatId(statId);
        jpaMemberDestinationRepo.deleteByChargingStationAndMember(station,member);
    }

    @Transactional
    public void drop(Member member) {
        jpaMemberRepo.delete(member);
    }
}
