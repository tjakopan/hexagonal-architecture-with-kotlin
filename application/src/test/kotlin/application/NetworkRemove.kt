package application

import application.ports.input.NetworkManagementInputPort
import application.usecases.NetworkManagementUseCase
import domain.entity.Switch
import domain.vo.*
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe

class NetworkRemove {
  private val useCase: NetworkManagementUseCase = NetworkManagementInputPort()

  private val location: Location =
    Location("Av Republica Argentina 3109", "Curitiba", "PR", 80610260, "Brazil", 10f, -10f)
  private lateinit var network: Network
  private lateinit var switch: Switch

  @Given("I know the network I want to remove")
  fun `i know the network I want to remove`() {
    network = Network(IP("20.0.0.0"), "TestNetwork", 8)
  }

  @Given("I have a switch to remove a network")
  fun `i have a switch to remove a network`() {
    switch = Switch(
      Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490"),
      Vendor.CISCO,
      Model.XYZ0004,
      IP("20.0.0.100"),
      location,
      SwitchType.LAYER3,
      listOf(network)
    )
  }

  @Then("I remove the network from the switch")
  fun `i remove the network from the switch`() {
    val newSwitch = useCase.removeNetworkFromSwitch(network, switch)

    newSwitch.id shouldBe switch.id
    newSwitch.networks shouldNotContain network
  }
}