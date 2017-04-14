package com.romanmayer.instateam.dao;

import com.romanmayer.instateam.model.Project;

import java.util.List;

/**
 * DAO (Data Access Object) Interface for the Project model class
 */
public interface ProjectDao {
    List<Project> findAll();

    Project findById(Long id);

    void save(Project project);

    void delete(Project project);
}
