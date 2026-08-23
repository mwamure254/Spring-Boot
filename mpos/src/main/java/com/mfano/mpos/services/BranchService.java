package com.mfano.mpos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfano.mpos.models.Branch;
import com.mfano.mpos.repositories.BranchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    public void createBranch(Branch branch) {
        branchRepository.save(branch);
    }

    public Branch getBranchById(Long id) {
        return branchRepository.findById(id).orElse(null);
    }

    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }

    public void updateBranch(Long id, Branch updatedBranch) {
        Branch existingBranch = branchRepository.findById(id).orElse(null);
        if (existingBranch != null) {
            existingBranch.setName(updatedBranch.getName());
            existingBranch.setLocation(updatedBranch.getLocation());
            branchRepository.save(existingBranch);
        }
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

}
