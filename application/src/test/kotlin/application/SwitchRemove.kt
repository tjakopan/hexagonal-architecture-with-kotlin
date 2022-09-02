package application

import application.ports.input.SwitchManagementInputPort
import application.ports.output.SwitchManagementOutputPort
import application.usecases.SwitchManagementUseCase
import domain.entity.EdgeRouter
import domain.entity.Switch
import domain.vo.*
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.maps.shouldNotContainKey
import io.mockk.mockk

class SwitchRemove {
  private val outputPort: SwitchManagementOutputPort = mockk(relaxed = true)
  private val useCase: SwitchManagementUseCase = SwitchManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)
  private val switch: Switch = Switch(
    Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490"),
    Vendor.CISCO,
    Model.XYZ0004,
    IP("20.0.0.100"),
    location,
    SwitchType.LAYER3
  )
  private val edgeRouter: EdgeRouter =
    EdgeRouter(Id(), Vendor.CISCO, Model.XYZ0002, IP("20.0.0.1"), location, mapOf(switch.id to switch))

  @Given("I know the switch I want to remove")
  fun `i know the switch I want to remove`() {
  }

  @And("The switch has no networks")
  fun `the switch has no networks`() {
    switch.networks.shouldBeEmpty()
  }

  @Then("I remove the switch from the edge router")
  fun `i remove the switch from the edge router`() {
    val newEdgeRouter = useCase.removeSwitchFromEdgeRouter(switch, edgeRouter)

    newEdgeRouter.switches shouldNotContainKey switch.id
  }
}