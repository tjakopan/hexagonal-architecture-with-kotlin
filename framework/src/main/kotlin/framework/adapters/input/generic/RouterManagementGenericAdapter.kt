package framework.adapters.input.generic

import application.ports.input.RouterManagementInputPort
import application.usecases.RouterManagementUseCase
import domain.entity.CoreRouter
import domain.entity.Router
import domain.vo.*
import framework.adapters.output.h2.RouterManagementH2Adapter
import kotlin.reflect.KClass

class RouterManagementGenericAdapter(
  private val useCase: RouterManagementUseCase = RouterManagementInputPort(RouterManagementH2Adapter)
) {
  fun retrieveRouter(id: Id): Router? = useCase.retrieveRouter(id)

  fun removeRouter(id: Id) = useCase.removeRouter(id)

  fun <T : Router> createRouter(vendor: Vendor, model: Model, ip: IP, location: Location, type: KClass<T>): Router =
    useCase.createRouter(vendor, model, ip, location, type)

  fun addRouterToCoreRouter(routerId: Id, coreRouterId: Id): CoreRouter {
    val router =
      useCase.retrieveRouter(routerId) ?: throw IllegalArgumentException("Router $routerId does not exist")
    val coreRouter =
      useCase.retrieveRouter(coreRouterId) ?: throw IllegalArgumentException("Router $coreRouterId does not exist")
    if (coreRouter !is CoreRouter) throw UnsupportedOperationException("Please inform the id of a core router to add a router")
    return useCase.addRouterToCoreRouter(router, coreRouter)
  }

  fun removeRouterFromCoreRouter(routerId: Id, coreRouterId: Id): CoreRouter {
    val router =
      useCase.retrieveRouter(routerId) ?: throw IllegalArgumentException("Router $routerId does not exist")
    val coreRouter =
      useCase.retrieveRouter(coreRouterId) ?: throw IllegalArgumentException("Router $coreRouterId does not exist")
    if (coreRouter !is CoreRouter) throw UnsupportedOperationException("Please inform the id of a core router to remove a router")
    return useCase.removeRouterFromCoreRouter(router, coreRouter)
  }
}

inline fun <reified T : Router> RouterManagementGenericAdapter.createRouter(
  vendor: Vendor,
  model: Model,
  ip: IP,
  location: Location
): Router =
  createRouter(vendor, model, ip, location, T::class)