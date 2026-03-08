package com.workos.corporate.presentation.mappers.user.impl;

import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.presentation.controllers.user.dto.request.CreateUserDetailsRequestDto;
import com.workos.corporate.presentation.controllers.user.dto.response.UserDetailsResponseDto;
import com.workos.corporate.presentation.mappers.user.UserDetailsMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapperImpl implements UserDetailsMapper {

    @Override
    public UserDetailsResponseDto toResponseDto(UserDetails userDetails) {
        return new UserDetailsResponseDto(
            userDetails.getId(),
            userDetails.getUserId(),
            userDetails.getEmailAddress(),
            userDetails.getFirstName(),
            userDetails.getMiddleName(),
            userDetails.getLastName(),
            userDetails.getPhoneNumber(),
            userDetails.getPhoneCountryCode()
        );
    }

    @Override
    public UserDetails toEntity(CreateUserDetailsRequestDto dto, String userId) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userId);
        userDetails.setEmailAddress(dto.emailAddress());
        userDetails.setFirstName(dto.firstName());
        userDetails.setMiddleName(dto.middleName());
        userDetails.setLastName(dto.lastName());
        userDetails.setPhoneNumber(dto.phoneNumber());
        userDetails.setPhoneCountryCode(dto.phoneCountryCode());
        return userDetails;
    }
}
