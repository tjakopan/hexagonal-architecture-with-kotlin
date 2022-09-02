plugins {
  id("hexagonal.architecture.with.kotlin.kotlin-library-conventions")
  id("org.jetbrains.kotlin.plugin.allopen") version "1.7.10"
  id("org.jetbrains.kotlin.plugin.noarg") version "1.7.10"
}

dependencies {
  implementation(project(":application"))
  implementation("com.h2database:h2:2.1.214")
  implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:3.0.3")
  implementation(platform("com.fasterxml.jackson:jackson-bom:2.13.3"))
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

val jpaAnnotations = listOf("jakarta.persistence.Entity", "jakarta.persistence.Embeddable")
allOpen {
  annotations(jpaAnnotations)
}
noArg {
  annotations(jpaAnnotations)
}