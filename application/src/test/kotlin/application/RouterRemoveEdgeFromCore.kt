package application

import application.ports.input.RouterManagementInputPort
import application.ports.output.RouterManagementOutputPort
import application.usecases.RouterManagementUseCase
import domain.entity.CoreRouter
import domain.entity.EdgeRouter
import domain.entity.Router
import domain.vo.*
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.maps.shouldNotContainKey
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.mockk


class RouterRemoveEdgeFromCore {
  private val outputPort: RouterManagementOutputPort = mockk(relaxed = true)
  private val useCase: RouterManagementUseCase = RouterManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)

  private val edgeRouter: EdgeRouter =
    EdgeRouter(Id(), Vendor.CISCO, Model.XYZ0002, IP("20.0.0.1"), location)

  private val coreRouter: CoreRouter =
    CoreRouter(Id(), Vendor.HP, Model.XYZ0001, IP("10.0.0.1"), location, mapOf(edgeRouter.id to edgeRouter))

  @Given("The core router has at least one edge router connected to it")
  fun `the core router has at least one edge router connected to it`() {
    val router = coreRouter.routers
      .values.firstOrNull(Router.typePredicate<EdgeRouter>())

    router.shouldBeTypeOf<EdgeRouter>()
    router shouldBe edgeRouter
  }

  @And("The switch has no networks attached to it")
  fun `the switch has no networks attached to id`() {
    edgeRouter.switches.values.flatMap { it.networks }.shouldBeEmpty()
  }

  @And("The edge router has no switches attached to it")
  fun `the edge router has no switches attached to it`() {
    edgeRouter.switches.shouldBeEmpty()
  }

  @Then("I remove the edge router from the core router")
  fun `edge router is removed from core router`() {
    val newCoreRouter = useCase.removeRouterFromCoreRouter(edgeRouter, coreRouter)

    newCoreRouter.id shouldBe coreRouter.id
    newCoreRouter.routers.shouldNotContainKey(edgeRouter.id)
  }
}