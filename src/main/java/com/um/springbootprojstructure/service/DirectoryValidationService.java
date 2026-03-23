package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.dto.ValidateDirectoryRequest;
import com.um.springbootprojstructure.dto.ValidateDirectoryResponse;

public interface DirectoryValidationService {
    ValidateDirectoryResponse validate(ValidateDirectoryRequest request);
}
