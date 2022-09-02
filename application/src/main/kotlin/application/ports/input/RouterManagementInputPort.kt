package application.ports.input

import application.ports.output.RouterManagementOutputPort
import application.usecases.RouterManagementUseCase
import domain.entity.CoreRouter
import domain.entity.Router
import domain.entity.factory.RouterFactory
import domain.vo.*
import kotlin.reflect.KClass

class RouterManagementInputPort(private val outputPort: RouterManagementOutputPort) : RouterManagementUseCase {
  override fun <T : Router> createRouter(
    vendor: Vendor,
    model: Model,
    ip: IP,
    location: Location,
    type: KClass<T>
  ): T {
    val router = RouterFactory.createRouter(vendor, model, ip, location, type)
    persistRouter(router)
    return router
  }

  override fun removeRouter(id: Id) = outputPort.removeRouter(id)

  override fun addRouterToCoreRouter(router: Router, coreRouter: CoreRouter): CoreRouter {
    val newCoreRouter = coreRouter.addRouter(router)
    persistRouter(newCoreRouter)
    return newCoreRouter
  }

  override fun removeRouterFromCoreRouter(router: Router, coreRouter: CoreRouter): CoreRouter {
    val newCoreRouter = coreRouter.removeRouter(router)
    persistRouter(newCoreRouter)
    return newCoreRouter
  }

  override fun retrieveRouter(id: Id): Router? = outputPort.retrieveRouter(id)

  override fun persistRouter(router: Router) = outputPort.persistRouter(router)
}