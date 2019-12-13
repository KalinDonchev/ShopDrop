package com.kalin.shopdrop.service.services;

import java.util.List;

public interface AuthenticatedUserService {
    String getUsername();

    List<String> getRoles();
}
