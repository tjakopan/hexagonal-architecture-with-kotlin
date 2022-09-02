package framework.adapters.input.generic

import application.ports.input.NetworkManagementInputPort
import application.ports.input.SwitchManagementInputPort
import application.usecases.NetworkManagementUseCase
import application.usecases.SwitchManagementUseCase
import domain.entity.Switch
import domain.vo.Id
import domain.vo.Network
import framework.adapters.output.h2.SwitchManagementH2Adapter

class NetworkManagementGenericAdapter(
  private val switchManagementUseCase: SwitchManagementUseCase = SwitchManagementInputPort(SwitchManagementH2Adapter),
  private val networkManagementUseCase: NetworkManagementUseCase = NetworkManagementInputPort()
) {
  fun addNetworkToSwitch(network: Network, switchId: Id): Switch {
    val switch = switchManagementUseCase.retrieveSwitch(switchId)
      ?: throw IllegalArgumentException("Switch $switchId does not exist")
    return networkManagementUseCase.addNetworkToSwitch(network, switch)
  }

  fun removeNetworkFromSwitch(network: Network, switchId: Id): Switch {
    val switch = switchManagementUseCase.retrieveSwitch(switchId)
      ?: throw IllegalArgumentException("Switch $switchId does not exist")
    return networkManagementUseCase.removeNetworkFromSwitch(network, switch)
  }
}