package application

import application.ports.input.SwitchManagementInputPort
import application.usecases.SwitchManagementUseCase
import domain.entity.EdgeRouter
import domain.entity.Switch
import domain.vo.*
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.assertions.withClue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class SwitchAdd {
  private val useCase: SwitchManagementUseCase = SwitchManagementInputPort()

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

    val actualSwitchId = router.switches[Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")]?.id
    withClue("Actual switch id should be present") {
      actualSwitchId.shouldNotBeNull()
    }
    actualSwitchId shouldBe switch.id
  }
}