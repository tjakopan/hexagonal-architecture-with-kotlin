package framework

import domain.service.NetworkService
import domain.vo.IP
import domain.vo.Id
import domain.vo.Network
import framework.adapters.input.generic.NetworkManagementGenericAdapter
import framework.adapters.input.generic.SwitchManagementGenericAdapter
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import kotlin.test.Test

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class NetworkTest {
  private val networkManagementGenericAdapter: NetworkManagementGenericAdapter = NetworkManagementGenericAdapter()
  private val switchManagementGenericAdapter: SwitchManagementGenericAdapter = SwitchManagementGenericAdapter()

  @Test
  @Order(1)
  fun `add network to switch`() {
    val network = Network(IP("20.0.0.0"), "TestNetwork", 8)
    val switchId = Id("922dbcd5-d071-41bd-920b-00f83eb4bb46")

    val switch = networkManagementGenericAdapter.addNetworkToSwitch(network, switchId)

    val actualNetwork = NetworkService.findNetwork(switch.networks, Network.namePredicate("TestNetwork"))
    actualNetwork shouldBe network
  }

  @Test
  @Order(2)
  fun `remove network from switch`() {
    val switchId = Id("922dbcd5-d071-41bd-920b-00f83eb4bb46")
    val switch = switchManagementGenericAdapter.retrieveSwitch(switchId)
    val network = NetworkService.findNetwork(switch!!.networks, Network.namePredicate("HR"))

    val newSwitch = networkManagementGenericAdapter.removeNetworkFromSwitch(network!!, switchId)

    NetworkService.findNetwork(newSwitch.networks, Network.namePredicate("HR")).shouldBeNull()
  }
}