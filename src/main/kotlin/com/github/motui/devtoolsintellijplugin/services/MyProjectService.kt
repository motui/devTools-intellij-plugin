package com.github.motui.devtoolsintellijplugin.services

import com.github.motui.devtoolsintellijplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
