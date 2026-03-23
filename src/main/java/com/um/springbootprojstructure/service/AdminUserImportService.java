package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.dto.ImportUsersResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AdminUserImportService {
    ImportUsersResponse importLegacyUsersXml(MultipartFile file);
}
