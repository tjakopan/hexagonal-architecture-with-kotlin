package application.ports.input

import application.usecases.NetworkManagementUseCase
import domain.entity.Switch
import domain.vo.IP
import domain.vo.Network

class NetworkManagementInputPort : NetworkManagementUseCase {
  override fun createNetwork(address: IP, name: String, cidr: Int): Network = Network(address, name, cidr)

  override fun addNetworkToSwitch(network: Network, switch: Switch): Switch {
    switch.addNetworkToSwitch(network)
    return switch
  }

  override fun removeNetworkFromSwitch(network: Network, switch: Switch): Switch {
    switch.removeNetworkFromSwitch(network)
    return switch
  }
}