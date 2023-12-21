package com.firstapi.serviceimpl;

import com.firstapi.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FIleUploadServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        {  String name = file.getOriginalFilename();

        String filePath = path+ File.separator+name;

        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

            Files.copy(file.getInputStream(), Paths.get(name));
        return name;
    }

}

    @Override
    public InputStream getResource(String path, String fileName) throws FileAlreadyExistsException {
        return null;
    }
}
