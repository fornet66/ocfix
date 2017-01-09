package com.xcloud.ocfix;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FileCacheMapper implements RowMapper<FileCacheVO> {

    @Override
    public FileCacheVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        FileCacheVO file = new FileCacheVO();
        file.setFileId(rs.getInt("fileid"));
        file.setStorage(rs.getInt("storage"));
        file.setPath(rs.getString("path"));
        file.setPathHash(rs.getString("path_hash"));
        file.setParent(rs.getInt("parent"));
        file.setName(rs.getString("name"));
        file.setMimeType(rs.getInt("mimetype"));
        file.setMimeTypeName(rs.getString("mimetype_name"));
        file.setMimePart(rs.getInt("mimepart"));
        file.setSize(rs.getLong("size"));
        file.setMtime(rs.getInt("mtime"));
        file.setStorageMtime(rs.getInt("storage_mtime"));
        file.setEncrypted(rs.getInt("encrypted"));
        file.setUnencryptedSize(rs.getLong("unencrypted_size"));
        file.setEtag(rs.getString("etag"));
        file.setPermissions(rs.getInt("permissions"));
        file.setChecksum(rs.getString("checksum"));
        return file;
    }

}
