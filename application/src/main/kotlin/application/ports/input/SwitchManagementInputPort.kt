package application.ports.input

import application.usecases.SwitchManagementUseCase
import domain.entity.EdgeRouter
import domain.entity.Switch
import domain.vo.*

class SwitchManagementInputPort : SwitchManagementUseCase {
  override fun createSwitch(vendor: Vendor, model: Model, ip: IP, location: Location, type: SwitchType): Switch =
    Switch(Id(), vendor, model, ip, location, type)

  override fun addSwitchToEdgeRouter(switch: Switch, edgeRouter: EdgeRouter): EdgeRouter {
    edgeRouter.addSwitch(switch)
    return edgeRouter
  }

  override fun removeSwitchFromEdgeRouter(switch: Switch, edgeRouter: EdgeRouter): EdgeRouter {
    edgeRouter.removeSwitch(switch)
    return edgeRouter
  }
}