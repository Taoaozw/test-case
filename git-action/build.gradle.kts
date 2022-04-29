
@Suppress("DSL_SCOPE_VIOLATION")
plugins{
    alias(libs.plugins.spring)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.dependency)
}

dependencies{
    implementation(rootProject.libs.spring.boot.starter.web)
}

tasks.bootJar{
    this.archiveFileName.set("git-action.jar")
}
