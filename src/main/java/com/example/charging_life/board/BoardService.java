package com.example.charging_life.board;

import com.example.charging_life.board.dto.*;
import com.example.charging_life.board.entity.*;
import com.example.charging_life.board.repository.JpaCommentLikeMembersRepository;
import com.example.charging_life.board.repository.JpaCommentRepository;
import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.file.FileHandler;
import com.example.charging_life.file.FileService;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final JpaBoardRepository jpaBoardRepository;
    private final JpaMemberRepo jpaMemberRepo;
    private final JpaLikeMembersRepository jpaLikeMembersRepository;
    private final JpaStationRepository jpaStationRepository;
    private final JpaFileRepository jpaFileRepository;
    private final JpaCommentRepository jpaCommentRepository;
    private final JpaCommentLikeMembersRepository jpaCommentLikeMembersRepository;
    private final FileService fileService;
    private final FileHandler fileHandler;

    public Long getStation(Member member, BoardReqDto boardReqDto) throws Exception {
        if (boardReqDto.getStatId() != null) {
            ChargingStation chargingStation = jpaStationRepository.findByStatId(boardReqDto.getStatId());
            return jpaBoardRepository.save(boardReqDto.toEntities(member, chargingStation)).getId();
        } else {
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
    public BoardResDto create(Member member, BoardReqDto boardReqDto, List<MultipartFile> files) throws Exception {
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
    public BoardUpdateResDto update(Long id, Member member, BoardUpdateReqDto boardUpdateReqDto) {
        Board oneBoard = jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PageDoesNotExist));
        if (oneBoard.getMember() == member) {
            String updateTitle = boardUpdateReqDto.getTitle();
            String updateDescription = boardUpdateReqDto.getDescription();
            String updateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
            if (updateTitle != null) {
                jpaBoardRepository.updateTitle(updateTitle, id);
            }
            if (updateDescription != null) {
                jpaBoardRepository.updateDescription(updateDescription, id);
            }
            jpaBoardRepository.updateUpdateDateTime(updateDateTime, id);
        }
        Board board = jpaBoardRepository.getReferenceById(id);
        BoardUpdateResDto boardUpdateResDto = new BoardUpdateResDto(board);
        return boardUpdateResDto;
    }

    @Transactional
    public void delete(Long id) {
        Board board = jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PageDoesNotExist));
        jpaBoardRepository.delete(board);
    }

    public List<BoardDto> findList() {
        List<Board> boards = jpaBoardRepository.findAllByOrderByUpdateDateTimeDesc();
        List<BoardDto> boardDtos = boards.stream().map(BoardDto::new).collect(Collectors.toList());
        return boardDtos;
    }

    @Transactional
    public BoardResDto findboardByBoardId(Long id, List<Long> fileId) {
        Board board = jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PageDoesNotExist));
        int visit = board.getVisit();
        int visitUp = (visit + 1);
        jpaBoardRepository.updateVisit(visitUp, id);
        Board boardRes = jpaBoardRepository.getReferenceById(id);
        BoardResDto boardResDto = new BoardResDto(boardRes, fileId);
        return boardResDto;
    }

    public List<BoardDto> findboardByCategory(Category category) {
        List<Board> board = jpaBoardRepository.findByCategoryOrderByUpdateDateTimeDesc(category);
        List<BoardDto> boardDtos = board.stream().map(BoardDto::new).collect(Collectors.toList());
        return boardDtos;
    }

    @Transactional
    public BoardLikeResDto like(Long id, Member member, Like like) {
        jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PageDoesNotExist));
        Board board = jpaBoardRepository.getReferenceById(id);
        System.out.println(jpaLikeMembersRepository.findByBoard_idAndMember_id(id, member.getId()));
        Optional<LikeMembers> likeMembers = jpaLikeMembersRepository.findByBoard_idAndMember_id(id, member.getId());
        if (like.getLike() == "LIKE") {
            if (!likeMembers.isPresent()) {
                LikeMembers likeMember = LikeMembers.builder()
                        .board(board)
                        .member(member)
                        .build();
                jpaLikeMembersRepository.save(likeMember);
            }
        } else {
            if (likeMembers.isPresent()) {
                Long deleteId = jpaLikeMembersRepository.findByBoard_idAndMember_id(id, member.getId()).get().getId();
                jpaLikeMembersRepository.deleteById(deleteId);}
        }
        Long count = jpaLikeMembersRepository.findByBoard_id(id).stream().count();
        Integer likes = count.intValue();
        jpaBoardRepository.updateLikes(likes, id);
        Board boardRes = jpaBoardRepository.getReferenceById(id);
        BoardLikeResDto boardLikeResDto = new BoardLikeResDto(boardRes);
        return boardLikeResDto;
    }

    @Transactional
    public CommentResDto createComment(Member member, CommentReqDto commentReqDto, Long boardId) {
        String contents = commentReqDto.getComment();
        Board board = jpaBoardRepository.findById(boardId).orElseThrow(() -> new CustomException(ExceptionEnum.PageDoesNotExist));

        Comment comment = Comment.builder()
                .comment(contents)
                .member(member)
                .board(board)
                .build();

        Comment newComment = jpaCommentRepository.save(comment);
        CommentResDto commentResDto = new CommentResDto(newComment);

        return commentResDto;

    }

    public List<CommentLikeResDto> findCommentList(Long boardId) {
        List<Comment> comments = jpaCommentRepository.findByBoardIdOrderByUpdateDateTimeDesc(boardId);
        List<CommentLikeResDto> commentLikeResDtos = comments.stream().map(CommentLikeResDto::new).collect(Collectors.toList());
        return commentLikeResDtos;
    }

    @Transactional
    public CommentResDto updateComment(Member member, Long commentId, String comment) {
        Comment oneComment = jpaCommentRepository.findById(commentId).orElseThrow(() -> new CustomException(ExceptionEnum.CommentDoesNotExist));
        if (oneComment.getMember() == member){
            String updateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
            jpaCommentRepository.updateComment(comment, commentId);
            jpaCommentRepository.updateUpdateDateTime(updateDateTime, commentId);
        }
        Comment updatecomment = jpaCommentRepository.getReferenceById(commentId);
        CommentResDto commentResDto = new CommentResDto(updatecomment);
        return commentResDto;
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = jpaCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CommentDoesNotExist));
        jpaCommentRepository.delete(comment);
    }

    @Transactional
    public CommentLikeResDto likeComment(Long commentId, Member member, Like like) {
        Comment comment = jpaCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CommentDoesNotExist));
        Optional<CommentLikeMembers> commentLikeMembers = jpaCommentLikeMembersRepository.findByComment_idAndMember_id(commentId, member.getId());
        if (like.getLike() == "LIKE"){
            if (!commentLikeMembers.isPresent()) {
                CommentLikeMembers commentLikeMember = CommentLikeMembers.builder()
                        .comment(comment)
                        .member(member)
                        .build();
                jpaCommentLikeMembersRepository.save(commentLikeMember);
            }
        }
        else {
            if (commentLikeMembers.isPresent()) {
                Long deleteId = jpaCommentLikeMembersRepository.findByComment_idAndMember_id(commentId, member.getId()).get().getId();
                jpaCommentLikeMembersRepository.deleteById(deleteId);
            }
        }
        Long count = jpaCommentLikeMembersRepository.findByComment_id(commentId).stream().count();
        Integer likes = count.intValue();
        jpaCommentRepository.updateLikes(likes, commentId);
        Comment commentRes = jpaCommentRepository.getReferenceById(commentId);
        CommentLikeResDto commentLikeResDto = new CommentLikeResDto(commentRes);
        return commentLikeResDto;
    }

    public CommentLikeResDto findComment(Long id) {
        Comment commentRes = jpaCommentRepository.getReferenceById(id);
        CommentLikeResDto commentLikeResDto = new CommentLikeResDto(commentRes);
        return commentLikeResDto;
    }

    public String checkLike(Member member, Long boardId, Long commentId) {
        if (commentId != null) {
            if (jpaCommentLikeMembersRepository.findByComment_idAndMember_id(commentId, member.getId()).isPresent()){ return "PRESENT";}
            else {
                return "PASS";
            }
        }
        else {
            if (jpaLikeMembersRepository.findByBoard_idAndMember_id(boardId, member.getId()).isPresent()){return "PRESENT";}
            else{
                return "PASS";
            }
        }
    }
}
