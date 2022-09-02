package domain.entity

import domain.entity.specification.DifferentIpSpec
import domain.entity.specification.EmptyRouterSpec
import domain.entity.specification.EmptySwitchSpec
import domain.entity.specification.SameCountrySpec
import domain.vo.*

data class CoreRouter(
  override val id: Id,
  override val vendor: Vendor,
  override val model: Model,
  override val ip: IP,
  override val location: Location,
  val routers: Map<Id, Router> = mapOf()
) : Router(id, vendor, model, ip, location) {
  fun addRouter(router: Router): CoreRouter {
    val sameCountrySpec = SameCountrySpec(this)
    val diffIpSpec = DifferentIpSpec(this)
    sameCountrySpec.check(router)
    diffIpSpec.check(router)

    val newRouters = routers + (router.id to router)

    return copy(routers = newRouters)
  }

  fun removeRouter(router: Router): CoreRouter {
    val emptyRouterSpec = EmptyRouterSpec()
    val emptySwitchSpec = EmptySwitchSpec()
    when (router) {
      is CoreRouter -> emptyRouterSpec.check(router)
      is EdgeRouter -> emptySwitchSpec.check(router)
    }

    val newRouters = routers - router.id

    return copy(routers = newRouters)
  }
}