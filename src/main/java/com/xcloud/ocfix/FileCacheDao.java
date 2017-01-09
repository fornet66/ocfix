package com.xcloud.ocfix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class FileCacheDao implements IBaseDao<FileCacheVO> {

    private Logger logger = LoggerFactory.getLogger(FileCacheDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(FileCacheVO t) {
        String sql = "insert into oc_filecache_bak () values ()";
        return jdbcTemplate.update(sql, new Object[] {});
    }

    @Override
    public int deleteById(FileCacheVO t) {
        String sql = "delete from oc_filecache where fileid=?";
        return jdbcTemplate.update(sql, new Object[] { t.getFileId() });
    }

    @Override
    public int updateById(FileCacheVO t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public FileCacheVO findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<FileCacheVO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<FileCacheVO> findByCondition(FileCacheVO cond) {
        String sql = "select fileid, storage, path, path_hash, parent, name, a.mimetype, "
                + "b.mimetype as mimetype_name, mimepart, size, mtime, storage_mtime, encrypted, "
                + "unencrypted_size, etag, permissions, checksum from oc_filecache a, oc_mimetypes b "
                + "where storage=? and parent != -1 and a.mimetype=b.id order by path";
        try {
            return jdbcTemplate.query(sql, new Object[] { cond.getStorage() }, new FileCacheMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.error("not find record in oc_filecache");
            return null;
        }
    }

}
