package com.cabin.mapper.jpa;

import com.cabin.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 伍六七
 * @date 2023/8/3 11:23
 */
@Repository
public interface UploadMapper extends JpaRepository<Upload, Long> {
    Upload getUploadByMd5(String md5);
}
