package application

import application.ports.input.RouterManagementInputPort
import application.ports.output.RouterManagementOutputPort
import application.usecases.RouterManagementUseCase
import application.usecases.createRouter
import domain.entity.EdgeRouter
import domain.vo.IP
import domain.vo.Location
import domain.vo.Model
import domain.vo.Vendor
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.mockk

class RouterCreateEdge {
  private val outputPort: RouterManagementOutputPort = mockk(relaxed = true)
  private val useCase: RouterManagementUseCase = RouterManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)

  private lateinit var router: EdgeRouter

  @Given("I provide all required data to create an edge router")
  fun `create edge router`() {
    router = useCase.createRouter(Vendor.HP, Model.XYZ0004, IP("30.0.0.1"), location)
  }

  @Then("A new edge router is created")
  fun `a new edge router is created`() {
    this::router.isInitialized.shouldBeTrue()
  }
}