package com.romanmayer.instateam.service;

import com.romanmayer.instateam.model.Collaborator;

import java.util.List;

/**
 * Service Interface for the Collaborator model class
 */
public interface CollaboratorService {
    List<Collaborator> findAll();

    Collaborator findById(Long id);

    void save(Collaborator collaborator);

    void delete(Collaborator collaborator);
}
