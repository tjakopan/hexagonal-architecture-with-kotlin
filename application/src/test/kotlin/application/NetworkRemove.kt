package application

import application.ports.input.NetworkManagementInputPort
import application.usecases.NetworkManagementUseCase
import domain.entity.Switch
import domain.service.NetworkService
import domain.vo.*
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.nulls.shouldBeNull

class NetworkRemove {
  private val useCase: NetworkManagementUseCase = NetworkManagementInputPort()

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

  @Given("I know the network I want to remove")
  fun `i know the network I want to remove`() {
  }

  @Given("I have a switch to remove a network")
  fun `i have a switch to remove a network`() {
  }

  @Then("I remove the network from the switch")
  fun `i remove the network from the switch`() {
    useCase.removeNetworkFromSwitch(network, switch)

    NetworkService.findNetwork(switch.networks, Network.namePredicate(network.name)).shouldBeNull()
  }
}