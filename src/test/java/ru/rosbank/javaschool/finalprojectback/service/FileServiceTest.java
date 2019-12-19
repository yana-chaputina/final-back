package ru.rosbank.javaschool.finalprojectback.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import ru.rosbank.javaschool.finalprojectback.dto.UploadResponseDto;
import ru.rosbank.javaschool.finalprojectback.exception.FileStorageException;
import ru.rosbank.javaschool.finalprojectback.exception.UnsupportedFileTypeException;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;


class FileServiceTest {

    @Test
    void saveMultipartJpeg() {

        MultipartFile multipartFileMock = mock(MultipartFile.class);
        when(multipartFileMock.getContentType()).thenReturn("image/jpeg");
        String uploadPath = "";

        FileService fileService = new FileService(uploadPath);
        UploadResponseDto dto = fileService.save(multipartFileMock);
        assertTrue(dto.getName().endsWith(".jpg"));

    }

    @Test
    void saveMultipartPng() {

        MultipartFile multipartFileMock = mock(MultipartFile.class);
        when(multipartFileMock.getContentType()).thenReturn("image/png");
        String uploadPath = "";

        FileService fileService = new FileService(uploadPath);
        UploadResponseDto dto = fileService.save(multipartFileMock);
        assertTrue(dto.getName().endsWith(".png"));

    }

    @Test
    void saveMultipartWebm() {

        MultipartFile multipartFileMock = mock(MultipartFile.class);
        when(multipartFileMock.getContentType()).thenReturn("video/webm");
        String uploadPath = "";

        FileService fileService = new FileService(uploadPath);
        UploadResponseDto dto = fileService.save(multipartFileMock);
        assertTrue(dto.getName().endsWith(".webm"));

    }

    @Test
    void saveMultipartMpeg() {

        MultipartFile multipartFileMock = mock(MultipartFile.class);
        when(multipartFileMock.getContentType()).thenReturn("audio/mpeg");
        String uploadPath = "";

        FileService fileService = new FileService(uploadPath);
        UploadResponseDto dto = fileService.save(multipartFileMock);
        assertTrue(dto.getName().endsWith(".mpeg"));

    }

    @Test
    void saveMultipartThrowUnsupportedFileTypeException() {

        MultipartFile multipartFileMock = mock(MultipartFile.class);
        when(multipartFileMock.getContentType()).thenReturn("");
        String uploadPath = "";

        FileService fileService = new FileService(uploadPath);
        assertThrows(UnsupportedFileTypeException.class, () -> fileService.save(multipartFileMock));
    }

    @Test
    void saveMultipartThrowFileStorageException() throws IOException {

        MultipartFile multipartFileMock = mock(MultipartFile.class);
        when(multipartFileMock.getContentType()).thenReturn("image/jpeg");
        String uploadPath = "";
        doThrow(IOException.class).when(multipartFileMock).transferTo((Path) any());
        FileService fileService = new FileService(uploadPath);
        assertThrows(FileStorageException.class, () -> fileService.save(multipartFileMock));
    }


}