package com.example.charging_life.board;

import com.example.charging_life.board.dto.*;
import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.board.entity.Like;
import com.example.charging_life.file.FileService;
import com.example.charging_life.file.dto.FileResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor

@Tag(name = "Board API" , description = "<게시판 API>" + "\n\n" +
        "\uD83D\uDCCC 공지사항 " +  "\n\n" +
        "\uD83D\uDCCC 자유게시판 " +  "\n\n" +
        "\uD83D\uDCCC 충전게시판 " )
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    @Operation(summary = "게시판 글 작성", description = "성공하면 게시글이 Board 데이터베이스에 저장" + "\n\n"+
            "‼️form-data로 POST 내용은 boardReqDto로 묶어서 한번에 보내야함‼️"+ "\n\n"+
            "✅ category가 \"FREE\" or \"Notice\"일 때"  + "\n\n"+
            "\uD83D\uDCCC 내용" + "\n\n"+
            "Content-Disposition: form-data; name=\"boardReqDto\""  + "\n\n"+
            "Content-Type: application/json"  + "\n\n"+
            "{\n" + "\n\n"+
            "  \"title\" : \"title1\",\n" + "\n\n"+
            "  \"description\" : \"description1\",\n" + "\n\n"+
            "  \"memberId\" : 1,\n" + "\n\n"+
            "  \"category\" : \"FREE\"\n" + "\n\n"+
            "}\n"+ "\n\n"+
            "✅ category가 \"Station\"일 때"  + "\n\n"+
            "\uD83D\uDCCC 내용" + "\n\n"+
            "Content-Disposition: form-data; name=\"boardReqDto\""  + "\n\n"+
            "Content-Type: application/json"  + "\n\n"+
            "{\n" + "\n\n"+
            "  \"title\" : \"title1\",\n" + "\n\n"+
            "  \"description\" : \"description1\",\n" + "\n\n"+
            "  \"memberId\" : 1,\n" + "\n\n"+
            "  \"statId\" : \"ME000006\",\n"+"\n\n"+
            "  \"category\" : \"FREE\"\n" + "\n\n"+
            "}\n"+ "\n\n"+
            "✅ 파일 업로드 할 때 \"jpeg, png, pdf\"가능"  + "\n\n"+
            "\uD83D\uDCCC 내용" + "\n\n"+
            "Content-Disposition: form-data; name=\"boardReqDto\""  + "\n\n"+
            "Content-Type: application/json"  + "\n\n"+
            "{\n" + "\n\n"+
            "  \"title\" : \"title1\",\n" + "\n\n"+
            "  \"description\" : \"description1\",\n" + "\n\n"+
            "  \"memberId\" : 1,\n" + "\n\n"+
            "  \"statId\" : \"ME000006\",\n"+"\n\n"+
            "  \"category\" : \"FREE\"\n" + "\n\n"+
            "}\n"+ "\n\n"+
            "\uD83D\uDCCC jpeg" + "\n\n"+
            "Content-Disposition: form-data; name=\"file\""  + "\n\n"+
            "Content-Type: image/jpeg"  + "\n\n"+
            "파일 첨부"+ "\n\n"+
            "\uD83D\uDCCC png" + "\n\n"+
            "Content-Disposition: form-data; name=\"file\""  + "\n\n"+
            "Content-Type: image/png"  + "\n\n"+
            "파일 첨부"+ "\n\n"+
            "\uD83D\uDCCC pdf" + "\n\n"+
            "Content-Disposition: form-data; name=\"file\""  + "\n\n"+
            "Content-Type: application/pdf"  + "\n\n"+
            "파일 첨부"+ "\n\n")
    @PostMapping("/board")
    public ResponseEntity<BoardResDto> create(@RequestPart(value = "boardReqDto") BoardReqDto boardReqDto,
                              @RequestPart(value = "file", required = false) List<MultipartFile> files
    ) throws Exception {
        return ResponseEntity.ok(boardService.create(boardReqDto, files));
    }

    @Operation(summary = "게시글 리스트", description = "성공하면 Board 데이터베이스에 저장되어있는 모든 게시글 출력")
    @GetMapping("/board/list")
    public ResponseEntity<List<BoardDto>> getBoardList() throws IOException {
        return ResponseEntity.ok(boardService.findList());
    }

    @Operation(summary = "게시글 상세조회", description = "성공하면 Board 데이터베이스에 저장되어있는 id 값의 게시글 출력")
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResDto> getBoard(@PathVariable Long id) throws IOException {
        List<FileResDto> fileResDtos =
                fileService.findAllByBoard(id);
        // 게시글 첨부파일 id 담을 List 객체 생성
        List<Long> fileId = new ArrayList<>();
        // 각 첨부파일 id 추가
        for(FileResDto fileResDto : fileResDtos)
            fileId.add(fileResDto.getFileId());

        // 게시글 id와 첨부파일 id 목록 전달받아 결과 반환
        return ResponseEntity.ok(boardService.findboardByBoardId(id, fileId));
    }

    @Operation(summary = "게시글 수정", description = "성공하면 title과 description가 수정됨" + "\n\n" +
            "⭐️title 또는 desctiption 부분 수정 가능⭐️"+ "\n\n" +
            "{\n" + "\n\n" +
            "  \"title\" : \"title1\",\n" + "\n\n"+
            "  \"description\" : \"description1\",\n" + "\n\n"+
            "}\n"+ "\n\n")
    @PatchMapping("/board/{id}")
    public ResponseEntity<BoardUpdateResDto> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
        return ResponseEntity.ok(boardService.update(id, boardUpdateReqDto));
    }

    @Operation(summary = "게시글 삭제", description = "성공하면 Board 데이터베이스에 저장되어있는 id 값의 게시글 삭제")
    @DeleteMapping("/board/{id}")
    public void delete(@PathVariable Long id) {
        boardService.delete(id);
    }

    @Operation(summary = "카테고리별 게시글 출력", description = "성공하면 Board 데이터베이스에 저장되어있는 게시글을 카테고리 별 조회"+ "\n\n" +
    "\"http://115.85.181.24:8084/board?category=FREE\"")
    @GetMapping("/board")
    public ResponseEntity<List<Board>> getBoardByCategory(@RequestParam(value = "category", required = false) Category category) throws IOException {
        return ResponseEntity.ok(boardService.findboardByCategory(category));
    }

    @Operation(summary = "게시글 좋아요 등록 및 취소", description = "해당 게시글을 좋아요 누른 적이 없으면 해당 게시글에 좋아요 등록 및 좋아요 수 Up &"+ "\n\n" +
            " 해당 게시글을 좋아요 누른 적이 있으면 해당 게시글에 좋아요 취소 및 좋아요 수 Down")
    @PostMapping("/board/{id}/like")
    public ResponseEntity<BoardLikeResDto> likeBoard(@PathVariable Long id,
                                                     @RequestParam(name = "memberId") Long memberId,
                                                     @RequestParam(name = "like") Like like) {
        return ResponseEntity.ok(boardService.like(id, memberId, like));
    }

    @Operation(summary = "댓글 작성", description = "성공하면 해당 boardId에 댓글이 해당 Comment 데이터베이스에 저장" + "\n\n"+
            "\uD83D\uDCCC 내용" + "\n\n"+
            "{\n" + "\n\n"+
            "  \"comment\" : \"comment1\",\n" + "\n\n"+
            "  \"writer\" : 1,\n" + "\n\n"+
            "}\n"+ "\n\n")
    @PostMapping("/board/{board_id}/comment")
    public ResponseEntity<CommentResDto> createComment(@RequestPart(value = "commentReqDto") CommentReqDto commentReqDto,
                                                     @PathVariable(value = "board_id") Long id) throws Exception {
        return ResponseEntity.ok(boardService.createComment(commentReqDto, id));
    }

    @Operation(summary = "댓글 리스트", description = "성공하면 해당 boardId의 Comment 데이터베이스에 저장되어있는 모든 댓글 출력")
    @GetMapping("/board/{board_id}/comment")
    public ResponseEntity<List<CommentResDto>> getCommentList(@PathVariable(value = "board_id") Long id) throws IOException {
        return ResponseEntity.ok(boardService.findCommentList(id));
    }

    @Operation(summary = "댓글 상세조회", description = "성공하면 Comment 데이터베이스에 저장되어있는 id 값의 댓글 출력")
    @GetMapping("/board/{board_id}/comment/{comment_id}")
    public ResponseEntity<CommentResDto> getComment(@PathVariable(value = "comment_id") Long id) throws IOException {
        return ResponseEntity.ok(boardService.findComment(id));
    }

    @Operation(summary = "댓글 수정", description = "성공하면 comment가 수정됨" + "\n\n" +
            "⭐️comment 부분 수정 가능⭐️"+ "\n\n" +
            "http://115.85.181.24:8084/board/{board_id}/comment/{comment_id}?comment=comment2")
    @PatchMapping("/board/{board_id}/comment/{comment_id}")
    public ResponseEntity<CommentResDto> updateComment(@PathVariable(value = "comment_id") Long commentId,
                                                     @RequestParam String comment) {
        return ResponseEntity.ok(boardService.updateComment(commentId, comment));
    }

    @Operation(summary = "댓글 삭제", description = "성공하면 Comment 데이터베이스에 저장되어있는 id 값의 댓글 삭제")
    @DeleteMapping("/board/{board_id}/comment/{comment_id}")
    public void deleteComment(@PathVariable(value = "comment_id") Long commentId){
        boardService.deleteComment(commentId);
    }

    @Operation(summary = "댓글 좋아요 등록 및 취소", description = "해당 댓글을 좋아요 누른 적이 없으면 해당 댓글에 좋아요 등록 및 좋아요 수 Up &"+ "\n\n" +
            " 해당 댓글을 좋아요 누른 적이 있으면 해당 댓글에 좋아요 취소 및 좋아요 수 Down")
    @PostMapping("/board/{board_id}/comment/{comment_id}/like")
    public ResponseEntity<CommentResDto> likeComment(@PathVariable(value = "comment_id") Long commentId,
                                                   @RequestParam(name = "memberId") Long memberId,
                                                   @RequestParam(name = "like")Like like) {
        return ResponseEntity.ok(boardService.likeComment(commentId, memberId, like));
    }
}
