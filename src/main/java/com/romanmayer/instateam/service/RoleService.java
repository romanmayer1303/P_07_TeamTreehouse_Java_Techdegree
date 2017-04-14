package com.romanmayer.instateam.service;

import com.romanmayer.instateam.model.Role;

import java.util.List;

/**
 * Service Interface for the Role model class
 */
public interface RoleService {
    List<Role> findAll();
    Role findById(Long id);
    void save(Role role);
    void delete(Role role);
}
