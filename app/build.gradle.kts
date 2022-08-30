plugins {
  id("hexagonal.architecture.with.kotlin.kotlin-application-conventions")
}

dependencies {
  implementation(project(":utilities"))
}

application {
  mainClass.set("hexagonal.architecture.with.kotlin.app.AppKt")
}
