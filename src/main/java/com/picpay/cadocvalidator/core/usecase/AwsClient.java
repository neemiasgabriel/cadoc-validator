package com.picpay.cadocvalidator.core.usecase;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface AwsClient {
  InputStream downloadFile(final String filePath, final String objectKey);
  boolean uploadFile(final String fileName, final String filePath);
  boolean uploadFile(final MultipartFile multipartFile);
}
