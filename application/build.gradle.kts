plugins {
  id("hexagonal.architecture.with.kotlin.kotlin-library-conventions")
}

dependencies {
  api(project(":domain"))

  testImplementation(platform("io.cucumber:cucumber-bom:7.6.0"))
  testImplementation("io.cucumber:cucumber-java")
  testImplementation("io.cucumber:cucumber-junit-platform-engine")
  testImplementation("org.junit.platform:junit-platform-suite")
  testImplementation("io.mockk:mockk:1.12.7")
}

tasks.named<Test>("test") {
  // Workaround. Gradle does not include enough information to disambiguate between different examples and scenarios.
  systemProperty("cucumber.junit-platform.naming-strategy", "long")
  // Change this to false to display the Cucumber Reports banner
  systemProperty("cucumber.publish.quiet", "true")
}
