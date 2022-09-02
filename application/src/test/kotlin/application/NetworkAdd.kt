package application

import application.ports.input.NetworkManagementInputPort
import application.usecases.NetworkManagementUseCase
import domain.entity.Switch
import domain.vo.*
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class NetworkAdd {
  private val useCase: NetworkManagementUseCase = NetworkManagementInputPort()

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)

  private lateinit var network: Network
  private lateinit var switch: Switch

  @Given("I have a network")
  fun `i have a network`() {
    network = Network(IP("10.0.0.1"), "Finance", 8)
  }

  @And("I have a switch to add a network")
  fun `i have a switch to add a network`() {
    switch = Switch(
      Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490"),
      Vendor.CISCO,
      Model.XYZ0004,
      IP("20.0.0.100"),
      location,
      SwitchType.LAYER3
    )
  }

  @Then("I add the network to the switch")
  fun `i add the network to the switch`() {
    val newSwitch = useCase.addNetworkToSwitch(network, switch)

    newSwitch.id shouldBe switch.id
    newSwitch.networks shouldContain network
  }
}