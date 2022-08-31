package application.usecases

import domain.entity.CoreRouter
import domain.entity.Router
import domain.vo.*
import kotlin.reflect.KClass

interface RouterManagementUseCase {
  fun <T : Router> createRouter(vendor: Vendor, model: Model, ip: IP, location: Location, type: KClass<T>): T

  fun addRouterToCoreRouter(router: Router, coreRouter: CoreRouter): CoreRouter

  fun removeRouterFromCoreRouter(router: Router, coreRouter: CoreRouter): Router?

  fun retrieveRouter(id: Id): Router?

  fun persistRouter(router: Router): Router
}

inline fun <reified T : Router> RouterManagementUseCase.createRouter(
  vendor: Vendor,
  model: Model,
  ip: IP,
  location: Location
): T =
  createRouter(vendor, model, ip, location, T::class)