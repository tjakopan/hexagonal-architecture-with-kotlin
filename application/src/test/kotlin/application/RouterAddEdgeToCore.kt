package application

import application.ports.input.RouterManagementInputPort
import application.ports.output.RouterManagementOutputPort
import application.usecases.RouterManagementUseCase
import application.usecases.createRouter
import domain.entity.CoreRouter
import domain.entity.EdgeRouter
import domain.vo.IP
import domain.vo.Location
import domain.vo.Model
import domain.vo.Vendor
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class RouterAddEdgeToCore {
  private val outputPort: RouterManagementOutputPort = mockk(relaxed = true)
  private val useCase: RouterManagementUseCase = RouterManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)

  private lateinit var edgeRouter: EdgeRouter
  private lateinit var coreRouter: CoreRouter

  @Given("I have an edge router")
  fun `assert edge router exists`() {
    edgeRouter = useCase.createRouter(Vendor.HP, Model.XYZ0004, IP("20.0.0.1"), location)
  }

  @And("I have a core router")
  fun `assert core router exists`() {
    coreRouter = useCase.createRouter(Vendor.CISCO, Model.XYZ0001, IP("30.0.0.1"), location)
  }

  @Then("I add an edge router to a core router")
  fun `add edge to core router`() {
    val newCoreRouter = useCase.addRouterToCoreRouter(edgeRouter, coreRouter)

    newCoreRouter.id shouldBe coreRouter.id
    newCoreRouter.routers shouldContain (edgeRouter.id to edgeRouter)
  }
}