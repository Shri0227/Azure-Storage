package com.azure.storage.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.azure.storage.blob.models.BlobStorageException;


@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler
	public BlobStorageException handleBlobStorageException(BlobStorageException exception) {
		return exception;
	}
}
