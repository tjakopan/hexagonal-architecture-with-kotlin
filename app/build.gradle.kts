plugins {
  id("hexagonal.architecture.with.kotlin.kotlin-application-conventions")
}

dependencies {
  implementation(project(":application"))
}

application {
  mainClass.set("hexagonal.architecture.with.kotlin.app.AppKt")
}
