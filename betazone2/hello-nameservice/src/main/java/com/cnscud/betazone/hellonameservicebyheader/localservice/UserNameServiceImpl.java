package com.cnscud.betazone.hellonameservicebyheader.localservice;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Local Implement.
 *
 * @author Felix Zhang 2021-06-04 10:17
 * @version 1.0.0
 */
@Service
public class UserNameServiceImpl implements UserNameService {

    final static String defaultName = "guest";
    static Map<Integer, String> names = new HashMap<>();

    static
    {
        names.put(1, "Felix");
        names.put(2, "World");
        names.put(3, "Sea");
        names.put(4, "Sky");
        names.put(5, "Mountain");
    }

    @Override
    public String readName(int id) {
        return names.get(id) == null? defaultName: names.get(id);
    }

    private Map<Integer, String> readNames() {
        return names;
    }
}
