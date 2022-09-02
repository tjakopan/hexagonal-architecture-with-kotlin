package framework.adapters.input.generic

import application.ports.input.RouterManagementInputPort
import application.ports.input.SwitchManagementInputPort
import application.usecases.RouterManagementUseCase
import application.usecases.SwitchManagementUseCase
import domain.entity.EdgeRouter
import domain.entity.Switch
import domain.vo.*
import framework.adapters.output.h2.RouterManagementH2Adapter
import framework.adapters.output.h2.SwitchManagementH2Adapter

class SwitchManagementGenericAdapter(
  private val switchManagementUseCase: SwitchManagementUseCase = SwitchManagementInputPort(SwitchManagementH2Adapter),
  private val routerManagementUseCase: RouterManagementUseCase = RouterManagementInputPort(RouterManagementH2Adapter)
) {
  fun retrieveSwitch(id: Id): Switch? = switchManagementUseCase.retrieveSwitch(id)

  fun createAndAddSwitchToEdgeRouter(
    vendor: Vendor,
    model: Model,
    ip: IP,
    location: Location,
    type: SwitchType,
    routerId: Id
  ): EdgeRouter {
    val switch = switchManagementUseCase.createSwitch(vendor, model, ip, location, type)
    val router = routerManagementUseCase.retrieveRouter(routerId)
    if (router !is EdgeRouter) throw UnsupportedOperationException("Please inform the id of an edge router to add a switch")
    val newRouter = switchManagementUseCase.addSwitchToEdgeRouter(switch, router)
    routerManagementUseCase.persistRouter(newRouter)
    return newRouter
  }

  fun removeSwitchFromEdgeRouter(switchId: Id, routerId: Id): EdgeRouter {
    val router = routerManagementUseCase.retrieveRouter(routerId)
    if (router !is EdgeRouter) throw UnsupportedOperationException("Please inform the id of an edge router to add a switch")
    val switch = switchManagementUseCase.retrieveSwitch(switchId)
      ?: throw IllegalArgumentException("Switch $switchId does not exist")
    val newRouter = switchManagementUseCase.removeSwitchFromEdgeRouter(switch, router)
    routerManagementUseCase.persistRouter(newRouter)
    return newRouter
  }
}