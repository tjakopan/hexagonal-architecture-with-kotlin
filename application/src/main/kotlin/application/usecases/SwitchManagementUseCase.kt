package application.usecases

import domain.entity.EdgeRouter
import domain.entity.Switch
import domain.vo.*

interface SwitchManagementUseCase {
  fun createSwitch(vendor: Vendor, model: Model, ip: IP, location: Location, type: SwitchType): Switch

  fun retrieveSwitch(id: Id): Switch?

  fun addSwitchToEdgeRouter(switch: Switch, edgeRouter: EdgeRouter): EdgeRouter

  fun removeSwitchFromEdgeRouter(switch: Switch, edgeRouter: EdgeRouter): EdgeRouter
}