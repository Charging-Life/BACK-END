package com.example.charging_life.board;

import com.example.charging_life.board.dto.*;
import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.file.FileHandler;
import com.example.charging_life.file.FileService;
import com.example.charging_life.board.entity.LikeMembers;
import com.example.charging_life.board.repository.JpaBoardRepository;
import com.example.charging_life.board.repository.JpaLikeMembersRepository;
import com.example.charging_life.file.dto.FileResDto;
import com.example.charging_life.file.entity.File;
import com.example.charging_life.file.repository.JpaFileRepository;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.repo.JpaMemberRepo;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.repository.JpaStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final JpaBoardRepository jpaBoardRepository;
    private final JpaMemberRepo jpaMemberRepo;
    private final JpaLikeMembersRepository jpaLikeMembersRepository;
    private final JpaStationRepository jpaStationRepository;
    private final JpaFileRepository jpaFileRepository;
    private final FileService fileService;
    private final FileHandler fileHandler;

    public Long getStation(Member member, BoardReqDto boardReqDto) throws Exception {
        if (boardReqDto.getStatId() != null) {
            ChargingStation chargingStation = jpaStationRepository.findByStatId(boardReqDto.getStatId());
            return jpaBoardRepository.save(boardReqDto.toEntities(member, chargingStation)).getId();
        }
        else {
            return jpaBoardRepository.save(boardReqDto.toEntity(member)).getId();
        }
    }

    public Long fileId(List<File> fileList, Board board) {
        if (!fileList.isEmpty()) {
            for (File file : fileList) {
                Long fileId = jpaFileRepository.save(file).getId();
                jpaFileRepository.updateboard(board, fileId);
            }
        }
        return null;
    }

    @Transactional
    public BoardResDto create(BoardReqDto boardReqDto, List<MultipartFile> files) throws Exception {
        Member member = jpaMemberRepo.findById(boardReqDto.getMemberId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.MemberIsNotExisted)) ;
        Long id = getStation(member, boardReqDto);
        List<File> fileList = fileHandler.parseFileInfo(files);
        Board board = jpaBoardRepository.getReferenceById(id);
        fileId(fileList, board);
        List<FileResDto> fileResDtos =
                fileService.findAllByBoard(id);
        // 게시글 첨부파일 id 담을 List 객체 생성
        List<Long> fileId = new ArrayList<>();
        BoardResDto boardResDto = new BoardResDto(board, fileId);
        return boardResDto;
    }

    @Transactional
    public BoardUpdateResDto update(Long id, BoardUpdateReqDto boardUpdateReqDto) {
        jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PageIsNotExisted));
        String updateTitle = boardUpdateReqDto.getTitle();
        String updateDescription = boardUpdateReqDto.getDescription();
        String updateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        if (updateTitle != null) {jpaBoardRepository.updateTitle(updateTitle, id);}
        if (updateDescription != null) {jpaBoardRepository.updateDescription(updateDescription, id);}
        jpaBoardRepository.updateUpdateDateTime(updateDateTime, id);
        Board board = jpaBoardRepository.getReferenceById(id);
        BoardUpdateResDto boardUpdateResDto = new BoardUpdateResDto(board);
        return boardUpdateResDto;
    }

    @Transactional
    public void delete(Long id) {
        Board board = jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PageIsNotExisted));
        jpaBoardRepository.delete(board);
    }

    public List<Board> findList() {
        List<Board> board = jpaBoardRepository.findAllByOrderByUpdateDateTimeDesc();
        return board;
    }

    @Transactional
    public BoardResDto findboardByBoardId(Long id, List<Long> fileId) {
        Board board = jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PageIsNotExisted));
        int visit = board.getVisit();
        int visitUp = (visit + 1);
        jpaBoardRepository.updateVisit(visitUp, id);
        Board boardRes = jpaBoardRepository.getReferenceById(id);
        BoardResDto boardResDto = new BoardResDto(boardRes, fileId);
        return boardResDto;
    }

    public List<Board> findboardByCategory(Category category) {
        List<Board> board = jpaBoardRepository.findByCategoryOrderByUpdateDateTimeDesc(category);
        return board;
    }

    @Transactional
    public BoardLikeResDto like(Long id, BoardLikeReqDto boardLikeReqDto) {
        jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PageIsNotExisted));
        Member member = boardLikeReqDto.getMember();
        Board board = boardLikeReqDto.getBoard();
        LikeMembers likeMembers = jpaLikeMembersRepository.findByBoard_idAndMember_id(id, member.getId());
        boolean check = (likeMembers != null);
        if (check == false){
            jpaLikeMembersRepository.save(boardLikeReqDto.toEntity(member, board));
        }
        else {
            jpaLikeMembersRepository.delete(likeMembers);
        }
        Long count = jpaLikeMembersRepository.findByBoard_id(id).stream().count();
        Integer likes = count.intValue();
        jpaBoardRepository.updateLikes(likes, id);
        Board boardRes = jpaBoardRepository.getReferenceById(id);
        BoardLikeResDto boardLikeResDto = new BoardLikeResDto(boardRes);
        return boardLikeResDto;
    }
}
