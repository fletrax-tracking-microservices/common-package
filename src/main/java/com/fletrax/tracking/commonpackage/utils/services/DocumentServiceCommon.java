package com.fletrax.tracking.commonpackage.utils.services;

import com.fletrax.tracking.commonpackage.client.DocumentClientCommon;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.UUID;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DocumentServiceCommon {

    private DocumentClientCommon documentClient;

    public ResponseEntity<Object> uploadDocument(MultipartFile file, String relatedEntityId, String relatedEntityType,
            String oldDocumentId) {
        return documentClient.uploadDocument(file, relatedEntityId, relatedEntityType, oldDocumentId);
    }

    public ResponseEntity<byte[]> fetchDocument(UUID id) {
        return documentClient.fetchDocument(id);
    }

    /**
     * Uploads a document and sets its ID to the specified field of the given model.
     *
     * @param file       the file to upload
     * @param model      the object whose field will be updated
     * @param fieldName  the name of the field to update
     * @param entityId   the ID of the entity associated with the file
     * @param entityType the TYPE of the entity associated with the file
     * @param repository the repository to save the updated model (optional, can be
     *                   null for DTOs)
     * @param <T>        the type of the model
     */
    public <T> T uploadAndSetField(MultipartFile file, T model, String fieldName,
            String entityId, String entityType) {
        // String entityId, String entityType, JpaRepository<T, ?> repository) {
        if (file != null && !file.isEmpty()) {
            try {
                // Access the old value of the field
                Field field = model.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                String oldDocumentId = (String) field.get(model);

                // Upload the file
                ResponseEntity<Object> response = uploadDocument(file, entityId, entityType, oldDocumentId);

                // System.out.println("response : " + response);

                if (response.getStatusCode().is2xxSuccessful()) {
                    String newDocumentId = (String) response.getBody();

                    // System.out.println("newDocumentId : " + newDocumentId);

                    // Set the new document ID to the field
                    field.set(model, newDocumentId);
                    // System.out.println("model : " + model);
                    // // Save the updated model if a repository is provided
                    // if (repository != null) {
                    // repository.save(model);
                    // }
                    // return newDocumentId;
                } else {
                    throw new RuntimeException("Failed to upload document: " + response.getBody());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error accessing or updating field: " + fieldName, e);
            }
        }
        return model;
    }

}