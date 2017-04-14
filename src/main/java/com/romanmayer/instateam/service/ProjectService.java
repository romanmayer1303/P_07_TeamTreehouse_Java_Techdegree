package com.romanmayer.instateam.service;

import com.romanmayer.instateam.model.Project;

import java.util.List;

/**
 * Service Interface for the Project model class
 */
public interface ProjectService {
    List<Project> findAll();

    Project findById(Long id);

    void save(Project project);

    void delete(Project project);
}
