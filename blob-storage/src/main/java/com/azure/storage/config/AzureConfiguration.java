package com.azure.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.constants.StorageConstants;

@Configuration
public class AzureConfiguration {

	@Bean
	public BlobServiceClient buildBlobServiceClient() {
		return new BlobServiceClientBuilder().connectionString(StorageConstants.CONNECTION_STRING).buildClient();
	}
}
