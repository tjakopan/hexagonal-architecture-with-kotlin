package application.usecases

import domain.entity.Switch
import domain.vo.IP
import domain.vo.Network

interface NetworkManagementUseCase {
  fun createNetwork(address: IP, name: String, cidr: Int): Network

  fun addNetworkToSwitch(network: Network, switch: Switch): Switch

  fun removeNetworkFromSwitch(network: Network, switch: Switch): Switch
}