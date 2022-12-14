package application

import application.ports.input.SwitchManagementInputPort
import application.ports.output.SwitchManagementOutputPort
import application.usecases.SwitchManagementUseCase
import domain.entity.EdgeRouter
import domain.entity.Switch
import domain.vo.*
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.maps.shouldContain
import io.mockk.mockk

class SwitchAdd {
  private val outputPort: SwitchManagementOutputPort = mockk(relaxed = true)
  private val useCase: SwitchManagementUseCase = SwitchManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)
  private val edgeRouter: EdgeRouter =
    EdgeRouter(Id(), Vendor.CISCO, Model.XYZ0002, IP("20.0.0.1"), location)

  private lateinit var switch: Switch

  @Given("I provide a switch")
  fun `i provide a switch`() {
    switch = Switch(
      Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490"),
      Vendor.CISCO,
      Model.XYZ0004,
      IP("20.0.0.100"),
      location,
      SwitchType.LAYER3
    )
  }

  @Then("I add the switch to the edge router")
  fun `i add the switch to the edge router`() {
    val router = useCase.addSwitchToEdgeRouter(switch, edgeRouter)

    router.switches shouldContain (switch.id to switch)
  }
}