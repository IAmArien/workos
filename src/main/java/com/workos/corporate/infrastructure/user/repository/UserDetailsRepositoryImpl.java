package com.workos.corporate.infrastructure.user.repository;

import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.domain.user.repository.UserDetailsRepository;
import com.workos.corporate.shared.exception.BadRequestException;
import com.workos.corporate.shared.exception.NotFoundException;
import com.workos.corporate.shared.exception.UnprocessableEntityException;
import com.workos.corporate.shared.response.ApiErrors;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDetailsRepositoryImpl implements UserDetailsRepository {

    private static final String CODE_EMAIL_ALREADY_EXISTS = "EMAE001";
    private static final String CODE_USER_ID_IS_MISSING = "UIDIM001";
    private static final String CODE_USER_ID_IS_NON_EXISTENT = "UIDINE001";
    private static final String CODE_EMAIL_IS_MISSING = "EMIM001";
    private static final String CODE_FIRST_NAME_IS_MISSING = "FNIM001";
    private static final String CODE_LAST_NAME_IS_MISSING = "LNIM001";
    private static final String CODE_PHONE_NUMBER_IS_MISSING = "PNIM001";
    private static final String CODE_PN_COUNTRY_CODE_IS_MISSING = "PNCCIM001";

    private final UserDetailsJpaRepository jpa;

    public UserDetailsRepositoryImpl(UserDetailsJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public UserDetails getUserDetailsById(String userId) {
        if (userId.trim().isEmpty()) {
            throw new BadRequestException(
                "Request parameter userId is missing",
                    CODE_USER_ID_IS_MISSING
            );
        }
        Optional<UserDetails> userDetails = this.jpa.findByUserId(userId);
        return userDetails.orElseThrow(() ->
            new NotFoundException(
                String.format("Unable to find user with an ID of: %s", userId),
                CODE_USER_ID_IS_NON_EXISTENT
            )
        );
    }

    @Override
    public void createUserDetails(UserDetails userDetails) {
        String emailAddress = userDetails.getEmailAddress();
        Optional<UserDetails> optionalUserDetails = this.jpa.findByEmailAddress(emailAddress);
        if (optionalUserDetails.isPresent()) {
            throw new BadRequestException(
                String.format("Email address for %s already exists", emailAddress),
                CODE_EMAIL_ALREADY_EXISTS
            );
        }
        if (isValidUserDetails(userDetails)) {
            this.jpa.save(userDetails);
        }
    }

    private boolean isValidUserDetails(UserDetails userDetails) {
        boolean hasError = false;
        UnprocessableEntityException exception =
            new UnprocessableEntityException("Required parameter/s is/are missing.", null);
        if (userDetails.getUserId().trim().isEmpty()) {
            exception.addError(new ApiErrors("Required parameter userId is missing", CODE_USER_ID_IS_MISSING));
            hasError = true;
        }
        if (userDetails.getEmailAddress().trim().isEmpty()) {
            exception.addError(
                new ApiErrors(
                    "Required parameter email_address is missing",
                    CODE_EMAIL_IS_MISSING
                )
            );
            hasError = true;
        }
        if (userDetails.getFirstName().trim().isEmpty()) {
            exception.addError(
                new ApiErrors(
                    "Required parameter first_name is missing",
                    CODE_FIRST_NAME_IS_MISSING
                )
            );
            hasError = true;
        }
        if (userDetails.getLastName().trim().isEmpty()) {
            exception.addError(
                new ApiErrors(
                    "Required parameter last_name is missing",
                    CODE_LAST_NAME_IS_MISSING
                )
            );
            hasError = true;
        }
        if (userDetails.getPhoneNumber().trim().isEmpty()) {
            exception.addError(
                new ApiErrors(
                    "Required parameter phone_number is missing",
                    CODE_PHONE_NUMBER_IS_MISSING
                )
            );
            hasError = true;
        }
        if (userDetails.getPhoneCountryCode().trim().isEmpty()) {
            exception.addError(
                new ApiErrors(
                    "Required parameter phone_country_code is missing",
                    CODE_PN_COUNTRY_CODE_IS_MISSING
                )
            );
            hasError = true;
        }
        if (hasError) throw exception;
        return true;
    }
}
