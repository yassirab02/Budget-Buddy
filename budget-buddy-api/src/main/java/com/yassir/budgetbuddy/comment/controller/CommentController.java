package com.yassir.budgetbuddy.comment.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
@Tag(name = "Comment")
public class CommentController {


}
