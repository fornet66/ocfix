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

    public List<FileCacheVO> findFiles(String userName, Integer storage) {
        FileCacheVO cond = new FileCacheVO();
        cond.setUserName(userName);
        cond.setStorage(storage);
        List<FileCacheVO> list = fileCacheDao.findByCondition(cond);
        boolean foundone = false;
        for (FileCacheVO file : list) {
            file.setIfExists(true);
            String base = "data/";
            String user = userName + "/";
            String path = file.getPath();
            if (file.getMimeType().intValue() == 2) {
                path = path + "/";
            }
            String url = base + user + path;
            if (!ossUtils.findObject(url)) {
                file.setIfExists(false);
            } else {
                foundone = true;
            }
        }
        if (foundone) {
            return list;
        }
        return null;
    }

    public boolean fixit(FileCacheVO file) {
        String base = "data/";
        String user = file.getUserName() + "/";
        String path = file.getPath();
        if (file.getMimeType().intValue() == 2) {
            path = path + "/";
        }
        String url = base + user + path;
        if (!ossUtils.findObject(url)) {
            System.out.println("can't find " + url);
            // fileCacheDao.insert(file);
            // fileCacheDao.deleteById(file);
            return true;
        }
        return false;
    }

}
