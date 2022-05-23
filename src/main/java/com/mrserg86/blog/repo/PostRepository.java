package com.mrserg86.blog.repo;

import com.mrserg86.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
