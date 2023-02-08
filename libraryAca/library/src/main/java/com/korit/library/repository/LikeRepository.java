package com.korit.library.repository;

import com.korit.library.entity.BookLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeRepository {
    public int addLike(BookLike bookLike);
    public int deleteLike(BookLike bookLike);
    public int getLikeStatus(BookLike bookLike);
    public int getLikeCount(int bookId);
}
