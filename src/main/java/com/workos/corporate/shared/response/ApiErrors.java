package com.workos.corporate.shared.response;

import jakarta.annotation.Nullable;

public record ApiErrors(String description, @Nullable String code) { }
