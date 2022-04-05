package com.saieeash.downloadFile.service;

import com.saieeash.downloadFile.entity.Attachment;
import com.saieeash.downloadFile.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentServiceImp implements AttachmentService{

    @Autowired
    private AttachmentRepository attachmentRepository;


    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new Exception("FileName contains invalid  sequence of characters");
            }

            Attachment attachment = new Attachment(fileName,file.getContentType(),file.getBytes());

            return  attachmentRepository.save(attachment);

        }catch (Exception e){
            throw  new Exception("Could not save the file");
        }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(() -> new Exception("File not found with id + " + fileId));
    }
}
