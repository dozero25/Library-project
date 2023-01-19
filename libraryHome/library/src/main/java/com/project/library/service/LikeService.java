package com.project.library.service;

import com.project.library.entity.BookLike;
import com.project.library.exception.CustomLikeException;
import com.project.library.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public void like(int bookId, int userId) {
        BookLike bookLike = BookLike.builder()
                .bookId(bookId)
                .userId(userId)
                .build();

        if (likeRepository.getLikeStatus(bookLike) > 0) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("likeError", "좋아요를 취소해주세요");

            throw new CustomLikeException(errorMap);
        }
        likeRepository.addLike(bookLike);
    }
    public void dislike(int bookId, int userId) {
        BookLike bookLike = BookLike.builder()
                .bookId(bookId)
                .userId(userId)
                .build();

        if (likeRepository.getLikeStatus(bookLike) == 0) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("likeError", "좋아요를 눌러주세요");

            throw new CustomLikeException(errorMap);
        }

        likeRepository.deleteLike(bookLike);
    }
}
