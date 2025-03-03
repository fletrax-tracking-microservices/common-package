package com.fletrax.tracking.commonpackage.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.fletrax.tracking.commonpackage.configuration.FeignClient.FeignClientConfig;

import feign.Headers;

@FeignClient(name = "documents-service", path = "/internal/documents", configuration = FeignClientConfig.class)
public interface DocumentClientCommon {

        @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Headers("Content-Type: multipart/form-data")
        ResponseEntity<Object> uploadDocument(
                        @RequestPart("file") MultipartFile file,
                        @RequestParam("relatedEntityId") String relatedEntityId,
                        @RequestParam("relatedEntityType") String relatedEntityType,
                        @RequestParam(value = "oldDocumentId", required = false) String oldDocumentId);

        @GetMapping("/{id}")
        public ResponseEntity<byte[]> fetchDocument(@PathVariable UUID id);

}
