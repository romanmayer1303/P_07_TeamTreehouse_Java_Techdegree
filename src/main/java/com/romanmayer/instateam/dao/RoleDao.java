package com.romanmayer.instateam.dao;

import com.romanmayer.instateam.model.Role;

import java.util.List;

/**
 * DAO (Data Access Object) Interface for the Role model class
 */
public interface RoleDao {
    List<Role> findAll();
    Role findById(Long id);
    void save(Role role);
    void delete(Role role);
}
