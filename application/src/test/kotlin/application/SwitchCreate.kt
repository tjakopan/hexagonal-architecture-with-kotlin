package application

import application.ports.input.SwitchManagementInputPort
import application.ports.output.SwitchManagementOutputPort
import application.usecases.SwitchManagementUseCase
import domain.entity.Switch
import domain.vo.*
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class SwitchCreate {
  private val outputPort: SwitchManagementOutputPort = mockk(relaxed = true)
  private val useCase: SwitchManagementUseCase = SwitchManagementInputPort(outputPort)

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)

  private lateinit var switch: Switch

  @Given("I provide all required data to create a switch")
  fun `i provide all required data to create a switch`() {
    switch = useCase.createSwitch(Vendor.CISCO, Model.XYZ0001, IP("20.0.0.100"), location, SwitchType.LAYER3)
  }

  @Then("A new switch is created")
  fun `a new switch is created`() {
    this::switch.isInitialized.shouldBeTrue()
    switch.vendor shouldBe Vendor.CISCO
    switch.model shouldBe Model.XYZ0001
    switch.ip shouldBe IP("20.0.0.100")
    switch.location shouldBe location
    switch.type shouldBe SwitchType.LAYER3
  }
}