package com.ssafy.artchain.pieceowner.controller;

import com.ssafy.artchain.pieceowner.service.PieceOwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pieceowner")
@RequiredArgsConstructor
@Slf4j
public class PieceOwnerController {

    private final PieceOwnerService pieceOwnerService;

}
