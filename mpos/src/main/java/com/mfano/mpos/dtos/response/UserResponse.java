package com.mfano.mpos.dtos.response;

import com.mfano.mpos.models.security.Role;

public record UserResponse(Long id, String username, Role role, Long branchId) {}
