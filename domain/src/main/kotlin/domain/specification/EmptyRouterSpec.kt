package domain.specification

import domain.entity.CoreRouter
import domain.specification.shared.AbstractSpecification
import domain.specification.shared.check

class EmptyRouterSpec : AbstractSpecification<CoreRouter>() {
  override fun isSatisfiedBy(t: CoreRouter): Boolean = t.routers.isEmpty()

  override fun check(t: CoreRouter) =
    check(t) { "It isn't allowed to remove a core router with other routers attached to it." }
}