package com.example.sista.BAP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // menambahkan resource handler untuk folder bap-uploads agar file di folder ini
        // bisa diakses melalui url
        registry.addResourceHandler("/bap-uploads/**")
                // menentukan lokasi fisik folder bap-uploads yang ada di sistem file
                .addResourceLocations("file:/D:/angie/unpar/sem 5/rpl/tubesLocal/sistaSpring/bap-uploads");
        // catatan: path ini perlu diubah setiap kali pindah device

        // menambahkan resource handler untuk folder static agar file di dalam folder
        registry.addResourceHandler("/static/**")
                // menentukan lokasi folder static yang berada di dalam classpath aplikasi
                .addResourceLocations("classpath:/static/");
    }
}
