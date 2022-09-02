package application

import application.ports.input.RouterManagementInputPort
import application.ports.output.RouterManagementOutputPort
import application.usecases.RouterManagementUseCase
import domain.entity.CoreRouter
import domain.entity.Router
import domain.vo.*
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.maps.shouldNotContainKey
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.mockk


class RouterRemoveCoreFromCore {
  private val outputPort: RouterManagementOutputPort = mockk(relaxed = true)
  private val useCase: RouterManagementUseCase = RouterManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)

  private val anotherCoreRouter: CoreRouter =
    CoreRouter(Id("81579b05-4b4e-4b9b-91f4-75a5a8561296"), Vendor.HP, Model.XYZ0001, IP("10.1.0.1"), location)
  private val coreRouter: CoreRouter =
    CoreRouter(
      Id(),
      Vendor.HP,
      Model.XYZ0001,
      IP("10.0.0.1"),
      location,
      mapOf(anotherCoreRouter.id to anotherCoreRouter)
    )

  @Given("The core router has at least one core router connected to it")
  fun `the core router has at least one core router connected to it`() {
    val router = coreRouter.routers
      .values.firstOrNull(Router.typePredicate<CoreRouter>())

    router.shouldBeTypeOf<CoreRouter>()
    router shouldBe anotherCoreRouter
  }

  @And("The core router has no other routers connected to it")
  fun `the core router has no other routers connected to it`() {
    anotherCoreRouter.routers.shouldBeEmpty()
  }

  @Then("I remove the core router from another core router")
  fun `core router is removed from core router`() {
    val newCoreRouter = useCase.removeRouterFromCoreRouter(anotherCoreRouter, coreRouter)

    newCoreRouter.id shouldBe coreRouter.id
    newCoreRouter.routers shouldNotContainKey anotherCoreRouter.id
  }
}