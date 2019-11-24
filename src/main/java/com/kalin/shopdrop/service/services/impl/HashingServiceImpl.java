package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.service.services.HashingService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class HashingServiceImpl implements HashingService {
    @Override
    public String hash(String string) {
        return DigestUtils.sha256Hex(string);
    }
}
