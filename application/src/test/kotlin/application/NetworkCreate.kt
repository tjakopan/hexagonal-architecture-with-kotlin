package application

import application.ports.input.NetworkManagementInputPort
import application.usecases.NetworkManagementUseCase
import domain.vo.IP
import domain.vo.Network
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class NetworkCreate {
  private val useCase: NetworkManagementUseCase = NetworkManagementInputPort()

  private lateinit var network: Network

  @Given("I provide all required data to create a network")
  fun `i provide all required data to create a network`() {
    network = useCase.createNetwork(IP("10.0.0.1"), "Finance", 8)
  }

  @Then("A new network is created")
  fun `a new network is created`() {
    this::network.isInitialized.shouldBeTrue()
    network.ip shouldBe IP("10.0.0.1")
    network.name shouldBe "Finance"
    network.cidr shouldBe 8
  }
}