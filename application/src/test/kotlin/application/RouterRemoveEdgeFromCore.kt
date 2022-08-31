package application

import application.ports.input.RouterManagementInputPort
import application.ports.output.RouterManagementOutputPort
import application.usecases.RouterManagementUseCase
import domain.entity.CoreRouter
import domain.entity.EdgeRouter
import domain.entity.Router
import domain.entity.Switch
import domain.vo.*
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.assertions.withClue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.mockk


class RouterRemoveEdgeFromCore {
  private val outputPort: RouterManagementOutputPort = mockk(relaxed = true)
  private val useCase: RouterManagementUseCase = RouterManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)

  private val network: Network = Network(IP("20.0.0.0"), "TestNetwork", 8)

  private val switch: Switch = Switch(
    Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490"),
    Vendor.CISCO,
    Model.XYZ0004,
    IP("20.0.0.100"),
    location,
    SwitchType.LAYER3,
    listOf(network)
  )

  private val edgeRouter: EdgeRouter =
    EdgeRouter(Id(), Vendor.CISCO, Model.XYZ0002, IP("20.0.0.1"), location, mapOf(switch.id to switch))

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
    val networksSizeBefore = switch.networks.size

    switch.removeNetworkFromSwitch(network)

    networksSizeBefore shouldBe 1
    switch.networks.shouldBeEmpty()
  }

  @And("The edge router has no switches attached to it")
  fun `the edge router has no switches attached to it`() {
    val switchesSizeBefore = edgeRouter.switches.size

    edgeRouter.removeSwitch(switch)

    switchesSizeBefore shouldBe 1
    edgeRouter.switches.shouldBeEmpty()
  }

  @Then("I remove the edge router from the core router")
  fun `edge router is removed from core router`() {
    val expectedId = edgeRouter.id

    val actualId = useCase.removeRouterFromCoreRouter(edgeRouter, coreRouter)?.id

    withClue("Actual id should be present") {
      actualId shouldNotBe null
    }
    actualId shouldBe expectedId
  }
}