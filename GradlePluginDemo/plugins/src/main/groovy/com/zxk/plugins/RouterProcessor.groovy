package com.zxk.plugins

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterProcessor implements Plugin<Project>{
    @Override
    void apply(Project project) {
        project.extensions.create(AppConstants.ROUTER_CONFIG,RouterConfig)

        def android=project.extensions.getByType(AppExtension)
        android.applicationVariants.all{variant->
            println("variant=${variant.variantData}")
            def config=project.extensions.getByName(AppConstants.ROUTER_CONFIG)
            println("config=${config.version}")
        }
    }
}

class RouterConfig{
    def version=""
}