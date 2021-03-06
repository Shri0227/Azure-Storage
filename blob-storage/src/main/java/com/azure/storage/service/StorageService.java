package com.azure.storage.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;

@Service
public class StorageService {

	@Autowired
	BlobServiceClient blobServiceClient;
	
	public ResponseEntity createContainer(String containerName) {
			BlobContainerClient blobContainerClient = blobServiceClient.createBlobContainer(containerName);
			return ResponseEntity.ok().body(containerName+" created");
	}
	
	public ResponseEntity uploadBlob(MultipartFile file, String containerName, String prefix) {
		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		String fileName = null;
		if(null != prefix) {
			fileName = prefix + file.getOriginalFilename();
		} else {
			fileName = file.getOriginalFilename();
		}

		BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
		try {
			blobClient.upload(file.getInputStream(), file.getSize());
			return ResponseEntity.ok().body(fileName+" uploaded to "+containerName);
		} catch (IOException e) {
			System.out.println("Exception occurred while uploading blob : "+e.getMessage());
		}
		return null;
	}
	
	public ResponseEntity listBlobs(String containerName) {
		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		List<String> blobLists = new ArrayList<String>();
		for(BlobItem blobItem : blobContainerClient.listBlobs()) {
			blobLists.add(blobItem.getName());
		}
		return ResponseEntity.accepted().body(blobLists);
	}
	
	public ResponseEntity deleteContainer(String containerName) {
		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		blobContainerClient.delete();
		return ResponseEntity.ok(containerName+" is deleted");
	}
	
	public ResponseEntity deleteBlob(String containerName, String blobName) {
		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		BlobClient blobClient =  blobContainerClient.getBlobClient(blobName);
		blobClient.delete();
		return ResponseEntity.ok(blobName+" deleted from container "+containerName);
	}
	
	public ResponseEntity<byte[]> downloadBlob(String containerName, String blobName) {
		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		BlobClient blobClient =  blobContainerClient.getBlobClient(blobName);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		blobClient.download(outStream);
		byte[] bytes = outStream.toByteArray();
		
		return ResponseEntity.ok().body(bytes);
	}
	
}
