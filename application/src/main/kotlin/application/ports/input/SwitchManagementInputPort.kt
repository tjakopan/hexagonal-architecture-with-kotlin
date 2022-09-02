package application.ports.input

import application.ports.output.SwitchManagementOutputPort
import application.usecases.SwitchManagementUseCase
import domain.entity.EdgeRouter
import domain.entity.Switch
import domain.vo.*

class SwitchManagementInputPort(private val outputPort: SwitchManagementOutputPort) : SwitchManagementUseCase {
  override fun createSwitch(vendor: Vendor, model: Model, ip: IP, location: Location, type: SwitchType): Switch =
    Switch(Id(), vendor, model, ip, location, type)

  override fun retrieveSwitch(id: Id): Switch? = outputPort.retrieveSwitch(id)

  override fun addSwitchToEdgeRouter(switch: Switch, edgeRouter: EdgeRouter): EdgeRouter = edgeRouter.addSwitch(switch)

  override fun removeSwitchFromEdgeRouter(switch: Switch, edgeRouter: EdgeRouter): EdgeRouter =
    edgeRouter.removeSwitch(switch)
}