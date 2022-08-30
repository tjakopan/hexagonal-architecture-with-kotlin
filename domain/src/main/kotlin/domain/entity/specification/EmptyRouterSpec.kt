package domain.entity.specification

import domain.entity.CoreRouter
import domain.entity.specification.shared.AbstractSpecification
import domain.entity.specification.shared.check

class EmptyRouterSpec : AbstractSpecification<CoreRouter>() {
  override fun isSatisfiedBy(t: CoreRouter): Boolean = t.routers.isEmpty()

  override fun check(t: CoreRouter) =
    check(t) { "It isn't allowed to remove a core router with other routers attached to it." }
}