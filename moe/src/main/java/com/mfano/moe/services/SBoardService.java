package com.mfano.moe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfano.moe.models.SBoard;
import com.mfano.moe.repositories.SBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SBoardService {
@Autowired
    private final SBoardRepository sBoardRepository;

    // Get All 
    public List<SBoard> findAll() {
        return sBoardRepository.findAll();
    }

    // Get  By Id
    public SBoard findById(Long id) {
        return sBoardRepository.findById(id).orElse(null);
    }

    // Get  By Id
    public SBoard findByInst(Long id) {
        return sBoardRepository.findById(id).orElse(null);
    }

    public SBoard findByInstitutionId(Long inst){
        return sBoardRepository.findByInstitutionId(inst).orElse(null);
    }

    public SBoard findByProfileId(Long pro){
        return sBoardRepository.findByProfileId(pro).orElse(null);
    }

    // Delete 
    public void delete(Long id) {
        sBoardRepository.deleteById(id);
    }

    // Update 
    public void save(SBoard board) {
        sBoardRepository.save(board);
    }
}
