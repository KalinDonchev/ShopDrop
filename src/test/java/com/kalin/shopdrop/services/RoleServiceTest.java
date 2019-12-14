package com.kalin.shopdrop.services;

import com.kalin.shopdrop.TestBase;
import com.kalin.shopdrop.data.models.Role;
import com.kalin.shopdrop.data.repositories.RoleRepository;
import com.kalin.shopdrop.service.models.RoleServiceModel;
import com.kalin.shopdrop.service.services.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RoleServiceTest extends TestBase {

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Test
    public void seedRolesInDb_whenRepositoryIsEmpty_shouldSeedAllRoles() {

        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        roles.add(new Role());
        roles.add(new Role());
        roles.add(new Role());

        when(roleRepository.count()).thenReturn(0L);

        roleService.seedRolesInDb();

        assertEquals(4, roles.size());
    }

    @Test
    public void seedRolesInDb_whenRepositoryNotEmpty_shouldNotSeedRoles() {

        List<Role> roles = new ArrayList<>();

        when(roleRepository.count()).thenReturn(4L);

        roleService.seedRolesInDb();

        assertEquals(0, roles.size());
    }
 // ==================
    @Test
    public void findAllRoles_whenAnyRolesInDb_shouldReturnAllCorrect() {

        List<Role> roles;
        roles = Arrays.asList(new Role("ROLE_ROOT"), new Role("ROLE_ADMIN"), new Role("ROLE_USER"));
        when(roleRepository.findAll()).thenReturn(roles);

        Set<RoleServiceModel> roleServiceModels = roleService.findAllRoles();

        assertEquals(roles.size(), roleServiceModels.size());
    }

    @Test
    public void findAllRoles_whenNoRolesInDb_shouldReturnEmptySet(){

        List<Role> roles = new ArrayList<>();

        when(roleRepository.findAll()).thenReturn(roles);

        Set<RoleServiceModel> roleServiceModels = roleService.findAllRoles();

        assertEquals(0, roleServiceModels.size());
    }

    @Test
    public void findByAuthority_whenAuthorityExist_shouldReturnCorrectRole(){

        when(roleRepository.findByAuthority("ROLE_ROOT"))
                .thenReturn((new Role("ROLE_ROOT")));

        RoleServiceModel roleServiceModel = roleService.findByAuthority("ROLE_ROOT");

        assertEquals("ROLE_ROOT", roleServiceModel.getAuthority());
    }

    @Test
    public void findByAuthority_whenAuthorityNotExist_shouldThrowException() {

        when(roleRepository.findByAuthority("ROLE_ROOT"))
                .thenReturn(null);

        assertThrows(Exception.class, () -> roleService.findByAuthority("ROLE_ROOT"));
    }


}
