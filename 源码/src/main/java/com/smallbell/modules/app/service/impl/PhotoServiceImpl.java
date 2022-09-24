package com.smallbell.modules.app.service.impl;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.PhotoDao;
import com.smallbell.modules.app.service.PhotoService;
import com.smallbell.modules.app.utils.OssUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private PhotoDao photoDao;

    @Override
    public R photo(MultipartFile file, Long id) {

        String s = OssUtils.upLoad(file);
        if (s==null)
            return R.error("图片上传失败");
        photoDao.updatePhoto(s,id);


        return R.ok().put("url",s);
    }
}
