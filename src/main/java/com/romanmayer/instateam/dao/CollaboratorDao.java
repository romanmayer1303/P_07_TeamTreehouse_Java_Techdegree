package com.romanmayer.instateam.dao;

import com.romanmayer.instateam.model.Collaborator;

import java.util.List;

/**
 * DAO (Data Access Object) Interface for the CollaboratorService model class
 */
public interface CollaboratorDao {
    List<Collaborator> findAll();

    Collaborator findById(Long id);

    void save(Collaborator collaborator);

    void delete(Collaborator collaborator);
}
