package com.xcloud.ocfix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FixUtils {

    @Autowired
    private FileCacheDao fileCacheDao;

    @Autowired
    private OssUtils ossUtils;

    public List<FileCacheVO> findFiles(Integer storage) {
        FileCacheVO cond = new FileCacheVO();
        cond.setStorage(storage);
        List<FileCacheVO> list = fileCacheDao.findByCondition(cond);
        for (FileCacheVO file : list) {
            file.setIfExists(true);
            String base = "data/";
            String user = file.getUserName() + "/";
            String path = file.getPath();
            String url = base + user + path;
            if (!ossUtils.findObject(url)) {
                file.setIfExists(false);
            }
        }
        return list;
    }

    public boolean fixit(FileCacheVO file) {
        String base = "data/";
        String user = file.getUserName() + "/";
        String path = file.getPath();
        String url = base + user + path;
        if (!ossUtils.findObject(url)) {
            fileCacheDao.insert(file);
            fileCacheDao.deleteById(file);
        }
        return false;
    }

}
