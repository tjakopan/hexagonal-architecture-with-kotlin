package domain.entity

import domain.entity.specification.DifferentIpSpec
import domain.entity.specification.EmptyRouterSpec
import domain.entity.specification.EmptySwitchSpec
import domain.entity.specification.SameCountrySpec
import domain.vo.*

class CoreRouter(
  id: Id,
  vendor: Vendor,
  model: Model,
  ip: IP,
  location: Location,
  routers: Map<Id, Router> = mapOf()
) : Router(id, vendor, model, ip, location) {
  private val _routers: MutableMap<Id, Router> = routers.toMutableMap()
  val routers: Map<Id, Router>
    get() = _routers

  fun addRouter(router: Router) {
    val sameCountrySpec = SameCountrySpec(this)
    val diffIpSpec = DifferentIpSpec(this)
    sameCountrySpec.check(router)
    diffIpSpec.check(router)

    _routers[router.id] = router
  }

  fun removeRouter(router: Router): Router? {
    val emptyRouterSpec = EmptyRouterSpec()
    val emptySwitchSpec = EmptySwitchSpec()
    when (router) {
      is CoreRouter -> emptyRouterSpec.check(router)
      is EdgeRouter -> emptySwitchSpec.check(router)
    }
    return _routers.remove(router.id)
  }

  override fun toString(): String {
    return "CoreRouter(routers=$routers)"
  }
}