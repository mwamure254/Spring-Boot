package com.mfano.moe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.moe.models.Board;
import com.mfano.moe.repositories.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;

    // Get All ILevels
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // Get ILevel By Id
    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // Delete ILevel
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // Update ILevel
    public void save(Board board) {
        boardRepository.save(board);
    }

    public Board findByProfileId(Long pid){
       return boardRepository.findByProfileId(pid);
    }

    public Board findByInstitutionId(Long inst){
        return boardRepository.findByInstitutionId(inst);
    }
}
