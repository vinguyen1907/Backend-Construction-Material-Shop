package com.example.cmsbe.services.generators;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class Generator {
    public abstract void export(HttpServletResponse response) throws IOException;
}
