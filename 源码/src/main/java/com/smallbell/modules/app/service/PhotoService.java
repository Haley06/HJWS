package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    R photo(MultipartFile file, Long id);
}
