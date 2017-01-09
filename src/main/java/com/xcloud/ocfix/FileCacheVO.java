package com.xcloud.ocfix;

public class FileCacheVO {

    private String userName;
    private Integer fileId;
    private Integer storage;
    private String path;
    private String pathHash;
    private Integer parent;
    private String name;
    private Integer mimeType;
    private String mimeTypeName;
    private Integer mimePart;
    private Long size;
    private Integer mtime;
    private Integer storageMtime;
    private Integer encrypted;
    private Long unencryptedSize;
    private String etag;
    private Integer permissions;
    private String checksum;
    private boolean ifExists;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathHash() {
        return pathHash;
    }

    public void setPathHash(String pathHash) {
        this.pathHash = pathHash;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMimeType() {
        return mimeType;
    }

    public void setMimeType(Integer mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeTypeName() {
        return mimeTypeName;
    }

    public void setMimeTypeName(String mimeTypeName) {
        this.mimeTypeName = mimeTypeName;
    }

    public Integer getMimePart() {
        return mimePart;
    }

    public void setMimePart(Integer minePart) {
        this.mimePart = minePart;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getMtime() {
        return mtime;
    }

    public void setMtime(Integer mtime) {
        this.mtime = mtime;
    }

    public Integer getStorageMtime() {
        return storageMtime;
    }

    public void setStorageMtime(Integer storageMtime) {
        this.storageMtime = storageMtime;
    }

    public Integer getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Integer encrypted) {
        this.encrypted = encrypted;
    }

    public Long getUnencryptedSize() {
        return unencryptedSize;
    }

    public void setUnencryptedSize(Long unencryptedSize) {
        this.unencryptedSize = unencryptedSize;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

}
