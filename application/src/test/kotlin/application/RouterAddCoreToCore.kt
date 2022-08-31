package application

import application.ports.input.RouterManagementInputPort
import application.ports.output.RouterManagementOutputPort
import application.usecases.RouterManagementUseCase
import application.usecases.createRouter
import domain.entity.CoreRouter
import domain.vo.IP
import domain.vo.Location
import domain.vo.Model
import domain.vo.Vendor
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class RouterAddCoreToCore {
  private val outputPort: RouterManagementOutputPort = mockk(relaxed = true)
  private val useCase: RouterManagementUseCase = RouterManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)

  private lateinit var coreRouter: CoreRouter
  private lateinit var anotherCoreRouter: CoreRouter

  @Given("I have this core router")
  fun `assert this core router exists`() {
    coreRouter = useCase.createRouter(Vendor.CISCO, Model.XYZ0001, IP("30.0.0.1"), location)
  }

  @And("I have another core router")
  fun `assert another core router exists`() {
    anotherCoreRouter = useCase.createRouter(Vendor.CISCO, Model.XYZ0001, IP("40.0.0.1"), location)
  }

  @Then("I add a core router to another core router")
  fun `add core to core router`() {
    val expectedCoreId = coreRouter.id

    val routerWithCore = useCase.addRouterToCoreRouter(coreRouter, anotherCoreRouter)

    val actualCoreId = routerWithCore.routers[expectedCoreId]?.id
    actualCoreId shouldBe expectedCoreId
  }
}