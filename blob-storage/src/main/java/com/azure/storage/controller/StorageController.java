package com.azure.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.service.StorageService;

@RestController
@RequestMapping("/storage")
public class StorageController {
	@Autowired
	StorageService storageService;
	
	@PostMapping("/container/create/{container_name}")
	public ResponseEntity createContainer(@PathVariable("container_name") String containerName) {
		return storageService.createContainer(containerName);
	}
	@PostMapping("/{container_name}/blob/upload")
	public ResponseEntity uploadBlob(@RequestParam("file") MultipartFile file,
			@PathVariable("container_name") String containerName, 
			@RequestHeader("prefix") String prefix) {
		return storageService.uploadBlob(file, containerName, prefix);
	}
	
	@GetMapping("/{container_name}/blob/lists")
	public ResponseEntity listBlobs(@PathVariable("container_name") String containerName) {
		return storageService.listBlobs(containerName);
	}
	
	@DeleteMapping("/delete/{containerName}")
	public ResponseEntity deleteContainer(@PathVariable("containerName") String containerName) {
		return storageService.deleteContainer(containerName);
	}
	
	@DeleteMapping("/delete/{containerName}/blob/{blobName}")
	public ResponseEntity deleteBlob(@PathVariable("containerName") String containerName,
			@PathVariable("blobName") String blobName) {
		return storageService.deleteBlob(containerName, blobName);
	}
	
	@GetMapping("/download/{containerName}/{blobName}")
	public ResponseEntity downloadBlob(@PathVariable("containerName") String containerName,
			@PathVariable("blobName") String blobName) {
		return storageService.downloadBlob(containerName, blobName);
	}
}
