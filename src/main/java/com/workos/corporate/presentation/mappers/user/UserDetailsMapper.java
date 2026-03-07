package com.workos.corporate.presentation.mappers.user;

import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.presentation.controllers.user.dto.request.CreateUserDetailsRequestDto;
import com.workos.corporate.presentation.controllers.user.dto.response.UserDetailsResponseDto;

public interface UserDetailsMapper {
    UserDetailsResponseDto toResponseDto(UserDetails userDetails);
    UserDetails toEntity(CreateUserDetailsRequestDto dto, String userId);
}
